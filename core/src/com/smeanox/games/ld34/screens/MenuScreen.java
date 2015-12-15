package com.smeanox.games.ld34.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.smeanox.games.ld34.Consts;
import com.smeanox.games.ld34.Font;
import com.smeanox.games.ld34.Icons;
import com.smeanox.games.ld34.LD34;
import com.smeanox.games.ld34.MusicManager;
import com.smeanox.games.ld34.Sounds;
import com.smeanox.games.ld34.Textures;
import com.smeanox.games.ld34.ConstsMenu;
import com.smeanox.games.ld34.world.GameState;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Comment
 */
public class MenuScreen implements Screen {

	private LD34 game;

	private OrthographicCamera camera;

	private boolean wasSpacePressed, wasPlantActionPressed, wasAttackActionPressed, wasBackToMenuPressed;

	private int activeMenuItem;
	private String[] menuItems = {"Upgrade Lives", "Upgrade Damage", "Buy Rose", "Buy Bridge", "Play", "Mute Game"};
	private Font font;

	private float resetCounter;

	private float screenRatio;

	private SpriteBatch spriteBatch;

	public MenuScreen(LD34 game) {
		this.game = game;

		camera = new OrthographicCamera(Consts.DEV_WIDTH, Consts.DEV_HEIGHT);

		font = new Font(Textures.get().font);
		spriteBatch = new SpriteBatch();

		activeMenuItem = 4;

		resetCounter = 0;

		wasSpacePressed = wasAttackActionPressed = wasPlantActionPressed = wasBackToMenuPressed = true;

		GameState.get().load();
	}

	@Override
	public void show() {
		wasSpacePressed = wasAttackActionPressed = wasPlantActionPressed = wasBackToMenuPressed = true;
		resetCounter = 0;
	}

	@Override
	public void render(float delta) {
		MusicManager.get().update(delta);

		if(resetCounter > 0){
			resetCounter -= delta / Consts.RESET_PREFERENCES_TIME;
		}

		handleInput(delta);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		spriteBatch.begin();

		for (int i = 0; i < ConstsMenu.GAME_TITLE_SPLIT.length; i++) {
			int y = (int) (Consts.HEIGHT - (ConstsMenu.TITLE_OFFSET_Y + i * ConstsMenu.ITEMS_SPACING_Y));
			font.draw(spriteBatch, ConstsMenu.GAME_TITLE_SPLIT[i], ConstsMenu.TITLE_OFFSET_X + 2,
					y - 2, ConstsMenu.FONT_SIZE, ConstsMenu.TITLE_COLOR_SHADOW);
			font.draw(spriteBatch, ConstsMenu.GAME_TITLE_SPLIT[i], ConstsMenu.TITLE_OFFSET_X,
					y, ConstsMenu.FONT_SIZE, ConstsMenu.TITLE_COLOR);
		}
		Icons.COIN.draw(spriteBatch, ConstsMenu.ICON_SIZE, ConstsMenu.ITEMS_ICON_OFFSET_X, (int) (Consts.HEIGHT - (ConstsMenu.ITEMS_OFFSET_Y)));
		font.draw(spriteBatch, "" + GameState.get().getMoney(), ConstsMenu.ITEMS_OFFSET_X,
				(int) (Consts.HEIGHT - (ConstsMenu.ITEMS_OFFSET_Y)), ConstsMenu.FONT_SIZE);

		spriteBatch.draw(Textures.get().mike, Consts.WIDTH - ConstsMenu.MIKE_OFFSET_X, Consts.HEIGHT - ConstsMenu.MIKE_OFFSET_Y,
				ConstsMenu.MIKE_WIDTH, ConstsMenu.MIKE_HEIGHT);

		drawMenu();

		drawKeyHelp(spriteBatch, font);

		spriteBatch.end();
	}

	private void drawMenu() {
		ArrayList<String> menuItemsInfo = new ArrayList<String>(Arrays.asList(menuItems));
		ArrayList<String> prices = new ArrayList<String>();
		ArrayList<Boolean> canBuy = new ArrayList<Boolean>();
		ArrayList<Icons> icons = new ArrayList<Icons>();

		menuItemsInfo.set(0, menuItems[0] + " (" + MathUtils.ceil(GameState.get().getHeroHealth()) + ")");
		menuItemsInfo.set(1, menuItems[1] + " (" + MathUtils.ceil(GameState.get().getHeroDamage()) + ")");
		menuItemsInfo.set(2, menuItems[2] + " (" + GameState.get().getRoses() + ")");
		menuItemsInfo.set(3, menuItems[3] + " (" + GameState.get().getBridges() + ")");

		menuItemsInfo.set(5, Sounds.get().isMuted() ? "unmute game" : "mute game");

		prices.add("" + GameState.get().getHeroHealthUpgradeCost());
		prices.add("" + GameState.get().getHeroDamageUpgradeCost());
		prices.add("" + GameState.get().getRoseCost());
		prices.add("" + GameState.get().getBridgeCost());
		prices.add("");
		prices.add("");

		canBuy.add(GameState.get().isAbleToUpgradeHealth());
		canBuy.add(GameState.get().isAbleToUpgradeDamage());
		canBuy.add(GameState.get().isAbleToBuyRose());
		canBuy.add(GameState.get().isAbleToBuyBridge());
		canBuy.add(false);
		canBuy.add(false);

		for(int i = 0; i < 5; i++){
			icons.add(Icons.values()[i]);
		}
		icons.add(Icons.KEY);

		for (int i = 0; i < menuItems.length; i++) {
			int y = (int) (Consts.HEIGHT - (ConstsMenu.ITEMS_OFFSET_Y + (i + 1) * ConstsMenu.ITEMS_SPACING_Y));
			if(prices.get(i).length() > 0) {
				font.draw(spriteBatch, prices.get(i).toLowerCase(),
						(int) (Consts.WIDTH - ConstsMenu.ITEMS_PRICE_OFFSET_X - prices.get(i).length()
								* ConstsMenu.ITEMS_PRICE_OFFSET_PER_CHAR), y, ConstsMenu.FONT_SIZE,
						(canBuy.get(i) ? ConstsMenu.CAN_BUY_ITEM_COLOR : ConstsMenu.CANNOT_BUT_ITEM_COLOR));
				Icons.COIN.draw(spriteBatch, ConstsMenu.ICON_SIZE, Consts.WIDTH - ConstsMenu.ITEMS_PRICE_ICON_OFFSET_X, y);
			}
			icons.get(i).draw(spriteBatch, ConstsMenu.ICON_SIZE, ConstsMenu.ITEMS_ICON_OFFSET_X, y);
			font.draw(spriteBatch, menuItemsInfo.get(i).toLowerCase(), ConstsMenu.ITEMS_OFFSET_X, y,
					ConstsMenu.FONT_SIZE, (i == activeMenuItem ? ConstsMenu.ACTIVE_ITEM_COLOR : ConstsMenu.INACTIVE_ITEM_COLOR));
		}
	}

	public static void drawKeyHelp(SpriteBatch spriteBatch, Font font){
		Icons.KEY.draw(spriteBatch, ConstsMenu.KEY_ICON_SIZE, ConstsMenu.KEY_PLANT_X, Consts.HEIGHT - ConstsMenu.KEY_PLANT_Y);
		font.draw(spriteBatch, Consts.KEY_PLANT_ACTION_STRING, ConstsMenu.KEY_PLANT_X + ConstsMenu.KEY_TEXT_OFFSET_X,
				(int) (Consts.HEIGHT - ConstsMenu.KEY_PLANT_Y + ConstsMenu.KEY_TEXT_OFFSET_Y), ConstsMenu.KEY_FONT_SIZE);
		Icons.ROSE.draw(spriteBatch, ConstsMenu.KEY_ICON_SIZE, ConstsMenu.KEY_PLANT_X + ConstsMenu.KEY_SECOND_ICON_OFFSET_X,
				Consts.HEIGHT - ConstsMenu.KEY_PLANT_Y);

		Icons.KEY.draw(spriteBatch, ConstsMenu.KEY_ICON_SIZE, Consts.WIDTH - ConstsMenu.KEY_ATTACK_X, Consts.HEIGHT - ConstsMenu.KEY_ATTACK_Y);
		font.draw(spriteBatch, Consts.KEY_ATTACK_ACTION_STRING, (int) (Consts.WIDTH - ConstsMenu.KEY_ATTACK_X + ConstsMenu.KEY_TEXT_OFFSET_X),
				(int) (Consts.HEIGHT - ConstsMenu.KEY_ATTACK_Y + ConstsMenu.KEY_TEXT_OFFSET_Y), ConstsMenu.KEY_FONT_SIZE);
		Icons.AXE.draw(spriteBatch, ConstsMenu.KEY_ICON_SIZE, Consts.WIDTH - (ConstsMenu.KEY_ATTACK_X + ConstsMenu.KEY_SECOND_ICON_OFFSET_X),
				Consts.HEIGHT - ConstsMenu.KEY_ATTACK_Y);
	}

	private void handleInput(float delta){
		int touchInp = 0;
		if(Gdx.input.isTouched()){
			touchInp = (int) Math.signum(Gdx.input.getX() - Gdx.graphics.getWidth() / 2);
		}

		if(!wasAttackActionPressed && (Gdx.input.isKeyPressed(Consts.KEY_ATTACK_ACTION) || touchInp == 1)){
			chooseItem();
		}
		if(!wasPlantActionPressed && (Gdx.input.isKeyPressed(Consts.KEY_PLANT_ACTION) || touchInp == -1)){
			nextItem();
		}
		if(!wasSpacePressed && Gdx.input.isKeyPressed(Input.Keys.SPACE)){
			activeMenuItem = 4;
			chooseItem();
		}
		if(!wasBackToMenuPressed && (Gdx.input.isKeyPressed(Consts.KEY_BACK_TO_MENU) || Gdx.input.isKeyPressed(Input.Keys.BACK))){
			Gdx.app.exit();
		}

		wasAttackActionPressed = Gdx.input.isKeyPressed(Consts.KEY_ATTACK_ACTION) || touchInp == 1;
		wasPlantActionPressed = Gdx.input.isKeyPressed(Consts.KEY_PLANT_ACTION) || touchInp == -1;
		wasBackToMenuPressed = Gdx.input.isKeyPressed(Consts.KEY_BACK_TO_MENU) || Gdx.input.isKeyPressed(Input.Keys.BACK);
		wasSpacePressed = Gdx.input.isKeyPressed(Input.Keys.SPACE);
	}

	private void nextItem(){
		activeMenuItem++;
		activeMenuItem %= menuItems.length;

		Sounds.get().play(Sounds.get().blub);
	}

	private void chooseItem(){
		switch (activeMenuItem){
			case 0:
				if(GameState.get().isAbleToUpgradeHealth()) {
					GameState.get().upgradeHealth();
					Sounds.get().play(Sounds.get().coin);
				}
				break;
			case 1:
				if(GameState.get().isAbleToUpgradeDamage()){
					GameState.get().upgradeDamage();
					Sounds.get().play(Sounds.get().coin);
				}
				break;
			case 2:
				if(GameState.get().isAbleToBuyRose()) {
					GameState.get().buyRose();
					Sounds.get().play(Sounds.get().coin);
				}
				break;
			case 3:
				if(GameState.get().isAbleToBuyBridge()) {
					GameState.get().buyBridge();
					Sounds.get().play(Sounds.get().coin);
				}
				break;
			case 4:
				game.showGame(true);
				Sounds.get().play(Sounds.get().start);
				break;
			case 5:
				Sounds.get().setMuted(!Sounds.get().isMuted());
				MusicManager.get().setPlaying(!Sounds.get().isMuted());
				Sounds.get().play(Sounds.get().coin);

				resetCounter++;

				if(resetCounter > Consts.RESET_PREFERENCES_HITS){
					GameState.get().reset();
				}
				break;
		}

		GameState.get().save();
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
