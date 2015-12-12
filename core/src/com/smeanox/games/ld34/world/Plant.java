package com.smeanox.games.ld34.world;

import com.smeanox.games.ld34.Consts;

/**
 * Comment
 */
public abstract class Plant implements Updatable, Renderable, Collidable, Destroyable {

	protected World world;

	protected float x0, y0;

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
	}

	@Override
	public void destroy() {
		world.getUpdatables().remove(this);
		world.getRenderables(Consts.LAYER_PLANT).remove(this);
		world.getPhysics().removeCollidable(this);
	}

	public float getX0() {
		return x0;
	}

	public float getY0() {
		return y0;
	}
}
