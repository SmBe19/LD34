package com.smeanox.games.ld34.world;

import com.smeanox.games.ld34.Consts;

/**
 * Comment
 */
public abstract class Enemy extends Rigidbody implements Updatable, Renderable, Destroyable {
	protected World world;

	public Enemy(World world){
		this.world = world;
		world.getUpdatables().add(this);
		world.addRenderable(Consts.LAYER_ENEMY, this);
		world.getPhysics().addCollidable(this);
	}

	@Override
	public void destroy() {
		world.getUpdatables().remove(this);
		world.getRenderables(Consts.LAYER_ENEMY).remove(this);
		world.getPhysics().removeCollidable(this);
	}

	public abstract float getDamage();

	@Override
	public boolean onCollision(Collidable collidable, float delta) {
		if(collidable instanceof Hero){
			((Hero) collidable).addLives(-getDamage() * delta);
		}
		return true;
	}
}
