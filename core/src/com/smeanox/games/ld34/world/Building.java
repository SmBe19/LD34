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
public class Building implements Renderable, Collidable, Destroyable {
	private boolean destroyed = false;

	private int vSegments, hSegments;
	private TextureRegion[][] regions;
	private TextureRegion[][] overgrow;

	private World world;
	private float x0;
	private float y0;
	private Texture walls;
	private Texture plants;

	private Rectangle collisionBox;

	public Building(World world, float x0, float y0, int vertSegments, int horSegments) {
		this.x0 = x0;
		this.y0 = y0;
		this.world = world;
		this.vSegments = vertSegments;
		this.hSegments = horSegments;
		this.regions = new TextureRegion[vSegments][hSegments];
		this.overgrow = new TextureRegion[vSegments][hSegments];
		this.walls = Textures.get().walls;
		this.plants = Textures.get().overlay;

		world.addRenderable(Consts.LAYER_BUILDING, this);
		world.getPhysics().addCollidable(this);

		for (int y = 0; y < vSegments; y++) {
			for (int x = 0; x < hSegments; x++) {
				int ix = MathUtils.randomBoolean(0.5f)?MathUtils.random.nextInt(8) : 7;
				TextureRegion region = new TextureRegion(walls, Consts.BUILDING_TEX_WIDTH * ix, 0, 
						Consts.BUILDING_TEX_WIDTH, Consts.BUILDING_TEX_HEIGHT);
				regions[y][x] = region;
				ix = MathUtils.random.nextInt(4);
				overgrow[y][x] = MathUtils.randomBoolean(((float)(vSegments-y))/vSegments) ? new TextureRegion(plants, Consts.BUILDING_TEX_WIDTH * ix, 0, Consts.BUILDING_TEX_WIDTH, Consts.BUILDING_TEX_HEIGHT) : null;
			}
		}

		collisionBox = new Rectangle();
	}

	public float getHeight(){
		return vSegments * Consts.BUILDING_TEX_HEIGHT * Consts.BUILDING_TEX_ZOOM;
	}


	public float getWidth(){
		return hSegments * Consts.BUILDING_TEX_WIDTH * Consts.BUILDING_TEX_ZOOM;
	}

	@Override
	public void render(float delta, SpriteBatch spriteBatch) {
		for (int y = 0; y < vSegments; y++) {
			for (int x = 0; x < hSegments; x++) {
				spriteBatch.draw(regions[y][x], getX() + x * Consts.BUILDING_TEX_WIDTH * Consts.BUILDING_TEX_ZOOM,
						getY() + y * Consts.BUILDING_TEX_HEIGHT * Consts.BUILDING_TEX_ZOOM,
						Consts.BUILDING_TEX_WIDTH * Consts.BUILDING_TEX_ZOOM,
						Consts.BUILDING_TEX_HEIGHT * Consts.BUILDING_TEX_ZOOM);
				if (overgrow[y][x] != null) {
					spriteBatch.draw(overgrow[y][x], getX() + x * Consts.BUILDING_TEX_WIDTH * Consts.BUILDING_TEX_ZOOM,
							getY() + y * Consts.BUILDING_TEX_HEIGHT * Consts.BUILDING_TEX_ZOOM,
							Consts.BUILDING_TEX_WIDTH * Consts.BUILDING_TEX_ZOOM,
							Consts.BUILDING_TEX_HEIGHT * Consts.BUILDING_TEX_ZOOM);
				}
			}
		}

	}

	@Override
	public Rectangle getCollisionBox() {
		return collisionBox.set(getX(), getY(), hSegments * Consts.BUILDING_TEX_WIDTH * Consts.BUILDING_TEX_ZOOM,
				vSegments * Consts.BUILDING_TEX_HEIGHT * Consts.BUILDING_TEX_ZOOM);
	}

	@Override
	public boolean onCollision(Collidable collidable, float delta) {
		return true;
	}

	@Override
	public void destroy() {
		if(isDestroyed()){
			return;
		}
		destroyed = true;
		world.getRenderables(Consts.LAYER_BUILDING).remove(this);
		world.getPhysics().removeCollidable(this);
	}

	@Override
	public boolean isDestroyed() {
		return destroyed;
	}

	public float getX() {
		return x0;
	}

	public float getY() {
		return y0;
	}
}
