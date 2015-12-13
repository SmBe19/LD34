package com.smeanox.games.ld34.world;

import com.smeanox.games.ld34.Consts;
import com.smeanox.games.ld34.Textures;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Thorn extends Plant {

	public Thorn(World world, float x0, float y0) {
		super(world, x0, y0);
		lives = Consts.THORN_START_LIVES;
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
		batch.draw(Textures.get().thorn, x0 - Consts.THORN_TEX_WIDTH * Consts.THORN_TEX_ZOOM / 2, y0,
				Consts.THORN_TEX_WIDTH * Consts.THORN_TEX_ZOOM, Consts.THORN_TEX_HEIGHT * Consts.THORN_TEX_ZOOM);
	}

	public Rectangle getCollisionBox() {
		return new Rectangle(x0 - Consts.THORN_TEX_WIDTH * Consts.THORN_TEX_ZOOM / 2, y0, Consts.THORN_TEX_WIDTH * Consts.THORN_TEX_ZOOM, Consts.THORN_TEX_HEIGHT * Consts.THORN_TEX_ZOOM);
	}

	@Override
	public boolean onCollision(Collidable collidable, float delta) {
		if(collidable instanceof Hero){
			((Hero) collidable).addLives(-Consts.THORN_DAMAGE_PER_SECOND * delta);
		}
		return true;
	}

}
