package com.smeanox.games.ld34.world;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Comment
 */
public class PhysicSimulation implements Updatable {

	private World world;

	private List<Rigidbody> rigidbodies;
	private List<Collidable> collidables;

	public PhysicSimulation(World world) {
		this.world = world;

		rigidbodies = new ArrayList<Rigidbody>();
		collidables = new ArrayList<Collidable>();
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

	@Override
	public void update(float delta) {
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
