package com.smeanox.games.ld34.world;

import com.smeanox.games.ld34.Consts;

/**
 * Comment
 */
public abstract class Plant implements Updatable, Renderable, Collidable, Destroyable {
	private boolean destroyed = false;

	protected World world;

	protected float x0, y0;
	protected float lives;

	public Plant(World world, float x0, float y0) {
		this.x0 = x0;
		this.y0 = y0;
		this.world = world;
		world.getUpdatables().add(this);
		world.addRenderable(Consts.LAYER_PLANT, this);
		world.getPhysics().addCollidable(this);
	}

	public abstract void grow(float delta);

	@Override
	public final void update(float delta) {
		grow(delta);

		if(!isAlive()){
			destroy();
		}
	}

	@Override
	public void destroy() {
		if(isDestroyed()){
			return;
		}
		destroyed = true;
		world.getUpdatables().remove(this);
		world.getRenderables(Consts.LAYER_PLANT).remove(this);
		world.getPhysics().removeCollidable(this);
	}

	@Override
	public boolean isDestroyed() {
		return destroyed;
	}

	public float getX0() {
		return x0;
	}

	public float getY0() {
		return y0;
	}

	public abstract float getWidth();

	public abstract float getHeight();

	public void setLives(float lives) {
		this.lives = lives;
	}

	public float getLives() {
		return lives;
	}

	public void addLives(float lives){
		this.lives += lives;
	}

	public boolean isAlive(){
		return lives > 0;
	}
}
