package com.smeanox.games.ld34.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.smeanox.games.ld34.Textures;
import com.smeanox.games.ld34.Consts;

/**
 * Comment
 */
public class Building implements Renderable
{

	private int vSegments, hSegments;
	private TextureRegion[][] regions;

	private World world;
	private float x0, y0;

	public Building(World world, float x0, float y0, int vertSegments, int horSegments) {
		this.x0 = x0;
		this.y0 = y0;
		this.world = world;
		world.getRenderables().add(this);
		this.vSegments = vertSegments;
		this.hSegments = horSegments;
		this.regions = new TextureRegion[vSegments][hSegments];
		Textures textures = Textures.get();

		for (int y=0; y<vSegments; y++) {
			for (int x=0; x<hSegments; x++) {
				int ix = MathUtils.random.nextInt(8);
				TextureRegion region = new TextureRegion(textures.walls, 16*ix, 0, 16, 16);
				regions[y][x] = region;
			}
		}

	}

	@Override
	public void render(float delta, SpriteBatch spriteBatch) {
		
		for (int y=0; y<vSegments; y++) {
			for (int x=0; x<hSegments; x++) {
				spriteBatch.draw(regions[y][x], x0 + x * 16 * Consts.HERO_TEX_ZOOM, y0 + y * 16 * Consts.HERO_TEX_ZOOM, 16 * Consts.HERO_TEX_ZOOM, 16 * Consts.HERO_TEX_ZOOM);
			}
		}

	}
}
