package com.smeanox.game.ld34;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public enum Icons {
	HEART(0),
	AXE(1),
	ROSE(2),
	BRIDGE(3),
	MIKE(4),
	COIN(5);

	private TextureRegion region;

	Icons(int index) {
		Texture iconTexture = Textures.get().icons;
		region = new TextureRegion(iconTexture, 0, 8*index, 8, 8);
	}

	public void draw(SpriteBatch batch, int scale, float x, float y) {
		batch.draw(region, x, y, 8 * scale, 8*scale);
	}
}
