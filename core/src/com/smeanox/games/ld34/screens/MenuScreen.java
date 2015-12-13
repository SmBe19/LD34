package com.smeanox.games.ld34.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.smeanox.games.ld34.Consts;
import com.smeanox.games.ld34.LD34;

/**
 * Comment
 */
public class MenuScreen implements Screen {

	private LD34 game;

	private boolean wasSpacePressed, wasPlantActionPressed, wasAttackActionPressed;

	private int activeMenuItem;
	private String[] menuItems = {"More Lives", "More Damage", "More Lives", "More Bridges", "Play"};

	public MenuScreen(LD34 game) {
		this.game = game;

		wasSpacePressed = wasAttackActionPressed = wasPlantActionPressed = false;
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		handleInput(delta);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


	}

	private void handleInput(float delta){
		int touchInp = 0;
		if(Gdx.input.isTouched()){
			touchInp = (int) Math.signum(Gdx.input.getX() - Consts.WIDTH / 2);
		}

		if(!wasAttackActionPressed && (Gdx.input.isKeyPressed(Consts.KEY_ATTACK_ACTION) || touchInp == -1)){
			nextItem();
		}
		if(!wasPlantActionPressed && (Gdx.input.isKeyPressed(Consts.KEY_PLANT_ACTION) || touchInp == 1)){
			chooseItem();
		}
		if(!wasSpacePressed && Gdx.input.isKeyPressed(Input.Keys.SPACE)){
			game.showGame(true);
		}

		wasAttackActionPressed = Gdx.input.isKeyPressed(Consts.KEY_ATTACK_ACTION) || touchInp == -1;
		wasPlantActionPressed = Gdx.input.isKeyPressed(Consts.KEY_PLANT_ACTION) || touchInp == 1;
		wasSpacePressed = Gdx.input.isKeyPressed(Input.Keys.SPACE);
	}

	private void nextItem(){
		activeMenuItem++;
		activeMenuItem %= menuItems.length;
	}

	private void chooseItem(){
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}
}
