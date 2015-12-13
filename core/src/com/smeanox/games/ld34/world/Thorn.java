package com.smeanox.games.ld34.world;

import com.badlogic.gdx.graphics.Color;
import com.smeanox.games.ld34.Consts;
import com.smeanox.games.ld34.Textures;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Thorn extends Plant {

	private ParticleSystem destroySystem;

	public Thorn(World world, float x0, float y0) {
		super(world, x0, y0,Consts.THORN_START_LIVES + x0 * Consts.THORN_LIVES_PER_DIST);
		initParticles();
	}

	private void initParticles(){
		destroySystem = new ParticleSystem(world, "thornDestroy", null, Consts.LAYER_PLANT, Textures.get().particle, Color.BROWN, 0.5f, 1f, 0.2f, 0.001f, 0.0005f, getX(), getY() + getHeight() / 2, 2, 2, 0, 0, 400, 400);
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
		return new Rectangle(getX() - Consts.THORN_TEX_WIDTH * Consts.THORN_TEX_ZOOM / 2, getY(), Consts.THORN_TEX_WIDTH * Consts.THORN_TEX_ZOOM, Consts.THORN_TEX_HEIGHT * Consts.THORN_TEX_ZOOM);
	}

	@Override
	public boolean onCollision(Collidable collidable, float delta) {
		if(collidable instanceof Hero){
			if(((Hero) collidable).getY() > getY() + getHeight() / 2){
				((Hero) collidable).addLives(-Consts.THORN_DAMAGE_ON_TOP);
				destroy();
			} else {
				((Hero) collidable).addLives(-Consts.THORN_DAMAGE_PER_SECOND * delta);
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
