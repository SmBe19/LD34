package com.smeanox.games.ld34.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.smeanox.games.ld34.Consts;
import com.smeanox.games.ld34.Textures;

public class Bridge extends Plant {
	
	private float targetWidth;

	private float width;

	private Texture bridgeTexture;

	private TextureRegion[] regions;

	private TextureRegion[] growRegions;
	
	private TextureRegion base;

	public Bridge(World world, float x0, float y0, float targetWidth) {
		super(world, x0, y0, Consts.BRIDGE_START_LIVES);
		this.targetWidth = targetWidth;
		bridgeTexture = Textures.get().bridge;
		growRegions = new TextureRegion[4];
		for (int i=0; i<4; i++) {
			growRegions[i] = new TextureRegion(bridgeTexture, i * Consts.BRIDGE_TEXTURE_WIDTH, Consts.BRIDGE_TEXTURE_HEIGHT, Consts.BRIDGE_TEXTURE_WIDTH, Consts.BRIDGE_TEXTURE_HEIGHT);
		}
		regions = new TextureRegion[MathUtils.ceil(targetWidth/(Consts.BRIDGE_STEP*Consts.BRIDGE_TEX_ZOOM))];
		for (int i=0; i<MathUtils.ceil(targetWidth/(Consts.BRIDGE_STEP*Consts.BRIDGE_TEX_ZOOM)); i++) {
			int iy = MathUtils.random.nextInt(4);
			regions[i] = new TextureRegion(bridgeTexture, 0, iy * Consts.BRIDGE_TEXTURE_HEIGHT, Consts.BRIDGE_TEXTURE_WIDTH, Consts.BRIDGE_TEXTURE_HEIGHT);
		}
		base = new TextureRegion(bridgeTexture, 0, 4 * Consts.BRIDGE_TEXTURE_HEIGHT, Consts.BRIDGE_TEXTURE_WIDTH, Consts.BRIDGE_TEXTURE_HEIGHT);
	}
	


	public void grow(float delta) {
		width += delta * Consts.BRIDGE_GROW_RATE;

		if( width > targetWidth) {
			width = targetWidth;
		}
	}

	@Override
	public float getHeight() {
		return Consts.BRIDGE_TEXTURE_HEIGHT;
	}

	public void render(float delta, SpriteBatch batch) {
		for (int x = 0; x < width; x += Consts.BRIDGE_STEP * Consts.BRIDGE_TEX_ZOOM) {
			//last section
			if( x + Consts.BRIDGE_STEP * Consts.BRIDGE_TEX_ZOOM > width) {
				int progress = (int)(4f*(width - x)/(Consts.BRIDGE_STEP * Consts.BRIDGE_TEX_ZOOM));
				batch.draw(growRegions[progress], getX() + x, getY() - Consts.BRIDGE_TEXTURE_HEIGHT*Consts.BRIDGE_TEX_ZOOM/2, Consts.BRIDGE_TEXTURE_WIDTH * Consts.BRIDGE_TEX_ZOOM, Consts.BRIDGE_TEXTURE_HEIGHT * Consts.BRIDGE_TEX_ZOOM);
			} else {
				batch.draw(regions[x/(int)(Consts.BRIDGE_STEP*Consts.BRIDGE_TEX_ZOOM)], getX() + x, getY() - Consts.BRIDGE_TEXTURE_HEIGHT*Consts.BRIDGE_TEX_ZOOM/2, Consts.BRIDGE_TEXTURE_WIDTH * Consts.BRIDGE_TEX_ZOOM, Consts.BRIDGE_TEXTURE_HEIGHT * Consts.BRIDGE_TEX_ZOOM);
			}
		}

	}

	public Rectangle getCollisionBox() {
		return new Rectangle(getX(), getY()- Consts.BRIDGE_TEXTURE_HEIGHT * Consts.BRIDGE_TEX_ZOOM / 2, width, Consts.BRIDGE_TEXTURE_HEIGHT * Consts.BRIDGE_TEX_ZOOM);
	}

	@Override
	public boolean onCollision(Collidable collidable, float delta) {
		return true;
	}

	public float getWidth() {
		return width;
	}
}
