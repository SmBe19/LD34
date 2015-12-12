package com.smeanox.games.ld34.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.smeanox.games.ld34.Consts;
import com.smeanox.games.ld34.Textures;

/**
 * Comment
 */
public class GroundPart implements Renderable, Collidable {

	private World world;
	private int x, width;

	private Texture ground;

	public GroundPart(World world, int x, int width) {
		this.world = world;
		this.x = x;
		this.width = width;

		ground = Textures.get().ground;

		world.getRenderables().add(this);
		world.getPhysics().addCollidable(this);
	}

	@Override
	public Rectangle getCollisionBox() {
		return new Rectangle(x, Consts.GROUND_HEIGHT - Consts.GROUND_THICKNESS, width, Consts.GROUND_THICKNESS);
	}

	@Override
	public void render(float delta, SpriteBatch spriteBatch) {
		int i = 0;
		while(i * ground.getWidth() < width){
			spriteBatch.draw(ground, x + i * ground.getWidth(),
					Consts.GROUND_HEIGHT + Consts.GROUND_TEX_OFFSET_Y - Consts.GROUND_TEX_HEIGHT,
					ground.getWidth(), Consts.GROUND_TEX_HEIGHT);
			i++;
		}
	}
}
