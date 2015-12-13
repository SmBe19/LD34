package com.smeanox.games.ld34.world;

import com.smeanox.games.ld34.Consts;
import com.smeanox.games.ld34.Textures;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;

public class Vine extends Plant {
	
	private float targetHeight;

	private float height;

	private Texture vineTexture;

	private TextureRegion[] regions;

	private TextureRegion[] growRegions;
	
	private TextureRegion base;

	public Vine(World world, float x0, float y0, float targetHeight) {
		super(world, x0, y0, Consts.VINE_START_LIVES);
		this.targetHeight = targetHeight;
		vineTexture = Textures.get().vine;
		growRegions = new TextureRegion[4];
		for (int i=0; i<4; i++) {
			growRegions[i] = new TextureRegion(vineTexture, Consts.VINE_TEXTURE_WIDTH, i * Consts.VINE_TEXTURE_HEIGHT, Consts.VINE_TEXTURE_WIDTH, Consts.VINE_TEXTURE_HEIGHT);
		}
		regions = new TextureRegion[MathUtils.ceil(targetHeight/(Consts.VINE_STEP*Consts.VINE_TEX_ZOOM))];
		for (int i=0; i<MathUtils.ceil(targetHeight/(Consts.VINE_STEP*Consts.VINE_TEX_ZOOM)); i++) {
			int ix = MathUtils.random.nextInt(4);
			regions[i] = new TextureRegion(vineTexture, 0, ix * Consts.VINE_TEXTURE_HEIGHT, Consts.VINE_TEXTURE_WIDTH, Consts.VINE_TEXTURE_HEIGHT);
		}
		base = new TextureRegion(vineTexture, 0, 4 * Consts.VINE_TEXTURE_HEIGHT, Consts.VINE_TEXTURE_WIDTH, Consts.VINE_TEXTURE_HEIGHT);
	}
	


	public void grow(float delta) {
		height += delta * Consts.VINE_GROW_RATE;

		if( height > targetHeight) {
			height = targetHeight;
		}
	}

	@Override
	public float getWidth() {
		return Consts.VINE_TEXTURE_WIDTH;
	}

	public void render(float delta, SpriteBatch batch) {
		batch.draw(base, getX() - Consts.VINE_TEXTURE_WIDTH * Consts.VINE_TEX_ZOOM/2, getY(), Consts.VINE_TEXTURE_WIDTH * Consts.VINE_TEX_ZOOM, Consts.VINE_TEXTURE_HEIGHT * Consts.VINE_TEX_ZOOM);
		for (int y = 0; y < height; y += Consts.VINE_STEP * Consts.VINE_TEX_ZOOM) {
			//last section
			if( y + Consts.VINE_STEP * Consts.VINE_TEX_ZOOM > height) {
				int progress = (int)(4f*(height - y)/(Consts.VINE_STEP * Consts.VINE_TEX_ZOOM));
				batch.draw(growRegions[progress], getX() - Consts.VINE_TEXTURE_WIDTH*Consts.VINE_TEX_ZOOM/2, getY() + y, Consts.VINE_TEXTURE_WIDTH * Consts.VINE_TEX_ZOOM, Consts.VINE_TEXTURE_HEIGHT * Consts.VINE_TEX_ZOOM);
			} else {
				batch.draw(regions[y/(int)(Consts.VINE_STEP*Consts.VINE_TEX_ZOOM)], getX() - Consts.VINE_TEXTURE_WIDTH*Consts.VINE_TEX_ZOOM/2, getY() + y, Consts.VINE_TEXTURE_WIDTH * Consts.VINE_TEX_ZOOM, Consts.VINE_TEXTURE_HEIGHT * Consts.VINE_TEX_ZOOM);
			}
		}

	}

	public Rectangle getCollisionBox() {
		return new Rectangle(getX() - Consts.VINE_TEXTURE_WIDTH * Consts.VINE_TEX_ZOOM / 2, getY(), Consts.VINE_TEXTURE_WIDTH * Consts.VINE_TEX_ZOOM, height);
	}

	@Override
	public boolean onCollision(Collidable collidable, float delta) {
		return true;
	}

	public float getHeight() {
		return height;
	}
}
