package com.smeanox.games.ld34.world;

import com.smeanox.games.ld34.Consts;
import com.smeanox.games.ld34.Textures;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;

public class Thorn extends Plant {
	

	public Thorn(World world, float x0, float y0) {
		super(world, x0, y0);
	}
	


	public void grow(float delta) {


	}
	
	public void render(float delta, SpriteBatch batch) {
		batch.draw(Textures.get().thorn, x0 - 16, y0);

	}

	public Rectangle getCollisionBox() {
		return new Rectangle(x0 - 16, y0, 32,32);
	}

	@Override
	public boolean onCollision(Collidable collidable) {
		return true;
	}

}
