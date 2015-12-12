package com.smeanox.games.ld34.world;

/**
 * Comment
 */
public abstract class Plant implements Updatable, Renderable, Collidable {

	protected World world;

	public Plant(World world) {
		this.world = world;
		world.getUpdatables().add(this);
		world.getRenderables().add(this);
		world.getPhysics().addCollidable(this);
	}

	public abstract void grow(float delta);

	@Override
	public final void update(float delta) {
		grow(delta);
	}
}
