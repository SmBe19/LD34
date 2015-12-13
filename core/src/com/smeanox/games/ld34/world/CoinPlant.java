package com.smeanox.games.ld34.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.smeanox.games.ld34.Consts;
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

	public static final Color[] colors = {new Color(0.5f, 0.3f, 0.1f, 1), new Color(0.8f, 0.4f, 0.1f, 1), new Color(0.6f, 0.7f, 0.8f, 1), new Color(0.9f, 0.9f, 0, 1), new Color(0.4f, 0, 0.9f, 1)};

	public CoinPlant(World world, float x0, float y0, int moneyLog, int colorNum) {
		super(world, x0, y0, Consts.COIN_START_LIVES);
		this.moneyLog = moneyLog;
		this.colorNum = colorNum;
		this.color = colors[colorNum];
		lives = Consts.COIN_START_LIVES;
		didGiveMoney = false;
		initAnimation();
		initParticles();
	}

	private void initAnimation(){
		if(animation == null){
			animation = Hero.createAnimation(Textures.get().coin, 0, 1, 0, 6, Consts.COIN_TEX_WIDTH, Consts.COIN_TEX_HEIGHT, 0.15f, Animation.PlayMode.LOOP);
		}
		animationTime = MathUtils.random(0, animation.getAnimationDuration());
	}

	private void initParticles(){
		destroySystem = new ParticleSystem(world, "coinDestroy", new CoinParticleFactory(), Consts.LAYER_PLANT, Textures.get().particle, new Color(color.r, color.g, color.b, 1), 0.5f, 5f, 0.2f,
				0.2f / (colorNum + 1) * 1f / 128,
				0.2f / (colorNum + 1) * 1f / 256,
				getX(), getY() + getHeight() / 2, 2, 2, 0,0 ,Consts.COIN_VELOCITY, Consts.COIN_VELOCITY);//(1 + colorNum*colorNum) * Consts.COIN_VELOCITY, (1 + colorNum*colorNum)* Consts.COIN_VELOCITY);
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
		return new Rectangle(getX() - Consts.COIN_TEX_WIDTH * Consts.COIN_TEX_ZOOM / 2, getY(), Consts.COIN_TEX_WIDTH * Consts.COIN_TEX_ZOOM, Consts.COIN_TEX_HEIGHT * Consts.COIN_TEX_ZOOM);
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
		if(didGiveMoney) {
			spawnDestroySystem();
		}
	}

	private void spawnDestroySystem(){
		GameState.get().addMoney((1 << moneyLog));
		destroySystem.setGenerating(true);
		destroySystem.setAutoDisable(0.2f);
	}


	public class CoinParticleFactory implements ParticleSystem.ParticleFactory {

		@Override
		public ParticleSystem.Particle createParticle(ParticleSystem ps, float time, float x, float y, float vx, float vy) {
			return new CoinParticle(world, ps, time, x, y, vx, vy, moneyLog);
		}
	}
}
