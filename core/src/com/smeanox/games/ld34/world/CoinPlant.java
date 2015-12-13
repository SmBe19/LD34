package com.smeanox.games.ld34.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
		super(world, x0, y0);
		this.moneyLog = moneyLog;
		this.colorNum = colorNum;
		this.color = colors[colorNum];
		lives = Consts.COIN_START_LIVES;
		didGiveMoney = false;
		initAnimation();
		initParticles();
	}

	private void initAnimation(){
		if(animation != null){
			return;
		}
		animation = Hero.createAnimation(Textures.get().coin, 0, 1, 0, 6, Consts.COIN_TEX_WIDTH, Consts.COIN_TEX_HEIGHT, 0.15f, Animation.PlayMode.LOOP);
		animationTime = 0;
	}

	private void initParticles(){
		destroySystem = new ParticleSystem(world, "coinDestroy", null, Consts.LAYER_PLANT, Textures.get().particle, color, 0.5f, 1f, 0.2f,
				(colors.length - colorNum)*(colors.length - colorNum) * 0.0001f,
				(colors.length - colorNum)*(colors.length - colorNum) * 0.00005f,
				x0, y0 + getHeight() / 2, 2, 2, 0, 0, (1 + colorNum*colorNum) * 200, (1 + colorNum*colorNum) * 200);
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
		return new Rectangle(x0 - Consts.COIN_TEX_WIDTH * Consts.COIN_TEX_ZOOM / 2, y0, Consts.COIN_TEX_WIDTH * Consts.COIN_TEX_ZOOM, Consts.COIN_TEX_HEIGHT * Consts.COIN_TEX_ZOOM);
	}

	@Override
	public boolean onCollision(Collidable collidable, float delta) {
		if(collidable instanceof Hero){
			GameState.get().addMoney(1 << moneyLog);
			didGiveMoney = true;
			destroy();
		}
		return true;
	}

	@Override
	public void render(float delta, SpriteBatch spriteBatch) {
		animationTime += delta;
		spriteBatch.setColor(color);
		spriteBatch.draw(animation.getKeyFrame(animationTime), x0 - Consts.COIN_TEX_WIDTH * Consts.COIN_TEX_ZOOM / 2, y0,
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
		destroySystem.setGenerating(true);
		destroySystem.setAutoDisable(0.2f);
	}
}
