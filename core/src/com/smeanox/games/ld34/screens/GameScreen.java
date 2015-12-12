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
import com.smeanox.games.ld34.Textures;
import com.smeanox.games.ld34.world.Renderable;
import com.smeanox.games.ld34.world.Updatable;
import com.smeanox.games.ld34.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Comment
 */
public class GameScreen implements Screen {

	private Game game;
	private OrthographicCamera camera;
	private OrthographicCamera backgroundCamera;
	private SpriteBatch spriteBatch;
	private float screenRatio;

	private Texture background;

	private World world;

	private boolean wasPlantActionPressed, wasAttackActionPressed;

	private FPSLogger fpsLogger = new FPSLogger();

	public GameScreen(Game game) {
		this.game = game;

		Textures.get().finishLoading();

		screenRatio = Consts.DEV_WIDTH / Consts.DEV_HEIGHT;

		camera = new OrthographicCamera(Consts.DEV_WIDTH, Consts.DEV_HEIGHT);
		backgroundCamera = new OrthographicCamera(Consts.DEV_WIDTH, Consts.DEV_HEIGHT);

		spriteBatch = new SpriteBatch();

		world = new World(camera);

		background = Textures.get().background;

		wasAttackActionPressed = wasPlantActionPressed = false;
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		fpsLogger.log();

		handleInput(delta);
		update(delta);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(!world.getHero().isAlive()){
			game.setScreen(new GameScreen(game));
		}

		camera.position.set(MathUtils.roundPositive(world.getHero().getX() + Consts.CAMERA_OFFSET_X),
				MathUtils.roundPositive(Math.max(world.getHero().getY() - Consts.GROUND_HEIGHT, -Consts.HEIGHT * 5)), 0);
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
	}

	private void update(float delta){
		for(Updatable updatable : new ArrayList<Updatable>(world.getUpdatables())){
			updatable.update(delta);
		}
	}

	private void handleInput(float delta){
		if(!wasAttackActionPressed && Gdx.input.isKeyPressed(Consts.KEY_ATTACK_ACTION)){
			world.getHero().attack();
		}
		if(!wasPlantActionPressed && Gdx.input.isKeyPressed(Consts.KEY_PLANT_ACTION)){
			world.getHero().plant();
		}

		wasAttackActionPressed = Gdx.input.isKeyPressed(Consts.KEY_ATTACK_ACTION);
		wasPlantActionPressed = Gdx.input.isKeyPressed(Consts.KEY_PLANT_ACTION);
	}

	@Override
	public void resize(int width, int height) {
		System.out.println("new size: " + width + " / " + height);

		screenRatio = (float)width / height;

		Consts.WIDTH = screenRatio * Consts.DEV_HEIGHT;

		camera.viewportWidth = Consts.WIDTH;
		camera.viewportHeight = Consts.HEIGHT;
		backgroundCamera.viewportWidth = Consts.WIDTH;
		backgroundCamera.viewportHeight = Consts.HEIGHT;
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
