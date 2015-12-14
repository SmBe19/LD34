package com.smeanox.games.ld34.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.smeanox.games.ld34.Consts;
import com.smeanox.games.ld34.Sounds;
import com.smeanox.games.ld34.Textures;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Thorn extends Plant {

	private ParticleSystem destroySystem;

	private Rectangle collisionBox;

	public Thorn(World world, float x0, float y0) {
		super(world, x0, y0,Consts.THORN_START_LIVES + x0 * Consts.THORN_LIVES_PER_DIST);
		initParticles();

		collisionBox = new Rectangle();
	}

	private void initParticles(){
		destroySystem = new ParticleSystem(world, null, Consts.LAYER_PLANT, Textures.get().particle, new Color(0.4f, 0.3f, 0.2f, 0.5f), 4f, 1f, 0.2f, 0.001f, 0.0005f, getX(), getY() + getHeight() / 2, 2, 2, 0, 0, 400, 400);
	}

	public void grow(float delta) {
	}

	@Override
	public float getWidth() {
		return Consts.THORN_TEX_WIDTH * Consts.THORN_TEX_ZOOM;
	}

	@Override
	public float getHeight() {
		return Consts.THORN_TEX_HEIGHT * Consts.THORN_TEX_ZOOM;
	}

	public void render(float delta, SpriteBatch batch) {
		batch.draw(Textures.get().thorn, getX() - Consts.THORN_TEX_WIDTH * Consts.THORN_TEX_ZOOM / 2, getY(),
				Consts.THORN_TEX_WIDTH * Consts.THORN_TEX_ZOOM, Consts.THORN_TEX_HEIGHT * Consts.THORN_TEX_ZOOM);
	}

	public Rectangle getCollisionBox() {
		return collisionBox.set(getX() - Consts.THORN_TEX_WIDTH * Consts.THORN_TEX_ZOOM / 2, getY(), Consts.THORN_TEX_WIDTH * Consts.THORN_TEX_ZOOM, Consts.THORN_TEX_HEIGHT * Consts.THORN_TEX_ZOOM);
	}

	@Override
	public boolean onCollision(Collidable collidable, float delta) {
		if(collidable instanceof Hero){
			if(((Hero) collidable).getY() > getY() + getHeight() / 2){
				((Hero) collidable).addLives(-Consts.THORN_DAMAGE_ON_TOP);
				destroy();
			} else {
				((Hero) collidable).addLives(-(Consts.THORN_DAMAGE_PER_SECOND + getX() * Consts.THORN_DAMAGE_PER_DIST) * delta);
			}
			if(MathUtils.randomBoolean(Consts.SOUND_HURTS_PROBABILITY)) {
				Sounds.get().hurt.play();
			}
		}
		return true;
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
		destroySystem.setGenerating(true);
		destroySystem.setAutoDisable(0.2f);
	}
}
