package com.smeanox.games.ld34.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Comment
 */
public class World implements Updatable, Renderable {
	private Hero hero;
	private List<Plant> plants;
	private List<Building> buildings;
	private PhysicSimulation physics;

	private float totalTime;

	private List<Updatable> updatables;
	private List<Renderable> renderables;

	public World(){
		updatables = new ArrayList<Updatable>();
		renderables = new ArrayList<Renderable>();

		physics = new PhysicSimulation(this);

		updatables.add(this);
		renderables.add(this);

		hero = new Hero(this);
		plants = new ArrayList<Plant>();
		buildings = new ArrayList<Building>();

		totalTime = 0;

		new GroundPart(this, 0, 5000);
		new Building(this, 1000, 0, 5,5);
	}

	@Override
	public void update(float delta){
		totalTime += delta;
		physics.update(delta);
	}

	@Override
	public void render(float delta, SpriteBatch spriteBatch) {
	}

	public Hero getHero() {
		return hero;
	}

	public List<Plant> getPlants() {
		return plants;
	}

	public List<Updatable> getUpdatables() {
		return updatables;
	}

	public List<Renderable> getRenderables() {
		return renderables;
	}

	public float getTotalTime() {
		return totalTime;
	}

	public PhysicSimulation getPhysics() {
		return physics;
	}
}
