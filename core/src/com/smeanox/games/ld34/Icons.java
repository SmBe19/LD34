package com.smeanox.games.ld34;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smeanox.games.ld34.Textures;

public enum Icons {
	HEART(0),
	AXE(1),
	ROSE(2),
	BRIDGE(3),
	MIKE(4),
	COIN(5),
	KEY(6);

	private TextureRegion region;

	Icons(int index) {
		Texture iconTexture = Textures.get().icons;
		region = new TextureRegion(iconTexture, 0, 8*index, 8, 8);
	}

	public void draw(SpriteBatch batch, float scale, float x, float y) {
		batch.draw(region, x , y, 8 * scale, 8*scale);
	}

	public TextureRegion get(){
		return region;
	}
}
