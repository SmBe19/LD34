package com.smeanox.games.ld34.world;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.smeanox.games.ld34.Consts;
import com.smeanox.games.ld34.Textures;

import java.util.ArrayList;
import java.util.List;

/**
 * Comment
 */
public class World implements Updatable, Renderable {
	private Hero hero;
	private List<GroundPart> groundParts;
	private PhysicSimulation physics;
	private Camera camera;

	private float totalTime;
	private ParticleSystem snowSystem;

	private List<Updatable> updatables;
	private List<List<Renderable> > renderables;

	public World(Camera camera){
		this.camera = camera;
		setGroundParts(new ArrayList<GroundPart>());
		updatables = new ArrayList<Updatable>();
		renderables = new ArrayList<List<Renderable> >();

		physics = new PhysicSimulation(this);

		updatables.add(this);
		addRenderable(Consts.LAYER_WORLD, this);

		hero = new Hero(this);

		totalTime = 0;

		generateWorldPart();
		//new Building(this, 1000, Consts.GROUND_HEIGHT, 10,10);
		//new Vine(this, 600, Consts.GROUND_HEIGHT, 1000);

		snowSystem = new ParticleSystem(this, "snow", null, Consts.LAYER_HERO, Textures.get().particle, Color.WHITE, 1f, 10, 1, 0.01f, 0.001f, 2500, 300, 2500, 5, -10, 0, 10, 10);
		snowSystem.setGenerating(true);
	}

	public void generateWorldPart(){
		float rightBorder = camera.position.x + Consts.WIDTH / 2;

		float lastPos = 0;
		while (true){
			for (GroundPart groundPart : getGroundParts()) {
				lastPos = Math.max(lastPos, groundPart.getX() + groundPart.getWidth());
			}

			if (lastPos < rightBorder + Consts.WIDTH) {
				float newPos, newWidth;
				if(lastPos == 0) {
					newPos = 0;
				} else {
					newPos = lastPos + MathUtils.random(Consts.GROUNDPART_MIN_DIST, Consts.GROUNDPART_MAX_DIST);
				}
				newWidth = MathUtils.random(Consts.GROUNDPART_MIN_WIDTH, Consts.GROUNDPART_MAX_WIDTH);
				GroundPart newGroundPart = new GroundPart(this, ((int) newPos), ((int) newWidth));
				newGroundPart.generate();
				getGroundParts().add(newGroundPart);
			} else {
				break;
			}
		}
	}

	private void clearWorldPart(){
		float leftBorder = camera.position.x - Consts.WIDTH;

		for(GroundPart groundPart : new ArrayList<GroundPart>(getGroundParts())){
			if(groundPart.getX() + groundPart.getWidth() < leftBorder){
				groundPart.destroy();
				getGroundParts().remove(groundPart);
			}
		}
	}

	public GroundPart getGroundPart(float x){
		for (GroundPart gp : getGroundParts()){
			if (gp.getX() > x) return gp;
		}
		return null;
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

		clearWorldPart();
		generateWorldPart();

		snowSystem.setStartX(hero.getX());
		snowSystem.setStartY(hero.getY());
	}

	@Override
	public void render(float delta, SpriteBatch spriteBatch) {
	}

	public Hero getHero() {
		return hero;
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

	public List<GroundPart> getGroundParts() {
		return groundParts;
	}

	public void setGroundParts(List<GroundPart> groundParts) {
		this.groundParts = groundParts;
	}
}
