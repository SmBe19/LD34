package com.smeanox.games.ld34.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.smeanox.games.ld34.Consts;
import com.smeanox.games.ld34.Textures;

import java.util.ArrayList;
import java.util.List;

/**
 * Comment
 */
public class GroundPart implements Renderable, Collidable, Destroyable {

	private World world;
	private int x, width;

	private List<Plant> plants;
	private List<Enemy> enemies;
	private List<Building> buildings;

	private Texture ground;
	private float maxGap = 0;

	public GroundPart(World world, int x, int width) {
		this.world = world;
		this.x = x;
		this.width = width;

		plants = new ArrayList<Plant>();
		enemies = new ArrayList<Enemy>();
		buildings = new ArrayList<Building>();

		ground = Textures.get().ground;

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
		float lastPlant = 0;

		while(lastPlant < width){
			lastPlant += MathUtils.random(Consts.PLANT_MIN_DIST, Consts.PLANT_MAX_DIST);
			if(lastPlant > width){
				break;
			}

			float y = Consts.GROUND_HEIGHT;
			for(Building aBuilding : buildings){
				if(aBuilding.getX() < x + lastPlant && aBuilding.getX() + aBuilding.getWidth() > x + lastPlant){
					if (MathUtils.randomBoolean(Consts.PLANT_ON_ROOF_CHANCE)){
						y = aBuilding.getHeight() + aBuilding.getY();
					}
					break;
				}
			}

			Plant plant = PlantFactory.getRandomPlant(world, x + lastPlant, y);
			plants.add(plant);
			lastPlant += plant.getWidth();
		}

		// enemies
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
		int i = 0;
		while(i * ground.getWidth() < width){
			spriteBatch.draw(ground, x + i * ground.getWidth(),
					Consts.GROUND_HEIGHT + Consts.GROUND_TEX_OFFSET_Y - Consts.GROUND_TEX_HEIGHT,
					ground.getWidth(), Consts.GROUND_TEX_HEIGHT);
			i++;
		}
	}

	@Override
	public void destroy() {
		world.getRenderables(Consts.LAYER_GROUND).remove(this);
		world.getPhysics().removeCollidable(this);

		for(Plant plant : new ArrayList<Plant>(plants)){
			plant.destroy();
		}
		for(Building building : new ArrayList<Building>(buildings)){
			building.destroy();
		}
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
