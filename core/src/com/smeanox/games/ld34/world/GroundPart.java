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
	private List<Building> buildings;

	private Texture ground;

	public GroundPart(World world, int x, int width) {
		this.world = world;
		this.x = x;
		this.width = width;

		plants = new ArrayList<Plant>();
		buildings = new ArrayList<Building>();

		ground = Textures.get().ground;

		world.addRenderable(Consts.LAYER_GROUND, this);
		world.getPhysics().addCollidable(this);
	}

	public void generate(){
		PlantFactory plantFactory = new PlantFactory();

		float lastBuilding = 0;

		while (lastBuilding + Consts.BUILDING_MIN_DIST + Consts.BUILDING_MIN_WIDTH < width){
			lastBuilding += MathUtils.random(Consts.BUILDING_MIN_DIST, Consts.BUILDING_MAX_DIST);

			buildings.add(new Building(world, lastBuilding, Consts.GROUND_HEIGHT, 8, 16));
		}
	}

	@Override
	public Rectangle getCollisionBox() {
		return new Rectangle(x, Consts.GROUND_HEIGHT - Consts.GROUND_THICKNESS, width, Consts.GROUND_THICKNESS);
	}

	@Override
	public boolean onCollision(Collidable collidable) {
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

	public List<Building> getBuildings() {
		return buildings;
	}
}
