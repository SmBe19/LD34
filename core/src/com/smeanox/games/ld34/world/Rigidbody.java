package com.smeanox.games.ld34.world;

import com.badlogic.gdx.math.MathUtils;
import com.smeanox.games.ld34.Consts;

/**
 * Comment
 */
public abstract class Rigidbody implements Collidable {

	protected float x, y, vx, vy;
	protected float fallingFor;
	protected float fallingFromY;
	protected float fallingFromX;

	public abstract boolean collidesWith(Collidable collidable);

	public void doPhysics(float delta){
		vy += Consts.GRAVITY * delta;
		x += vx * delta;
		y += vy * delta;
		if(vy < 0) {
			fallingFor += delta;
		} else {
			fallingFor = 0;
			fallingFromX = x;
			fallingFromY = y;
		}
	}

	public void collisionY(float diff){
		y -= diff;
		if(Math.abs(vy) > Consts.BOUNCINESS_MIN_Y){
			vy *= -Consts.BOUNCINESS;
		} else {
			vy = 0;
		}
		if (fallingFor > 0.5 && this instanceof  Hero){
			System.out.println("Jump from " + fallingFromX + "|" + fallingFromY + " to " + x + "|" + y + " in " + fallingFor);
		}
		fallingFor = 0;
		vx *= Consts.FRICTION;
	}

	public void collisionX(float diff){
		x -= diff;
		if(Math.abs(vx) > Consts.BOUNCINESS_MIN_X){
			vx *= -Consts.BOUNCINESS;
		} else {
			vx = 0;
		}
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
