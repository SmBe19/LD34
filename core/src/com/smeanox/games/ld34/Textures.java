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
	}

	public void clear(){
		manager.clear();
	}
}
