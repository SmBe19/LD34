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
		for (Rigidbody rigidbody : new ArrayList<Rigidbody>(rigidbodies)) {
			for (Collidable collidable : collidables) {
				if (collidable == rigidbody || !rigidbody.collidesWith(collidable)) {
					continue;
				}
				Rectangle collisionBox = rigidbody.getCollisionBox();
				Rectangle collisionBoxY = new Rectangle(collisionBox.getX() + collisionBox.getWidth() / 4,
						collisionBox.getY(), collisionBox.getWidth() / 2, collisionBox.getHeight());

				Rectangle collisionBoxCollidable = collidable.getCollisionBox();
				boolean collision = false;
				if (collisionBoxCollidable.overlaps(collisionBoxY)) {
					float diff = 0;
					if (collisionBoxY.getY() + collisionBoxY.getHeight() / 2
							< collisionBoxCollidable.getY() + collisionBoxCollidable.getHeight() / 2){
						diff = (collisionBoxY.getY() + collisionBoxY.getHeight()) - collisionBoxCollidable.getY();
					} else {
						diff = collisionBoxY.getY() - (collisionBoxCollidable.getY() + collisionBoxCollidable.getHeight());
					}
					rigidbody.collisionY(diff);
					collision = true;
				}

				collisionBox = rigidbody.getCollisionBox();
				Rectangle collisionBoxX = new Rectangle(collisionBox.getX(),
						collisionBox.getY() + collisionBox.getHeight() / 4, collisionBox.getWidth(),
						collisionBox.getHeight() / 2);

				if (collisionBoxCollidable.overlaps(collisionBoxX)) {
					float diff = 0;
					if (collisionBoxX.getX() + collisionBoxX.getWidth() / 2
							< collisionBoxCollidable.getX() + collisionBoxCollidable.getWidth() / 2){
						diff = (collisionBoxX.getX() + collisionBoxX.getWidth()) - collisionBoxCollidable.getX();
					} else {
						diff = collisionBoxX.getX() - (collisionBoxCollidable.getX() + collisionBoxCollidable.getWidth());
					}
					rigidbody.collisionX(diff);
					collision = true;
				}
				if(collision) {
					rigidbody.onCollision(collidable);
					collidable.onCollision(rigidbody);
				}
			}
		}
	}
}
