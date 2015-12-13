package com.smeanox.games.ld34.world;

import com.badlogic.gdx.math.Rectangle;

/**
 * Comment
 */
public interface Collidable {
	Rectangle getCollisionBox();

	boolean onCollision(Collidable collidable, float delta);
}
