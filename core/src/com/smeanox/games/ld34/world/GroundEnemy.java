package com.smeanox.games.ld34.world;

import com.smeanox.games.ld34.Consts;
import com.smeanox.games.ld34.Textures;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.Texture;

public class GroundEnemy extends Enemy {

	float animationTime = 0;
	Animation animation;


	public GroundEnemy(World world, float x, float y) {
		super(world, x, y);
		Texture texture = Textures.get().enemy;
		int type = MathUtils.random(0,3);
		animation = Hero.createAnimation(texture, type, type+1, 0, 4, Consts.ENEMY_TEX_WIDTH, Consts.ENEMY_TEX_HEIGHT, 0.1f, Animation.PlayMode.LOOP);
	}


	public float getDamage() {
		return 3;
	}

	public void update(float delta) {
		animationTime += delta;
		

	}

	public Rectangle getCollisionBox() {
		return new Rectangle(x,y,32,32);
	}

	public boolean collidesWith(Collidable c) {
		return c instanceof GroundPart || c instanceof Hero;
	}

	public void render(float delta, SpriteBatch batch) {
		batch.draw(animation.getKeyFrame(animationTime), x, y, Consts.ENEMY_TEX_WIDTH * Consts.ENEMY_TEX_ZOOM, Consts.ENEMY_TEX_HEIGHT * Consts.ENEMY_TEX_ZOOM);
	}
}
