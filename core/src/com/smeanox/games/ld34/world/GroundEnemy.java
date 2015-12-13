package com.smeanox.games.ld34.world;

import com.badlogic.gdx.graphics.Color;
import com.smeanox.games.ld34.Consts;
import com.smeanox.games.ld34.Textures;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.Texture;

public class GroundEnemy extends Enemy {

	float animationTime = 0;
	Animation animation;

	private ParticleSystem destroySystem, bloodSystem;

	public GroundEnemy(World world, float x, float y) {
		super(world, x, y);
		Texture texture = Textures.get().enemy;
		int type = MathUtils.random(0,3);
		animation = Hero.createAnimation(texture, type, type+1, 0, 4, Consts.ENEMY_TEX_WIDTH, Consts.ENEMY_TEX_HEIGHT, 0.1f, Animation.PlayMode.LOOP);
		initParticles();
	}

	private void initParticles(){
		destroySystem = new ParticleSystem(world, "groundEnemyDestroy", null, Consts.LAYER_PLANT, Textures.get().particle, new Color(0.8f, 0.7f, 0.4f, 1), 0.5f, 1f, 0.2f, 0.001f, 0.0005f, 0, 0, 2, 2, 0, 0, 400, 600);
		bloodSystem = new ParticleSystem(world, "groundEnemyBlood", null, Consts.LAYER_PLANT, Textures.get().particle, new Color(0.8f, 0.4f, 0.3f, 1), 0.5f, 1f, 0.2f, 0.001f, 0.0005f, 0, 0, 2, 2, 0, 0, 400, 400);
	}

	public float getDamage() {
		return Consts.ENEMY_DAMAGE_PER_SECOND;
	}

	@Override
	public float getDamageOnTop() {
		return Consts.ENEMY_DAMAGE_ON_TOP;
	}

	@Override
	public float getWidth() {
		return Consts.ENEMY_TEX_WIDTH * Consts.ENEMY_TEX_ZOOM;
	}

	@Override
	public float getHeight() {
		return Consts.ENEMY_TEX_HEIGHT * Consts.ENEMY_TEX_ZOOM;
	}

	public void update(float delta) {
		super.update(delta);
		animationTime += delta;

		if(Math.max(Math.abs(x - world.getHero().getX()), Math.abs(y - world.getHero().getY())) < Consts.ENEMY_DIST_TO_HERO) {
			vx = -Consts.ENEMY_SPEED;
		} else {
			vx = 0;
		}
	}

	public Rectangle getCollisionBox() {
		return new Rectangle(x,y,32,32);
	}

	public boolean collidesWith(Collidable collidable) {
		if (collidable instanceof Building) {
			if(vy < 0 && y - vy/60 + 2 + Consts.PLANT_TOP_MARGIN > ((Building) collidable).getHeight() + ((Building) collidable).getY()){
				return true;
			}
			return false;
		}
		return collidable instanceof GroundPart || collidable instanceof Hero;
	}

	public void render(float delta, SpriteBatch batch) {
		batch.draw(animation.getKeyFrame(animationTime), x, y, Consts.ENEMY_TEX_WIDTH * Consts.ENEMY_TEX_ZOOM, Consts.ENEMY_TEX_HEIGHT * Consts.ENEMY_TEX_ZOOM);
	}

	@Override
	public void addLives(float lives) {
		super.addLives(lives);

		if(lives < 0){
			spawnBloodSystem();
		}
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
		destroySystem.setStartX(x + getWidth() / 2);
		destroySystem.setStartY(y + getHeight() / 2);
		destroySystem.setGenerating(true);
		destroySystem.setAutoDisable(0.2f);
	}

	private void spawnBloodSystem(){
		bloodSystem.setStartX(x + getWidth() / 2);
		bloodSystem.setStartY(y + getHeight() / 2);
		bloodSystem.setGenerating(true);
		bloodSystem.setAutoDisable(0.2f);
	}
}
