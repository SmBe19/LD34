package com.smeanox.games.ld34.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.smeanox.games.ld34.Textures;
import com.smeanox.games.ld34.Consts;

/**
 * Comment
 */
public class Building implements Renderable, Collidable {

	private int vSegments, hSegments;
	private TextureRegion[][] regions;

	private World world;
	private float x0, y0;
	private Texture walls;

	public Building(World world, float x0, float y0, int vertSegments, int horSegments) {
		this.x0 = x0;
		this.y0 = y0;
		this.world = world;
		this.vSegments = vertSegments;
		this.hSegments = horSegments;
		this.regions = new TextureRegion[vSegments][hSegments];
		this.walls = Textures.get().walls;

		world.addRenderable(Consts.LAYER_BUILDING, this);
		world.getPhysics().addCollidable(this);

		for (int y = 0; y < vSegments; y++) {
			for (int x = 0; x < hSegments; x++) {
				int ix = MathUtils.random.nextInt(8);
				TextureRegion region = new TextureRegion(walls, Consts.BUILDING_TEX_WIDTH * ix, 0,
						Consts.BUILDING_TEX_WIDTH, Consts.BUILDING_TEX_HEIGHT);
				regions[y][x] = region;
			}
		}
	}

	@Override
	public void render(float delta, SpriteBatch spriteBatch) {
		for (int y = 0; y < vSegments; y++) {
			for (int x = 0; x < hSegments; x++) {
				spriteBatch.draw(regions[y][x], x0 + x * Consts.BUILDING_TEX_WIDTH * Consts.BUILDING_TEX_ZOOM,
						y0 + y * Consts.BUILDING_TEX_HEIGHT * Consts.BUILDING_TEX_ZOOM,
						Consts.BUILDING_TEX_WIDTH * Consts.BUILDING_TEX_ZOOM,
						Consts.BUILDING_TEX_HEIGHT * Consts.BUILDING_TEX_ZOOM);
			}
		}

	}

	@Override
	public Rectangle getCollisionBox() {
		return new Rectangle(x0, y0, hSegments * Consts.BUILDING_TEX_WIDTH * Consts.BUILDING_TEX_ZOOM,
				vSegments * Consts.BUILDING_TEX_HEIGHT * Consts.BUILDING_TEX_ZOOM);
	}

	@Override
	public void onCollision(Collidable collidable) {
	}
}
