package com.smeanox.games.ld34.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.smeanox.games.ld34.Consts;
import com.smeanox.games.ld34.Textures;

/**
 * Comment
 */
public class Hero extends Rigidbody implements Updatable, Renderable {

	private World world;
	private Texture texture;
	private Animation activeAnimation, walk, axeSwing, throwPlant, fall;

	private float animationTime;

	public Hero(World world) {
		this.world = world;
		world.getUpdatables().add(this);
		world.getRenderables().add(this);
		world.getPhysics().addRigidbody(this);

		texture = Textures.get().hero;

		x = y = vx = vy = 0;

		initAnimations();
	}

	private void initAnimations(){
		Array<TextureRegion> regions;

		// walk
		regions = new Array<TextureRegion>();
		for(int y = 1; y < 2; y++){
			for(int x = 0; x < 6; x++){
				regions.add(new TextureRegion(texture, Consts.HERO_TEX_WIDTH*x, Consts.HERO_TEX_HEIGHT*y, Consts.HERO_TEX_WIDTH, Consts.HERO_TEX_HEIGHT));
			}
		}
		walk = new Animation(0.1f, regions);
		walk.setPlayMode(Animation.PlayMode.LOOP);

		// axeSwing
		regions = new Array<TextureRegion>();
		for(int y = 5; y < 6; y++){
			for(int x = 0; x < 8; x++){
				regions.add(new TextureRegion(texture, Consts.HERO_TEX_WIDTH*x, Consts.HERO_TEX_HEIGHT*y, Consts.HERO_TEX_WIDTH, Consts.HERO_TEX_HEIGHT));
			}
		}
		axeSwing = new Animation(0.08f, regions);
		axeSwing.setPlayMode(Animation.PlayMode.LOOP);

		// throwPlant
		regions = new Array<TextureRegion>();
		for(int y = 2; y < 3; y++){
			for(int x = 0; x < 5; x++){
				regions.add(new TextureRegion(texture, Consts.HERO_TEX_WIDTH*x, Consts.HERO_TEX_HEIGHT*y, Consts.HERO_TEX_WIDTH, Consts.HERO_TEX_HEIGHT));
			}
		}
		throwPlant = new Animation(0.08f, regions);
		throwPlant.setPlayMode(Animation.PlayMode.LOOP);

		// fall
		regions = new Array<TextureRegion>();
		for(int y = 6; y < 7; y++){
			for(int x = 0; x < 4; x++){
				regions.add(new TextureRegion(texture, Consts.HERO_TEX_WIDTH*x, Consts.HERO_TEX_HEIGHT*y, Consts.HERO_TEX_WIDTH, Consts.HERO_TEX_HEIGHT));
			}
		}
		fall = new Animation(0.15f, regions);
		fall.setPlayMode(Animation.PlayMode.LOOP);

		activeAnimation = walk;
	}

	@Override
	public void update(float delta){
		if(isFalling()){
			if(activeAnimation != fall){
				activeAnimation = fall;
				animationTime = 0;
			}
		} else {
			if (activeAnimation != walk && (activeAnimation.isAnimationFinished(animationTime) || activeAnimation == fall)) {
				activeAnimation = walk;
				animationTime = 0;
			}
		}

		if(activeAnimation == walk || activeAnimation == fall) {
			vx = Consts.HERO_VELO;
		} else {
			vx = 0;
		}
	}

	@Override
	public void render(float delta, SpriteBatch spriteBatch) {
		animationTime += delta;

		spriteBatch.draw(activeAnimation.getKeyFrame(animationTime), x, y, Consts.HERO_TEX_WIDTH * Consts.HERO_TEX_ZOOM, Consts.HERO_TEX_HEIGHT * Consts.HERO_TEX_ZOOM);
	}

	public void attack(){
		if(activeAnimation != walk){
			return;
		}
		activeAnimation = axeSwing;
		animationTime = 0;
	}

	public void plant(){
		if(activeAnimation != walk){
			return;
		}
		activeAnimation = throwPlant;
		animationTime = 0;
	}

	public float getY() {
		return y;
	}

	public float getX() {
		return x;
	}

	@Override
	public Rectangle getCollisionBox() {
		return new Rectangle(x, y, Consts.HERO_TEX_WIDTH * Consts.HERO_TEX_ZOOM, Consts.HERO_TEX_HEIGHT * Consts.HERO_TEX_ZOOM);
	}
}
