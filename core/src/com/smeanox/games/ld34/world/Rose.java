package com.smeanox.games.ld34.world;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.smeanox.games.ld34.Consts;
import com.smeanox.games.ld34.Textures;

/**
 * Comment
 */
public class Rose extends Plant {
	private boolean destroyed = false;

	private Rectangle collisionBox;

	public Rose(World world, float x, float y){
		super(world, x, y, Consts.ROSE_START_LIVES);

		collisionBox = new Rectangle();

	}

	public Rectangle getCollisionBox() {
		return collisionBox.set(getX() - Consts.ROSE_TEX_WIDTH * Consts.ROSE_TEX_ZOOM / 2, getY(), Consts.ROSE_TEX_WIDTH * Consts.ROSE_TEX_ZOOM, Consts.ROSE_TEX_HEIGHT * Consts.ROSE_TEX_ZOOM);
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public boolean isDestroyed() {
		return destroyed;
	}

	@Override
	public boolean onCollision(Collidable collidable, float delta) {
		if(collidable instanceof Hero){
			//maybe spawn fancy particles
			world.getHero().addLives(Consts.ROSE_HP_BONUS * GameState.get().getHeroHealth());
			destroy();
		}
		return true;
	}



	public float getWidth() {
		return Consts.ROSE_TEX_WIDTH * Consts.ROSE_TEX_ZOOM;
	}

	public float getHeight() {
		return Consts.ROSE_TEX_HEIGHT * Consts.ROSE_TEX_ZOOM;
	}


	public void render(float delta, SpriteBatch batch) {
		batch.draw(Textures.get().rose, getX() - Consts.ROSE_TEX_WIDTH * Consts.ROSE_TEX_ZOOM / 2, getY(),
				Consts.ROSE_TEX_WIDTH * Consts.ROSE_TEX_ZOOM, Consts.ROSE_TEX_HEIGHT * Consts.ROSE_TEX_ZOOM);
	}


	public void grow(float delta) {
	}

}
