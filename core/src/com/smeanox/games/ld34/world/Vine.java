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

	public Vine(World world, float x0, float y0, float targetHeight) {
		super(world, x0, y0);
		this.targetHeight = targetHeight;
		vineTexture = Textures.get().vine;
		growRegions = new TextureRegion[4];
		for (int i=0; i<4; i++) {
			growRegions[i] = new TextureRegion(vineTexture, Consts.VINE_TEXTURE_WIDTH, i * Consts.VINE_TEXTURE_HEIGHT, Consts.VINE_TEXTURE_WIDTH, Consts.VINE_TEXTURE_HEIGHT);
		}
		regions = new TextureRegion[MathUtils.ceil(targetHeight/Consts.VINE_STEP)];
		for (int i=0; i<MathUtils.ceil(targetHeight/(float)Consts.VINE_STEP); i++) {
			int ix = MathUtils.random.nextInt(8);
			regions[i] = new TextureRegion(vineTexture, 0, i * Consts.VINE_TEXTURE_HEIGHT, Consts.VINE_TEXTURE_WIDTH, Consts.VINE_TEXTURE_HEIGHT);
		}
	}
	


	public void grow(float delta) {

		height += delta * Consts.VINE_GROW_RATE;

		if( height > targetHeight) height = targetHeight;


	}
	
	public void render(float delta, SpriteBatch batch) {
		for (int y = 0; y < height; y += Consts.VINE_STEP) {
			//last section
			if( y + Consts.VINE_STEP > height) {
				int progress = (int)(4f*(height - y)/Consts.VINE_STEP);
				batch.draw(growRegions[progress], x0 - Consts.VINE_TEXTURE_WIDTH/2, y0 + y);
			} else {
				batch.draw(regions[y/Consts.VINE_STEP], x0 - Consts.VINE_TEXTURE_WIDTH, y0 + y);
			}
		}

	}

	public Rectangle getCollisionBox() {
		return new Rectangle(x0 - Consts.VINE_TEXTURE_WIDTH/2, y0, Consts.VINE_TEXTURE_WIDTH, height);
	}

}
