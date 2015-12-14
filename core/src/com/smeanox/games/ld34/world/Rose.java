package com.smeanox.games.ld34.world;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.smeanox.games.ld34.Consts;
import com.smeanox.games.ld34.Sounds;
import com.smeanox.games.ld34.Textures;

/**
 * Comment
 */
public class Rose extends Plant {
	private boolean destroyed = false;

	private ParticleSystem destroySystem;

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
			world.getHero().addLives(Consts.ROSE_HP_BONUS * GameState.get().getHeroHealth());

			Sounds.get().play(Sounds.get().rose);

			spawnDestroySystem();
			destroy();
		}
		return true;
	}

	private void spawnDestroySystem() {
		destroySystem = new ParticleSystem(world, new CoinParticleFactory(), Consts.LAYER_PLANT, Textures.get().particle, new Color(1, 0, 0, 1), 4f, 5f, 0.2f,
				0.1f * 1f / 128,
				0.1f * 1f / 256,
				getX(), getY() + getHeight() / 2, 2, 2, 0, 0, Consts.COIN_VELOCITY, Consts.COIN_VELOCITY);//(1 + colorNum*colorNum) * Consts.COIN_VELOCITY, (1 + colorNum*colorNum)* Consts.COIN_VELOCITY);
		destroySystem.setGenerating(true);
		destroySystem.setAutoDisable(0.2f);
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

	public class CoinParticleFactory implements ParticleSystem.ParticleFactory {

		@Override
		public ParticleSystem.Particle createParticle(ParticleSystem ps, float time, float x, float y, float vx, float vy) {
			return new CoinParticle(world, ps, time, x, y, vx, vy);
		}
	}
}
