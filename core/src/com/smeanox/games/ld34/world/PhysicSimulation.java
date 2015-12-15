package com.smeanox.games.ld34.world;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.smeanox.games.ld34.Consts;

import java.util.ArrayList;
import java.util.List;

/**
 * Comment
 */
public class PhysicSimulation implements Updatable {

	private World world;

	private List<Rigidbody> rigidbodies;
	private List<Collidable> collidables;
	private float totalDelta = 0;
	private float tickRate = Consts.PHYSICS_TICKRATE;
	private float upssum = 0;
	private int upscnt = 0;
	private float pupssum = 0;
	private int rateAdjustments = 1;
	private int pupscnt = 0;
	private float avgups = 60f;
	private float particleRate = -1f;
	private float maxDelta = 0f;

	public PhysicSimulation(World world) {
		this.world = world;

		rigidbodies = new ArrayList<Rigidbody>();
		collidables = new ArrayList<Collidable>();
		if (Gdx.app.getType() == Application.ApplicationType.Android){
			tickRate = Consts.PHYSICS_TICKRATE_MOBILE;
		}
	}

	public void addRigidbody(Rigidbody rigidbody) {
		rigidbodies.add(rigidbody);
	}

	public void addCollidable(Collidable collidable) {
		collidables.add(collidable);
	}

	public void removeRigidbody(Rigidbody rigidbody) {
		rigidbodies.remove(rigidbody);
	}

	public void removeCollidable(Collidable collidable) {
		collidables.remove(collidable);
	}


	public float getUpdatesPerSecond(){
		return avgups;
	}

	public float getParticleRateMultiplier(){

		if (particleRate < 0){
			particleRate = GameState.get().getParticleRate();
			System.out.println("Loaded particle rate multiplier of " + particleRate);
			if (Math.abs(particleRate - 1) > 0.001f){
				rateAdjustments = 100;
			}
		}
		return particleRate;
	}

	@Override
	public void update(float delta) {
		upssum += delta;
		maxDelta = Math.max(delta, maxDelta);
		upscnt ++;
		if (upscnt > 300) {
			avgups = (1f / (upssum / upscnt)) / 1f;
			upscnt = 0;
			upssum = 0;
			int targetUPS = Consts.TARGET_UPS;
			if (Gdx.app.getType() == Application.ApplicationType.Android){
				targetUPS = Consts.TARGET_UPS_MOBILE;
			}
			float targetStutter = Consts.TARGET_STUTTER;
			if (Gdx.app.getType() == Application.ApplicationType.Android){
				targetStutter = Consts.TARGET_STUTTER_MOBILE;
			}
			//System.out.println(avgups + " ups / " + targetUPS);
			//System.out.println("stutter: " + maxDelta  + " / " + targetStutter);

			getParticleRateMultiplier();
			//System.out.println(Math.pow(rateAdjustments, 0.33f));
			if ((avgups < targetUPS || maxDelta > targetStutter) && rateAdjustments != 1 && rateAdjustments != 100){
				particleRate /= 1f + 3.5f / Math.pow(rateAdjustments, 0.33f);
			}else{
				particleRate *= 1f + 0.15f / Math.pow(rateAdjustments, 0.33f);
			}
			maxDelta = 0;
			//System.out.println(particleRate);
			GameState.get().setParticleRate(particleRate);
			rateAdjustments ++;
		}
		totalDelta += delta;
		if (totalDelta < tickRate) return;
		if (pupscnt > 120) {
			pupscnt = 0;
			pupssum = 0;
		}
		delta = totalDelta;
		pupssum += delta;
		pupscnt ++;
		totalDelta = 0;
		for (Rigidbody rigidbody : rigidbodies) {
			rigidbody.doPhysics(delta);
		}
		Rectangle collisionBoxY = new Rectangle();
		Rectangle collisionBoxX = new Rectangle();
		for (Rigidbody rigidbody : new ArrayList<Rigidbody>(rigidbodies)) {
			for (Collidable collidable : new ArrayList<Collidable>(collidables)) {
				Rectangle collisionBox = rigidbody.getCollisionBox();
				Rectangle collisionBoxCollidable = collidable.getCollisionBox();

				if(!collisionBoxCollidable.overlaps(collisionBoxCollidable)){
					continue;
				}

				if (collidable == rigidbody || !rigidbody.collidesWith(collidable)) {
					continue;
				}
				collisionBoxY.set(collisionBox.getX() + collisionBox.getWidth() / 4,
						collisionBox.getY(), collisionBox.getWidth() / 2, collisionBox.getHeight());

				boolean collision = false;
				float diffY = 0;
				float diffX = 0;
				if (collisionBoxCollidable.overlaps(collisionBoxY)) {
					if (collisionBoxY.getY() + collisionBoxY.getHeight() / 2
							< collisionBoxCollidable.getY() + collisionBoxCollidable.getHeight() / 2){
						diffY = (collisionBoxY.getY() + collisionBoxY.getHeight()) - collisionBoxCollidable.getY();
					} else {
						diffY = collisionBoxY.getY() - (collisionBoxCollidable.getY() + collisionBoxCollidable.getHeight());
					}
					collision = true;
				}

				collisionBoxX.set(collisionBox.getX(),
						collisionBox.getY() + collisionBox.getHeight() / 4 - diffY, collisionBox.getWidth(),
						collisionBox.getHeight() / 2);

				if (collisionBoxCollidable.overlaps(collisionBoxX)) {
					if (collisionBoxX.getX() + collisionBoxX.getWidth() / 2
							< collisionBoxCollidable.getX() + collisionBoxCollidable.getWidth() / 2) {
						diffX = (collisionBoxX.getX() + collisionBoxX.getWidth()) - collisionBoxCollidable.getX();
					} else {
						diffX = collisionBoxX.getX() - (collisionBoxCollidable.getX() + collisionBoxCollidable.getWidth());
					}
					collision = true;
				}
				if(collision) {
					boolean acceptCollision = rigidbody.onCollision(collidable, delta);
					collidable.onCollision(rigidbody, delta);

					if(acceptCollision){
						if(Math.abs(diffY) > 0.1e-10){
							rigidbody.collisionY(diffY);
						}
						if(Math.abs(diffX) > 0.1e-10){
							rigidbody.collisionX(diffX);
						}
					}
				}
			}
		}
	}
}
