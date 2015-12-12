package com.smeanox.games.ld34.world;

import com.smeanox.games.ld34.Consts;

/**
 * Comment
 */
public abstract class Rigidbody implements Collidable {

	protected float x, y, vx, vy;
	protected float fallingFor;

	public abstract boolean collidesWith(Collidable collidable);

	public void doPhysics(float delta){
		vy += Consts.GRAVITY * delta;
		x += vx;
		y += vy;
		fallingFor += delta;
	}

	public void collisionY(float diff){
		y -= diff;
		vy = 0;
		fallingFor = 0;
		vx *= Consts.FRICTION;
	}

	public void collisionX(float diff){
		x -= diff;
		vx = 0;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getVx() {
		return vx;
	}

	public float getVy() {
		return vy;
	}

	public boolean isFalling() {
		return fallingFor > Consts.FALLING_FOR_LIMIT;
	}
}
