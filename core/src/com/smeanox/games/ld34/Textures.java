package com.smeanox.games.ld34;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 * Comment
 */
public class Textures {
	private static Textures singleton;

	private AssetManager manager;

	public Texture hero;
	public Texture background;
	public Texture ground;
	public Texture walls;
	public Texture overlay;
	public Texture particle;
	public Texture vine;
	public Texture thorn;
	public Texture enemy;
	public Texture airEnemy;
	public Texture roofEnemy;
	public Texture coin;

	private Textures(){
		manager = new AssetManager();
		prepare();
	}

	public static Textures get(){
		if (singleton == null){
			singleton = new Textures();
		}
		return singleton;
	}

	private void prepare(){
		manager.load("textures/hero.png", Texture.class);
		manager.load("textures/background.png", Texture.class);
		manager.load("textures/ground.png", Texture.class);
		manager.load("textures/walls.png", Texture.class);
		manager.load("textures/overlay.png", Texture.class);
		manager.load("textures/particle.png", Texture.class);
		manager.load("textures/vine.png", Texture.class);
		manager.load("textures/thornbush.png", Texture.class);
		manager.load("textures/enemies.png", Texture.class);
		manager.load("textures/enemies.png", Texture.class);
		manager.load("textures/airenemy.png", Texture.class);
		manager.load("textures/roofenemy.png", Texture.class);
		manager.load("textures/coin.png", Texture.class);
	}

	public boolean update(){
		return manager.update();
	}

	public float getProgress(){
		return manager.getProgress();
	}

	public void finishLoading(){
		manager.update();
		manager.finishLoading();

		hero = manager.get("textures/hero.png", Texture.class);
		background = manager.get("textures/background.png", Texture.class);
		ground = manager.get("textures/ground.png", Texture.class);
		walls = manager.get("textures/walls.png", Texture.class);
		overlay = manager.get("textures/overlay.png", Texture.class);
		particle = manager.get("textures/particle.png", Texture.class);
		vine = manager.get("textures/vine.png", Texture.class);
		thorn = manager.get("textures/thornbush.png", Texture.class);
		enemy = manager.get("textures/enemies.png", Texture.class);
		airEnemy = manager.get("textures/airenemy.png", Texture.class);
		roofEnemy = manager.get("textures/roofenemy.png", Texture.class);
		coin = manager.get("textures/coin.png", Texture.class);
	}

	public void clear(){
		manager.clear();
	}
}
