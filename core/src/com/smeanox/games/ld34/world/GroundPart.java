package com.smeanox.games.ld34.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.smeanox.games.ld34.Consts;
import com.smeanox.games.ld34.Textures;

import java.util.ArrayList;
import java.util.List;

import javax.xml.soap.Text;

/**
 * Comment
 */
public class GroundPart implements Renderable, Collidable, Destroyable {
	private boolean destroyed = false;

	private World world;
	private int x, width;

	private List<Plant> plants;
	private List<Enemy> enemies;
	private List<Building> buildings;

	private Texture ground;

	private TextureRegion leftBorder;
	private TextureRegion rightBorder;
	private TextureRegion[] centre;

	private float maxGap = 0;

	public GroundPart(World world, int x, int width) {
		this.world = world;
		this.x = x;
		this.width = width;

		plants = new ArrayList<Plant>();
		enemies = new ArrayList<Enemy>();
		buildings = new ArrayList<Building>();

		ground = Textures.get().ground;
		centre = new TextureRegion[4];
		leftBorder = new TextureRegion(ground, 0, 0, Consts.GROUNDPART_TEX_WIDTH, Consts.GROUNDPART_TEX_HEIGHT);
		rightBorder = new TextureRegion(ground, 5*Consts.GROUNDPART_TEX_WIDTH, 0, Consts.GROUNDPART_TEX_WIDTH, Consts.GROUNDPART_TEX_HEIGHT);
		for (int xx = 1; xx < 5; xx++){
			centre[xx-1] = new TextureRegion(ground, xx * Consts.GROUNDPART_TEX_WIDTH, 0, Consts.GROUNDPART_TEX_WIDTH, Consts.GROUNDPART_TEX_HEIGHT);
		}

		world.addRenderable(Consts.LAYER_GROUND, this);
		world.getPhysics().addCollidable(this);
	}


	public void generate(){
		// buildings
		float lastBuilding = 0;

		while (lastBuilding < width){
			lastBuilding += MathUtils.random(Consts.BUILDING_MIN_DIST, Consts.BUILDING_MAX_DIST);
			int bwidth = MathUtils.random(Consts.BUILDING_MIN_WIDTH, Consts.BUILDING_MAX_WIDTH);

			if(lastBuilding + bwidth + Consts.BUILDING_MAX_DIST + Consts.BUILDING_MAX_WIDTH * Consts.BUILDING_TEX_WIDTH * Consts.BUILDING_TEX_ZOOM > width){
				break;
			}

			buildings.add(new Building(world, getX() + lastBuilding, Consts.GROUND_HEIGHT, MathUtils.random(Consts.BUILDING_MIN_HEIGHT, Consts.BUILDING_MAX_HEIGHT), bwidth));
			lastBuilding += buildings.get(buildings.size() - 1).getWidth();
		}
		int bwidth = MathUtils.random(Consts.BUILDING_MIN_WIDTH, Consts.BUILDING_MAX_WIDTH);
		int bheight = MathUtils.random(Consts.BUILDING_MIN_HEIGHT, Consts.BUILDING_MAX_HEIGHT);

		maxGap = Consts.HERO_VELO  * (float)Math.sqrt( 2 * bheight * Consts.BUILDING_TEX_HEIGHT * Consts.BUILDING_TEX_ZOOM / -Consts.GRAVITY);

		float endGap = Math.min(MathUtils.random(Consts.GROUNDPART_END_MIN_WIDTH, Consts.GROUNDPART_END_MAX_WIDTH), maxGap);
		buildings.add(new Building(world, width - bwidth * Consts.BUILDING_TEX_WIDTH * Consts.BUILDING_TEX_ZOOM + getX() - endGap, Consts.GROUND_HEIGHT, bheight, bwidth));
		maxGap -= endGap;

		// plants
		distributeThing(Consts.PLANT_MIN_DIST, Consts.PLANT_MAX_DIST, false, Consts.PLANT_ON_ROOF_CHANCE, new ThingFactory() {
			@Override
			public float createThing(World world, float x, float y) {
				Plant plant = PlantFactory.getRandomPlant(world, x, y);
				plants.add(plant);
				return plant.getWidth();
			}
		});

		// enemies
		distributeThing(Consts.ENEMY_MIN_DIST, Consts.ENEMY_MAX_DIST, false, Consts.ENEMY_ON_ROOF_CHANCE, new ThingFactory() {
			@Override
			public float createThing(World world, float x, float y) {
				GroundEnemy enemy = new GroundEnemy(world, x, y);
				enemies.add(enemy);
				return Consts.ENEMY_TEX_WIDTH;
			}
		});

		// coins
		final int logVal = x / Consts.COIN_LOG_ADD_PER_DIST;
		distributeThing(Consts.COIN_MIN_DIST, Consts.COIN_MAX_DIST, false, Consts.COIN_ON_ROOF_CHANCE, new ThingFactory() {
			@Override
			public float createThing(World world, float x, float y) {
				int coinNum = 0;
				float rand = MathUtils.random(0f, 1);
				for (int i = 0; i < CoinPlant.colors.length; i++) {
					rand -= Consts.COIN_PROBABILITIES[i];
					if (rand <= 0) {
						coinNum = i;
						break;
					}
				}
				CoinPlant coin = new CoinPlant(world, x, y + Consts.COIN_OFFSET_Y, logVal + coinNum * 2, coinNum);
				plants.add(coin);
				return coin.getWidth();
			}
		});
	}

	private interface ThingFactory {
		float createThing(World world, float x, float y);
	}

	private void distributeThing(float minDist, float maxDist, boolean roofOnly, float roofChance, ThingFactory thingFactory){
		float lastThing = 0;

		while(lastThing < width){
			lastThing += MathUtils.random(minDist, maxDist);
			if(lastThing > width){
				break;
			}

			float y = Consts.GROUND_HEIGHT;
			for(Building aBuilding : buildings){
				if(aBuilding.getX() < x + lastThing && aBuilding.getX() + aBuilding.getWidth() > x + lastThing){
					if (MathUtils.randomBoolean(roofChance)){
						y = aBuilding.getHeight() + aBuilding.getY();
					}
					break;
				}
			}
			if(roofOnly && Math.abs(y - Consts.GROUND_HEIGHT) < 0.1e-10){
				continue;
			}

			lastThing += thingFactory.createThing(world, x + lastThing, y);
		}
	}

	@Override
	public Rectangle getCollisionBox() {
		return new Rectangle(x, Consts.GROUND_HEIGHT - Consts.GROUND_THICKNESS, width, Consts.GROUND_THICKNESS);
	}

	@Override
	public boolean onCollision(Collidable collidable, float delta) {
		return true;
	}


	@Override
	public void render(float delta, SpriteBatch spriteBatch) {
		for (float x = getX(); x < getX() + getWidth(); x+= Consts.GROUNDPART_TEX_WIDTH * Consts.GROUNDPART_TEX_ZOOM){
			TextureRegion tex = centre[MathUtils.random(0,centre.length-1)];
			if (x == getX()) tex = leftBorder;
			if (x + Consts.GROUNDPART_TEX_WIDTH * Consts.GROUNDPART_TEX_ZOOM >= getX() + getWidth()) tex = rightBorder;
			spriteBatch.draw(tex, x ,Consts.GROUND_HEIGHT + Consts.GROUNDPART_TEX_OFFSET_Y * Consts.GROUNDPART_TEX_ZOOM- Consts.GROUNDPART_TEX_HEIGHT * Consts.GROUNDPART_TEX_ZOOM,
					Consts.GROUNDPART_TEX_WIDTH* Consts.GROUNDPART_TEX_ZOOM, Consts.GROUNDPART_TEX_HEIGHT* Consts.GROUNDPART_TEX_ZOOM);
		}
	}

	@Override
	public void destroy() {
		if(isDestroyed()){
			return;
		}
		destroyed = true;
		world.getRenderables(Consts.LAYER_GROUND).remove(this);
		world.getPhysics().removeCollidable(this);

		for(Plant plant : new ArrayList<Plant>(plants)){
			plant.destroy();
		}
		for(Building building : new ArrayList<Building>(buildings)){
			building.destroy();
		}
	}

	@Override
	public boolean isDestroyed() {
		return destroyed;
	}

	public int getX() {
		return x;
	}

	public int getWidth() {
		return width;
	}

	public List<Plant> getPlants() {
		return plants;
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}

	public List<Building> getBuildings() {
		return buildings;
	}

	public float getMaxGap() {
		return maxGap;
	}
}
