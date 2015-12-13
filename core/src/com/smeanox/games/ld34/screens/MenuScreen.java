package com.smeanox.games.ld34.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smeanox.games.ld34.Consts;
import com.smeanox.games.ld34.Font;
import com.smeanox.games.ld34.Icons;
import com.smeanox.games.ld34.LD34;
import com.smeanox.games.ld34.Textures;
import com.smeanox.games.ld34.world.ConstsMenu;
import com.smeanox.games.ld34.world.GameState;

/**
 * Comment
 */
public class MenuScreen implements Screen {

	private LD34 game;

	private OrthographicCamera camera;

	private boolean wasSpacePressed, wasPlantActionPressed, wasAttackActionPressed;

	private int activeMenuItem;
	private String[] menuItems = {"Upgrade Lives", "Upgrade Damage", "Buy Rose", "Buy Bridge", "Play (press Space)"};
	private Font font;

	private float screenRatio;

	private SpriteBatch spriteBatch;

	public MenuScreen(LD34 game) {
		this.game = game;

		camera = new OrthographicCamera(Consts.DEV_WIDTH, Consts.DEV_HEIGHT);

		font = new Font(Textures.get().font);
		spriteBatch = new SpriteBatch();

		wasSpacePressed = wasAttackActionPressed = wasPlantActionPressed = true;
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		handleInput(delta);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		spriteBatch.begin();

		for (int i = 0; i < ConstsMenu.GAME_TITLE_SPLIT.length; i++) {
			int y = (int) (Consts.HEIGHT - (ConstsMenu.TITLE_OFFSET_Y + i * ConstsMenu.ITEMS_SPACING_Y));
			font.draw(spriteBatch, ConstsMenu.GAME_TITLE_SPLIT[i].toLowerCase(), ConstsMenu.TITLE_OFFSET_X + 2,
					y - 2, ConstsMenu.FONT_SIZE, ConstsMenu.TITLE_COLOR_SHADOW);
			font.draw(spriteBatch, ConstsMenu.GAME_TITLE_SPLIT[i].toLowerCase(), ConstsMenu.TITLE_OFFSET_X,
					y, ConstsMenu.FONT_SIZE, ConstsMenu.TITLE_COLOR);
		}
		font.draw(spriteBatch, "$ " + GameState.get().getMoney(), ConstsMenu.ITEMS_OFFSET_X,
				(int) (Consts.HEIGHT - (ConstsMenu.ITEMS_OFFSET_Y)), ConstsMenu.FONT_SIZE);
		for (int i = 0; i < menuItems.length; i++) {
			int y = (int) (Consts.HEIGHT - (ConstsMenu.ITEMS_OFFSET_Y + (i + 1) * ConstsMenu.ITEMS_SPACING_Y));
			Icons.values()[i].draw(spriteBatch, ConstsMenu.ICON_SIZE, ConstsMenu.ITEMS_ICON_OFFSET_X, y);
			font.draw(spriteBatch, menuItems[i].toLowerCase(), ConstsMenu.ITEMS_OFFSET_X, y,
					ConstsMenu.FONT_SIZE, (i == activeMenuItem ? ConstsMenu.ACTIVE_ITEM_COLOR : ConstsMenu.INACTIVE_ITEM_COLOR));
		}
		spriteBatch.end();
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
		if(activeMenuItem == 4){
			game.showGame(true);
		}
	}

	@Override
	public void resize(int width, int height) {
		screenRatio = (float)width / height;

		Consts.WIDTH = screenRatio * Consts.DEV_HEIGHT;
		camera.viewportWidth = Consts.WIDTH;
		camera.viewportHeight = Consts.HEIGHT;
		camera.position.set(Consts.WIDTH / 2, Consts.HEIGHT / 2, 0);
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
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
