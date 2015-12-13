package com.smeanox.games.ld34.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.smeanox.games.ld34.Consts;
import com.smeanox.games.ld34.Font;
import com.smeanox.games.ld34.Icons;
import com.smeanox.games.ld34.LD34;
import com.smeanox.games.ld34.Textures;
import com.smeanox.games.ld34.world.GameState;
import com.smeanox.games.ld34.world.Renderable;
import com.smeanox.games.ld34.world.Updatable;
import com.smeanox.games.ld34.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Comment
 */
public class GameScreen implements Screen {

	private LD34 game;
	private OrthographicCamera camera;
	private OrthographicCamera uicamera;
	private OrthographicCamera backgroundCamera;
	private SpriteBatch spriteBatch;
	private float screenRatio;

	private float deathTimeout;

	private Texture background;
	private Font font;

	private World world;

	private boolean wasPlantActionPressed, wasAttackActionPressed;

	private FPSLogger fpsLogger = new FPSLogger();

	public GameScreen(LD34 game) {
		this.game = game;

		screenRatio = Consts.DEV_WIDTH / Consts.DEV_HEIGHT;

		camera = new OrthographicCamera(Consts.DEV_WIDTH, Consts.DEV_HEIGHT);
		uicamera = new OrthographicCamera(Consts.DEV_WIDTH, Consts.DEV_HEIGHT);
		backgroundCamera = new OrthographicCamera(Consts.DEV_WIDTH, Consts.DEV_HEIGHT);

		spriteBatch = new SpriteBatch();

		world = new World(camera);

		background = Textures.get().background;

		font = new Font(Textures.get().font);

		wasAttackActionPressed = wasPlantActionPressed = true;

		deathTimeout = Consts.DEATH_TIMEOUT;
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		fpsLogger.log();

		if(!world.getHero().isAlive()){
			deathTimeout -= delta;
			if(deathTimeout < 0) {
				game.showMenu();
			}
		}

		handleInput(delta);
		update(delta);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.position.set(MathUtils.roundPositive(world.getHero().getX() + Consts.CAMERA_OFFSET_X),
				MathUtils.roundPositive(Math.max(world.getHero().getY() - Consts.GROUND_HEIGHT, -Consts.HEIGHT * 5)), 0);
		shake(delta);
		backgroundCamera.position.set(MathUtils.roundPositive(camera.position.x / 10), MathUtils.roundPositive(camera.position.y / 10), 0);

		camera.update();
		backgroundCamera.update();

		spriteBatch.setProjectionMatrix(backgroundCamera.combined);

		spriteBatch.begin();

		float bgWidth = background.getWidth() * Consts.HEIGHT * 2 / background.getHeight();
		int start = MathUtils.floor((backgroundCamera.position.x - Consts.WIDTH / 2) / bgWidth);
		int end = MathUtils.ceil((backgroundCamera.position.x + Consts.WIDTH / 2) / bgWidth);
		for (int i = start; i < end; i++) {
			spriteBatch.draw(background, i * bgWidth, -Consts.HEIGHT, bgWidth, Consts.HEIGHT * 2);
		}
		spriteBatch.end();

		spriteBatch.setProjectionMatrix(camera.combined);

		spriteBatch.begin();
		for(List<Renderable> list : world.getRenderables()) {
			for (Renderable renderable : list) {
				renderable.render(delta, spriteBatch);
			}
		}
		spriteBatch.end();

		// UI

		spriteBatch.setProjectionMatrix(uicamera.combined);

		spriteBatch.begin();

		String text = "";
		int textWidth = 0;

		text = "" + GameState.get().getMoney();
		textWidth = text.length() * Consts.UI_MONEY_FONT_SIZE * Consts.UI_FONT_WIDTH_PER_SIZE_AND_CHAR;
		font.draw(spriteBatch, text, Consts.UI_MONEY_X, Consts.UI_MONEY_Y, Consts.UI_MONEY_FONT_SIZE);
		Icons.COIN.draw(spriteBatch, Consts.UI_MONEY_ICON_SIZE, Consts.UI_MONEY_X + Consts.UI_MONEY_ICON_OFFSET_X, Consts.UI_MONEY_Y + Consts.UI_MONEY_ICON_OFFSET_Y);

		text = "x" + (1 << (((int) world.getHero().getX()) / Consts.COIN_LOG_ADD_PER_DIST));
		font.draw(spriteBatch, text, Consts.UI_MONEY_MULTIPLIER_X, Consts.UI_MONEY_MULTIPLIER_Y, Consts.UI_MONEY_MULTIPLIER_FONT_SIZE);

		text = "" + MathUtils.ceil(Math.max(0, GameState.get().getRoses()));
		textWidth = text.length() * Consts.UI_LIVES_FONT_SIZE * Consts.UI_FONT_WIDTH_PER_SIZE_AND_CHAR;
		font.draw(spriteBatch, text, (int) (Consts.WIDTH - Consts.UI_LIVES_X - textWidth), Consts.UI_LIVES_Y + Consts.UI_ROSES_OFFSET_Y, Consts.UI_LIVES_FONT_SIZE);
		Icons.ROSE.draw(spriteBatch, Consts.UI_LIVES_ICON_SIZE, Consts.WIDTH - Consts.UI_LIVES_X + Consts.UI_LIVES_ICON_OFFSET_X, Consts.UI_LIVES_Y + Consts.UI_ROSES_OFFSET_Y + Consts.UI_LIVES_ICON_OFFSET_Y);

		text = "" + MathUtils.ceil(Math.max(0, GameState.get().getBridges()));
		textWidth = text.length() * Consts.UI_LIVES_FONT_SIZE * Consts.UI_FONT_WIDTH_PER_SIZE_AND_CHAR;
		font.draw(spriteBatch, text, (int) (Consts.WIDTH - Consts.UI_LIVES_X - textWidth), Consts.UI_LIVES_Y + Consts.UI_BRIDGES_OFFSET_Y, Consts.UI_LIVES_FONT_SIZE);
		Icons.BRIDGE.draw(spriteBatch, Consts.UI_LIVES_ICON_SIZE, Consts.WIDTH - Consts.UI_LIVES_X + Consts.UI_LIVES_ICON_OFFSET_X, Consts.UI_LIVES_Y + Consts.UI_BRIDGES_OFFSET_Y + Consts.UI_LIVES_ICON_OFFSET_Y);

		text = "" + MathUtils.ceil(Math.max(0, world.getHero().getLives()));
		textWidth = text.length() * Consts.UI_LIVES_FONT_SIZE * Consts.UI_FONT_WIDTH_PER_SIZE_AND_CHAR;
		font.draw(spriteBatch, text, (int) (Consts.WIDTH - Consts.UI_LIVES_X - textWidth), Consts.UI_LIVES_Y, Consts.UI_LIVES_FONT_SIZE);
		Icons.HEART.draw(spriteBatch, Consts.UI_LIVES_ICON_SIZE, Consts.WIDTH - Consts.UI_LIVES_X + Consts.UI_LIVES_ICON_OFFSET_X, Consts.UI_LIVES_Y + Consts.UI_LIVES_ICON_OFFSET_Y);

		spriteBatch.end();
	}

	private void shake(float delta){
		if(world.getCameraShake() > 0.5f){
			camera.position.add(MathUtils.randomTriangular(-world.getCameraShake(), world.getCameraShake()),
					MathUtils.randomTriangular(-world.getCameraShake(), world.getCameraShake()), 0);

			world.setCameraShake((float) (world.getCameraShake() * Math.pow(0.1f, delta)), true);
		} else {
			world.setCameraShake(0, true);
		}
	}

	private void update(float delta){
		for(Updatable updatable : new ArrayList<Updatable>(world.getUpdatables())){
			updatable.update(delta);
		}
	}

	private void handleInput(float delta){
		int touchInp = 0;
		if(Gdx.input.isTouched()){
			touchInp = (int) Math.signum(Gdx.input.getX() - Consts.WIDTH / 2);
		}

		if(!wasAttackActionPressed && (Gdx.input.isKeyPressed(Consts.KEY_ATTACK_ACTION) || touchInp == 1)){
			world.getHero().attack();
		}
		if(!wasPlantActionPressed && (Gdx.input.isKeyPressed(Consts.KEY_PLANT_ACTION) || touchInp == -1)){
			world.getHero().plant();
		}

		wasAttackActionPressed = Gdx.input.isKeyPressed(Consts.KEY_ATTACK_ACTION) || touchInp == 1;
		wasPlantActionPressed = Gdx.input.isKeyPressed(Consts.KEY_PLANT_ACTION) || touchInp == -1;
	}

	@Override
	public void resize(int width, int height) {
		screenRatio = (float)width / height;

		Consts.WIDTH = screenRatio * Consts.DEV_HEIGHT;

		camera.viewportWidth = Consts.WIDTH;
		camera.viewportHeight = Consts.HEIGHT;
		uicamera.viewportWidth = Consts.WIDTH;
		uicamera.viewportHeight = Consts.HEIGHT;
		backgroundCamera.viewportWidth = Consts.WIDTH;
		backgroundCamera.viewportHeight = Consts.HEIGHT;

		uicamera.position.set(Consts.WIDTH / 2, Consts.HEIGHT / 2, 0);
		uicamera.update();
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

	public OrthographicCamera getCamera() {
		return camera;
	}

	public OrthographicCamera getBackgroundCamera() {
		return backgroundCamera;
	}
}
