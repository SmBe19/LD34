package com.smeanox.games.ld34.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.smeanox.games.ld34.Consts;
import com.smeanox.games.ld34.Textures;

import java.util.HashSet;
import java.util.Set;

/**
 * Comment
 */
public class Hero extends Rigidbody implements Updatable, Renderable {

	private World world;
	private Texture texture;
	private Animation activeAnimation, walk, axeSwingOld, axeSwing, throwPlant, fall, climbing;
	private Set<Plant> climbingPlants;
	private Plant climbingPlant;
	private float lives;

	private int spammingCount;
	private float lastAttack;

	private ParticleSystem bloodInDaFaceSystem, attackedSystem, attackSystem, plantSystem, walkSystem, landingSystem;

	private float animationTime;

	public Hero(World world) {
		this.world = world;
		world.getUpdatables().add(this);
		world.addRenderable(Consts.LAYER_HERO, this);
		world.getPhysics().addRigidbody(this);
		world.getPhysics().addCollidable(this);

		lives = GameState.get().getHeroHealth();

		texture = Textures.get().hero;

		x = y = vx = vy = 0;
		climbingPlants = new HashSet<Plant>();
		climbingPlant = null;

		initAnimations();

		initParticles();
	}

	private void initAnimations() {
		walk = createAnimation(texture, 1, 2, 0, 6, Consts.HERO_TEX_WIDTH, Consts.HERO_TEX_HEIGHT, 0.1f, Animation.PlayMode.LOOP);
		axeSwingOld = createAnimation(texture, 5, 6, 0, 8, Consts.HERO_TEX_WIDTH, Consts.HERO_TEX_HEIGHT, 0.08f, Animation.PlayMode.LOOP);
		axeSwing = createAnimation(texture, 7, 8, 0, 8, Consts.HERO_TEX_WIDTH, Consts.HERO_TEX_HEIGHT, 0.08f, Animation.PlayMode.LOOP);
		throwPlant = createAnimation(texture, 2, 3, 0, 5, Consts.HERO_TEX_WIDTH, Consts.HERO_TEX_HEIGHT, 0.08f, Animation.PlayMode.LOOP);
		fall = createAnimation(texture, 6, 7, 0, 4, Consts.HERO_TEX_WIDTH, Consts.HERO_TEX_HEIGHT, 0.15f, Animation.PlayMode.LOOP);
		climbing = createAnimation(texture, 3, 4, 0, 4, Consts.HERO_TEX_WIDTH, Consts.HERO_TEX_HEIGHT, 0.2f, Animation.PlayMode.LOOP);

		activeAnimation = walk;
	}

	public static Animation createAnimation(Texture texture, int startY, int endY, int startX, int endX, int texWidth, int texHeight, float duration, Animation.PlayMode playMode){
		Array<TextureRegion> regions;
		regions = new Array<TextureRegion>();
		for (int y = startY; y < endY; y++) {
			for (int x = startX; x < endX; x++) {
				regions.add(new TextureRegion(texture, texWidth * x, texHeight * y, texWidth, texHeight));
			}
		}
		Animation animation = new Animation(duration, regions);
		animation.setPlayMode(playMode);
		return animation;
	}

	private void initParticles() {
		bloodInDaFaceSystem = new ParticleSystem(world, "bloodInDaFace", null, Consts.LAYER_HERO, Textures.get().particle, Color.RED, 0.5f, 5f, 0.5f, 0.0005f, 0.0001f, 0, 0, 2, 2, 20, 100, 277, 277);
		attackedSystem = new ParticleSystem(world, "attacked", null, Consts.LAYER_HERO, Textures.get().particle, Color.RED, 0.5f, 5f, 0.5f, 0.01f, 0.005f, 0, 0, 2, 2, 0, 100, 177, 177);
		attackSystem = new ParticleSystem(world, "attack", null, Consts.LAYER_HERO, Textures.get().particle, new Color(0.5f, 0, 0, 1), 0.5f, 0.4f, 0.1f, 0.05f, 0.01f, 0, 0, 2, 2, -10, 50, 10, 20);
		walkSystem = new ParticleSystem(world, "walk", null, Consts.LAYER_HERO, Textures.get().particle, Color.BROWN, 0.5f, 0.4f, 0.1f, 0.2f, 0.1f, 0, 0, 2, 2, 0, 80, 50, 20);
		landingSystem = new ParticleSystem(world, "landing", null, Consts.LAYER_HERO, Textures.get().particle, Color.BROWN, 0.5f, 0.6f, 0.2f, 0.025f, 0.01f, 0, 0, 5, 2, 0, 50, 200, 20);
		plantSystem = new ParticleSystem(world, "plant", new PlantParticleFactory(), Consts.LAYER_HERO, Textures.get().particle, new Color(0, 0.8f, 0, 1), 1f, 10f, 0.1f, 0.05f, 0.04f, 0, 0, 1, 1, Consts.HERO_VELO * 2f, 0, 10, 10);
	}

	@Override
	public void update(float delta) {
		if (isFalling()) {
			if (activeAnimation != fall) {
				setAnimation(fall);
			}
		} else {
			if(activeAnimation == climbing && climbingPlant != null){
				if(y + Consts.PLANT_TOP_MARGIN > (climbingPlant).getHeight() + climbingPlant.getY0()){
					setAnimation(walk);
				}
			}
			if (activeAnimation != walk && activeAnimation != climbing && (activeAnimation.isAnimationFinished(animationTime) || activeAnimation == fall)) {
				setAnimation(walk);
			}
		}

		if (activeAnimation == walk || activeAnimation == fall) {
			vx = Consts.HERO_VELO;
			walkSystem.setGenerating(!isFalling());
			walkSystem.setStartX(x);
			walkSystem.setStartY(y);
		} else {
			if (activeAnimation == climbing) {
				vy = Consts.HERO_CLIMB_VELO;
			}
			vx = 0;
			walkSystem.setGenerating(false);
		}

		walkSystem.setStartX(x + 8 * Consts.HERO_TEX_ZOOM);
		walkSystem.setStartY(y + 2 * Consts.HERO_TEX_ZOOM);

		if (y < -Consts.HEIGHT * 10) {
			lives = 0;
		}
	}

	@Override
	public void render(float delta, SpriteBatch spriteBatch) {
		animationTime += delta;

		spriteBatch.draw(activeAnimation.getKeyFrame(animationTime), x, y, Consts.HERO_TEX_WIDTH * Consts.HERO_TEX_ZOOM, Consts.HERO_TEX_HEIGHT * Consts.HERO_TEX_ZOOM);
	}

	public void attack() {
		if (activeAnimation == climbing) {
			setAnimation(fall);
			vy = Consts.HERO_JUMP_VELO;
			return;
		}

		if(world.getTotalTime() - lastAttack < Consts.HERO_SPAMMING_TIME_LIMIT){
			spammingCount++;
		} else {
			spammingCount = 0;
		}
		lastAttack = world.getTotalTime();

		if (activeAnimation != walk) {
			return;
		}
		setAnimation(axeSwing);

		if (MathUtils.randomBoolean(0.5f)) {
			spawnAttackSystem();
		}
		if (MathUtils.randomBoolean(0.01f + (float)spammingCount / Consts.HERO_SPAMMING_LIMIT)) {
			setAnimation(axeSwingOld);
			spawnBloodInDaFaceSystem();
		}

		GroundPart groundPart = world.getGroundPart(x);
		if(groundPart != null) {
			boolean damaged = false;
			for (Enemy enemy : groundPart.getEnemies()) {
				if (!enemy.isAlive()) {
					continue;
				}
				if (enemy.getX() > x && enemy.getX() - x < Consts.HERO_ATTACK_RANGE_X && Math.abs(enemy.getY() + enemy.getHeight() / 2 - (y + getHeight() / 2)) < Consts.HERO_ATTACK_RANGE_Y) {
					enemy.addLives(-GameState.get().getHeroDamage());
					damaged = true;
					break;
				}
			}
			if(!damaged) {
				for (Plant plant : groundPart.getPlants()) {
					if(!plant.isAlive()){
						continue;
					}
					if(plant instanceof Vine){
						continue;
					}
					float plantPosX = plant.getX0() - plant.getWidth() / 2;
					float plantPosY = plant.getY0() + plant.getHeight() / 2;
					if (plantPosX > x && plantPosX - x < Consts.HERO_ATTACK_RANGE_X && Math.abs(plantPosY - (y + getHeight() / 2)) < Consts.HERO_ATTACK_RANGE_Y) {
						plant.addLives(-Consts.HERO_DAMAGE);
						damaged = true;
						break;
					}
				}
			}
		}
	}

	public void plant() {
		if(activeAnimation == climbing){
			setAnimation(walk);
			plantOnePlant();
			vy = Consts.HERO_JUMP_VELO * 0.5f;
			return;
		}
		if (activeAnimation != walk) {
			return;
		}
		setAnimation(throwPlant);

		spawnPlantSystem();
	}

	private void plantOnePlant(){
		GroundPart groundPart = world.getGroundPart(x);
		if(groundPart != null){
			float newX = x + Consts.VINE_ON_CLIMBING_OFFSET_X;
			float newY = y + Consts.VINE_ON_CLIMBING_OFFSET_Y;
			float height = -1;
			for (Building b : groundPart.getBuildings()) {
				if (b.getX() < newX && newX < b.getX() + b.getWidth()) {
					height = b.getHeight() + b.getY();
				}
			}
			if (height > 0) {
				groundPart.getPlants().add(PlantFactory.justGimmeTheFrikkinNoicePlantPlox(world, newX, newY, height - newY));
			}
		}
	}

	private void setAnimation(Animation animation) {
		activeAnimation = animation;
		animationTime = 0;
	}

	private void spawnAttackSystem() {
		attackSystem.setStartX(x + 14 * Consts.HERO_TEX_ZOOM);
		attackSystem.setStartY(y + 13 * Consts.HERO_TEX_ZOOM);
		attackSystem.setGenerating(false);
		attackSystem.setAutoDisable(0.1f);
		attackSystem.setTimeout(0.5f);
	}

	private void spawnBloodInDaFaceSystem() {
		bloodInDaFaceSystem.setStartX(x + 17 * Consts.HERO_TEX_ZOOM);
		bloodInDaFaceSystem.setStartY(y + 13 * Consts.HERO_TEX_ZOOM);
		bloodInDaFaceSystem.setGenerating(false);
		bloodInDaFaceSystem.setAutoDisable(0.1f);
		bloodInDaFaceSystem.setTimeout(0.5f);
	}

	private void spawnAttackedSystem() {
		attackedSystem.setStartX(x + 12 * Consts.HERO_TEX_ZOOM);
		attackedSystem.setStartY(y + 13 * Consts.HERO_TEX_ZOOM);
		attackedSystem.setGenerating(true);
		attackedSystem.setAutoDisable(0.1f);
	}

	private void spawnPlantSystem() {
		plantSystem.setStartX(x + 24 * Consts.HERO_TEX_ZOOM);
		plantSystem.setStartY(y + 12 * Consts.HERO_TEX_ZOOM);
		plantSystem.setGenerating(false);
		plantSystem.setOneParticleTimeout(0.32f);
	}

	private void spawnLandingSystem() {
		landingSystem.setStartX(x + Consts.HERO_TEX_WIDTH / 2 * Consts.HERO_TEX_ZOOM);
		landingSystem.setStartY(y + 2 * Consts.HERO_TEX_ZOOM);
		landingSystem.setAutoDisable(0.5f);
		landingSystem.setGenerating(true);
	}

	public float getY() {
		return y;
	}

	public float getX() {
		return x;
	}

	public float getWidth(){
		return Consts.HERO_TEX_WIDTH * Consts.HERO_TEX_ZOOM;
	}

	public float getHeight(){
		return Consts.HERO_TEX_HEIGHT * Consts.HERO_TEX_ZOOM;
	}

	public float getLives() {
		return lives;
	}

	public void setLives(float lives) {
		this.lives = lives;
	}

	public void addLives(float lives){
		this.lives += lives;

		if(lives < 0){
			spawnAttackedSystem();

			world.setCameraShake((float) (Math.sqrt(-lives) * Consts.CAMERA_SHAKE_ATTACK_PER_DAMAGE));
		}
		if (lives > GameState.get().getHeroHealth()){
			lives = GameState.get().getHeroHealth();
		}
	}

	public boolean isAlive() {
		return lives > 0;
	}

	@Override
	public Rectangle getCollisionBox() {
		return new Rectangle(x, y, getWidth(), getHeight());
	}

	@Override
	public boolean onCollision(Collidable collidable, float delta) {
		if (collidable instanceof Vine) {
			if (activeAnimation == climbing) {
				return false;
			}
			setAnimation(climbing);
			climbingPlants.add((Plant) collidable);
			climbingPlant = (Plant) collidable;

			fallingFor = 0;
			return false;
		}
		if (collidable instanceof GroundPart){
			climbingPlants.clear();
		}
		if(vy < Consts.GRAVITY * 0.1f){
			spawnLandingSystem();

			world.setCameraShake(Consts.CAMERA_SHAKE_LANDING_PER_VELO_Y * vy * vy);
		}
		return true;
	}

	@Override
	public boolean collidesWith(Collidable collidable) {
		if (collidable instanceof Building) {
			if(vy < 0 && y - vy/60 + 2 + Consts.PLANT_TOP_MARGIN > ((Building) collidable).getHeight() + ((Building) collidable).getY()){
				return true;
			}
			return false;
		}
		if (collidable instanceof Vine) {
			if (((Vine) collidable).getX0() < x + getWidth() / 2 && !climbingPlants.contains(collidable)) {
				return true;
			}
			return false;
		}
		if (collidable instanceof CoinParticle){
			return true;
		}
		if (collidable instanceof ParticleSystem.Particle){
			return false;
		}

		return true;
	}

	public class PlantParticleFactory implements ParticleSystem.ParticleFactory {

		@Override
		public ParticleSystem.Particle createParticle(ParticleSystem ps, float time, float x, float y, float vx, float vy) {
			return new PlantParticle(world, ps, time, x, y, vx, vy);
		}
	}

}
