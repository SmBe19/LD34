package com.smeanox.games.ld34.world;

import com.smeanox.games.ld34.Consts;

/**
 * Comment
 */
public class PlantParticle extends ParticleSystem.Particle {

	private World world;

	public PlantParticle(World world, ParticleSystem particleSystem, float time, float x, float y, float vx, float vy) {
		super(particleSystem, time, x, y, vx, vy);
		this.world = world;
	}

	@Override
	public boolean collidesWith(Collidable collidable) {
		boolean collides = super.collidesWith(collidable);
		if (collides) {
			if (collidable instanceof Plant) {
				collides = false;
			}
		}
		return collides;
	}

	@Override
	public boolean onCollision(Collidable collidable, float delta) {
		boolean acceptCollision = super.onCollision(collidable, delta);
		if (collidable instanceof GroundPart) {
			GroundPart gp = (GroundPart) collidable;
			float height = -1;
			for (Building b : gp.getBuildings()) {
				if (b.getX() < getX() && getX() < b.getX() + b.getWidth()) {
					height = b.getHeight();
				}
			}
			if (height > 0) {
				gp.getPlants().add(PlantFactory.justGimmeTheFrikkinNoicePlantPlox(world, getX(), Consts.GROUND_HEIGHT, height));
			}else{
				gp.getPlants().add(new UselessPlant(world, getX(), Consts.GROUND_HEIGHT));

			}
			destroy();

		}
		return acceptCollision;
	}
}
