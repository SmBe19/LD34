package com.smeanox.games.ld34;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Color;

public class Font {
	private static final String order = "abcdefghijklmnopqrstuvwxyz.,!?\" 0123456789()'$";
	private Texture fontTexture;
	private TextureRegion[] regions;

	public Font(Texture fontTexture) {
		this.fontTexture = fontTexture;
		regions = new TextureRegion[order.length()];
		for (int i=0; i<order.length(); i++) {
			int x = i % 8;
			int y = i / 8;
			regions[i] = new TextureRegion(fontTexture, 8*x, 8*y, 7, 7);
		}
	}

	public void draw(SpriteBatch batch, String string, int x, int y, int scale) {
		String lower = string.toLowerCase();
		for (int i=0; i<lower.length(); i++) {
			char c = lower.charAt(i);
			int ix = order.indexOf(c);
			if(ix != -1) {
				batch.draw(regions[ix], x + 8 * i * scale, y, 8*scale, 8*scale);
			}
		}
	}

	public void draw(SpriteBatch batch, String string, int x, int y, int scale, Color col) {
		batch.setColor(col);
		draw(batch, string, x, y, scale);
		batch.setColor(Color.WHITE);
	}
}
