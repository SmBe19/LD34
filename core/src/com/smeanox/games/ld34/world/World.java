package com.smeanox.games.ld34.world;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.smeanox.games.ld34.Consts;

import java.util.ArrayList;
import java.util.List;

/**
 * Comment
 */
public class World implements Updatable, Renderable {
	private Hero hero;
	private List<GroundPart> groundParts;
	private PhysicSimulation physics;
	private WeatherSimulation weatherSimulation;
	private Camera camera;
	private Rectangle currentViewport;

	private float cameraShake;

	private float totalTime;

	private List<Updatable> updatables;
	private List<List<Renderable> > renderables;

	public World(Camera camera){
		this.camera = camera;
		setGroundParts(new ArrayList<GroundPart>());
		updatables = new ArrayList<Updatable>();
		renderables = new ArrayList<List<Renderable> >();

		cameraShake = 0;
		currentViewport = new Rectangle();

		physics = new PhysicSimulation(this);

		updatables.add(this);
		addRenderable(Consts.LAYER_WORLD, this);

		hero = new Hero(this);

		totalTime = 0;

		generateWorldPart();

		weatherSimulation = new WeatherSimulation(this);
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
					float maxGap = getGroundParts().get(getGroundParts().size() - 1).getMaxGap();
					newPos = lastPos +  Math.min(maxGap, MathUtils.random(Consts.GROUNDPART_MIN_DIST, maxGap));
				}
				newWidth = ((int)(MathUtils.random(Consts.GROUNDPART_MIN_WIDTH, Consts.GROUNDPART_MAX_WIDTH)/(Consts.GROUNDPART_TEX_WIDTH*Consts.GROUNDPART_TEX_ZOOM)))*Consts.GROUNDPART_TEX_WIDTH*Consts.GROUNDPART_TEX_ZOOM;
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
			if (gp.getX() < x && gp.getX() + gp.getWidth() > x) return gp;
		}
		return null;
	}

	public GroundPart getPrevGroundPart(float x){
		GroundPart ret = null;
		for (GroundPart gp : getGroundParts()){
			if (gp.getX() < x) ret = gp;
		}
		return ret;
	}
	public GroundPart getNextGroundPart(float x){
		for (GroundPart gp : getGroundParts()){
			if ( gp.getX()  > x) return gp;
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

	public float getCameraShake() {
		return cameraShake;
	}

	public void setCameraShake(float cameraShake){
		setCameraShake(cameraShake, false);
	}

	public void setCameraShake(float cameraShake, boolean force) {
		if(force){
			this.cameraShake = cameraShake;
		} else {
			this.cameraShake = Math.max(this.cameraShake, cameraShake);
		}
	}

	public Rectangle getCurrentViewport() {
		return currentViewport;
	}
}
