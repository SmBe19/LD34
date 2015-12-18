package com.smeanox.games.ld34.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.smeanox.games.ld34.Consts;
import com.smeanox.games.ld34.Sounds;
import com.smeanox.games.ld34.Textures;

/**
 * Comment
 */
public class CoinPlant extends Plant {

	private ParticleSystem destroySystem;

	private static Animation animation;
	private float animationTime;

	private int moneyLog;
	private int colorNum;
	private Color color;
	private boolean didGiveMoney;

	private Rectangle collisionBox;

	public static final Color[] colors = {new Color(0.5f, 0.3f, 0.1f, 1), new Color(0.8f, 0.4f, 0.1f, 1), new Color(0.6f, 0.7f, 0.8f, 1), new Color(0.9f, 0.9f, 0, 1), new Color(0.4f, 0, 0.9f, 1)};

	public CoinPlant(World world, float x0, float y0, int moneyLog, int colorNum) {
		super(world, x0, y0, Consts.COIN_START_LIVES);
		this.moneyLog = moneyLog;
		this.colorNum = colorNum;
		this.color = colors[colorNum];
		lives = Consts.COIN_START_LIVES;
		didGiveMoney = false;
		initAnimation();

		collisionBox = new Rectangle();
	}

	private void initAnimation(){
		if(animation == null){
			animation = Hero.createAnimation(Textures.get().coin, 0, 1, 0, 6, Consts.COIN_TEX_WIDTH, Consts.COIN_TEX_HEIGHT, 0.15f, Animation.PlayMode.LOOP);
		}
		animationTime = MathUtils.random(0, animation.getAnimationDuration());
	}


	@Override
	public void grow(float delta) {
	}

	@Override
	public float getWidth() {
		return Consts.COIN_TEX_WIDTH * Consts.COIN_TEX_ZOOM;
	}

	@Override
	public float getHeight() {
		return Consts.COIN_TEX_HEIGHT * Consts.COIN_TEX_ZOOM;
	}

	@Override
	public Rectangle getCollisionBox() {
		return collisionBox.set(getX() - Consts.COIN_TEX_WIDTH * Consts.COIN_TEX_ZOOM / 2, getY(), Consts.COIN_TEX_WIDTH * Consts.COIN_TEX_ZOOM, Consts.COIN_TEX_HEIGHT * Consts.COIN_TEX_ZOOM);
	}

	@Override
	public boolean onCollision(Collidable collidable, float delta) {
		if(collidable instanceof Hero){
			didGiveMoney = true;
			destroy();
		}
		return true;
	}

	@Override
	public void render(float delta, SpriteBatch spriteBatch) {
		animationTime += delta;
		spriteBatch.setColor(color);
		spriteBatch.draw(animation.getKeyFrame(animationTime), getX() - Consts.COIN_TEX_WIDTH * Consts.COIN_TEX_ZOOM / 2, getY(),
				Consts.COIN_TEX_WIDTH * Consts.COIN_TEX_ZOOM, Consts.COIN_TEX_HEIGHT * Consts.COIN_TEX_ZOOM);
		spriteBatch.setColor(Color.WHITE);
	}

	@Override
	public void destroy() {
		if(isDestroyed()){
			return;
		}
		super.destroy();
		spawnDestroySystem();
	}

	private void spawnDestroySystem(){
		if(didGiveMoney) {
			destroySystem = new ParticleSystem(world, new CoinParticleFactory(), Consts.LAYER_PLANT, Textures.get().particle, color, 4f, 5f, 0.2f,
					0.2f / (colorNum + 1) * 1f / 128,
					0.2f / (colorNum + 1) * 1f / 256,
					getX(), getY() + getHeight() / 2, 2, 2, 0, 0, Consts.COIN_VELOCITY, Consts.COIN_VELOCITY);//(1 + colorNum*colorNum) * Consts.COIN_VELOCITY, (1 + colorNum*colorNum)* Consts.COIN_VELOCITY);

			long money = 1L << moneyLog;
			if (money >= 0)
				GameState.get().addMoney((1l << moneyLog));

			Sounds.get().play(Sounds.get().coin);
		} else {
			Color pcolor = new Color(color);
			pcolor = pcolor.lerp(Color.BLACK, 0.3f);
			pcolor.a = 50;
			destroySystem = new ParticleSystem(world, new CoinDustParticleFactory(), Consts.LAYER_PLANT, Textures.get().particle, pcolor, 4f, 5f, 0.2f,
					0.2f / (colorNum + 1) * 1f / 32,
					0.2f / (colorNum + 1) * 1f / 64,
					getX(), getY() + getHeight() / 2, 2, 2, 0, 0, Consts.COIN_DUST_VELOCITY, Consts.COIN_DUST_VELOCITY);//(1 + colorNum*colorNum) * Consts.COIN_VELOCITY, (1 + colorNum*colorNum)* Consts.COIN_VELOCITY);

			if(!isAlive()) {
				Sounds.get().play(Sounds.get().destroyCoin);
			}
		}
		destroySystem.setGenerating(true);
		destroySystem.setAutoDisable(0.2f);
	}

	public class CoinParticleFactory implements ParticleSystem.ParticleFactory {

		@Override
		public ParticleSystem.Particle createParticle(ParticleSystem ps, float time, float x, float y, float vx, float vy) {
			return new CoinParticle(world, ps, time, x, y, vx, vy);
		}
	}

	public class CoinDustParticleFactory implements ParticleSystem.ParticleFactory {

		@Override
		public ParticleSystem.Particle createParticle(ParticleSystem ps, float time, float x, float y, float vx, float vy) {
			return new ParticleSystem.Particle( ps, time, x, y, vx, vy);
		}
	}
}
