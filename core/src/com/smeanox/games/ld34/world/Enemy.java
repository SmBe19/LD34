package com.smeanox.games.ld34.world;

import com.smeanox.games.ld34.Consts;

/**
 * Comment
 */
public abstract class Enemy extends Rigidbody implements Updatable, Renderable, Destroyable {
	private boolean destroyed = false;

	protected World world;

	protected float lives;

	public Enemy(World world, float x, float y){
		this.world = world;
		this.x = x;
		this.y = y;
		world.getUpdatables().add(this);
		world.addRenderable(Consts.LAYER_ENEMY, this);
		world.getPhysics().addRigidbody(this);
		world.getPhysics().addCollidable(this);

		lives = Consts.ENEMY_START_LIVES + x * Consts.ENEMY_LIVES_PER_DIST;
	}

	@Override
	public void update(float delta) {
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
		world.getRenderables(Consts.LAYER_ENEMY).remove(this);
		world.getPhysics().removeCollidable(this);
		world.getPhysics().removeRigidbody(this);
	}

	@Override
	public boolean isDestroyed() {
		return destroyed;
	}

	public abstract float getDamage();

	public abstract float getDamageOnTop();

	@Override
	public boolean onCollision(Collidable collidable, float delta) {
		if(collidable instanceof Hero){
			if(((Hero) collidable).getY() > y + getHeight() / 2){
				((Hero) collidable).addLives(-getDamageOnTop());
				destroy();
			} else {
				((Hero) collidable).addLives(-getDamage() * delta);
			}
		}
		return true;
	}

	public boolean isAlive() {
		return lives > 0;
	}

	public float getLives() {
		return lives;
	}

	public void setLives(float lives) {
		this.lives = lives;
	}

	public void addLives(float lives){
		this.lives += lives;
	}

	public abstract float getWidth();

	public abstract float getHeight();
}
