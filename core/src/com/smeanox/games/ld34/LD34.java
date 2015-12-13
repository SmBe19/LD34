package com.smeanox.games.ld34;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smeanox.games.ld34.screens.GameScreen;
import com.smeanox.games.ld34.screens.MenuScreen;

public class LD34 extends Game {

	private MenuScreen menuScreen;
	private GameScreen gameScreen;

	@Override
	public void create() {
		Textures.get().finishLoading();
		showMenu();
	}

	public void showMenu(){
		if(menuScreen == null){
			menuScreen = new MenuScreen(this);
		}
		setScreen(menuScreen);
	}

	public void showGame(boolean restart){
		if(gameScreen == null || restart){
			gameScreen = new GameScreen(this);
		}
		setScreen(gameScreen);
	}
}
