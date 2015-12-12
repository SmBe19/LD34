package com.smeanox.games.ld34.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smeanox.games.ld34.Consts;
import com.smeanox.games.ld34.Textures;

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
	private List<List<Renderable> > renderables;

	public World(){
		updatables = new ArrayList<Updatable>();
		renderables = new ArrayList<List<Renderable> >();

		physics = new PhysicSimulation(this);

		updatables.add(this);
		addRenderable(Consts.LAYER_WORLD, this);

		hero = new Hero(this);
		plants = new ArrayList<Plant>();
		buildings = new ArrayList<Building>();

		totalTime = 0;

		new GroundPart(this, 0, 10000);
		new Building(this, 1000, Consts.GROUND_HEIGHT, 10,10);

		new Vine(this, 600, Consts.GROUND_HEIGHT, 100);
		new ParticleSystem(this, "snow", Consts.LAYER_HERO, Textures.get().particle, Color.WHITE, 1f, 10, 1, 0.01f, 0.001f, 2500, 300, 2500, 5, -10, 0, 10, 10).setGenerating(true);
	}

	public void addRenderable(int layer, Renderable renderable){
		while(renderables.size() <= layer){
			renderables.add(new ArrayList<Renderable>());
		}
		renderables.get(layer).add(renderable);
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

	public List<List<Renderable>> getRenderables(){
		return renderables;
	}

	public List<Renderable> getRenderables(int layer) {
		return renderables.get(layer);
	}

	public float getTotalTime() {
		return totalTime;
	}

	public PhysicSimulation getPhysics() {
		return physics;
	}
}
