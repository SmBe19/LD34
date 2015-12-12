package com.smeanox.games.ld34.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.smeanox.games.ld34.Consts;
import com.smeanox.games.ld34.Textures;
import com.smeanox.games.ld34.world.Renderable;
import com.smeanox.games.ld34.world.Updatable;
import com.smeanox.games.ld34.world.World;

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

	public GameScreen(Game game) {
		this.game = game;

		Textures.get().finishLoading();

		screenRatio = Consts.DEV_WIDTH / Consts.DEV_HEIGHT;

		camera = new OrthographicCamera(Consts.DEV_WIDTH, Consts.DEV_HEIGHT);
		backgroundCamera = new OrthographicCamera(Consts.DEV_WIDTH, Consts.DEV_HEIGHT);

		spriteBatch = new SpriteBatch();

		world = new World();

		background = Textures.get().background;

		wasAttackActionPressed = wasPlantActionPressed = false;
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		handleInput(delta);
		update(delta);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// camera.position.set(world.getHero().getX(), Math.max(world.getHero().getY(), -Consts.HEIGHT), 0);
		camera.position.set(world.getHero().getX(), world.getHero().getY(), 0);
		backgroundCamera.position.set(camera.position.x / 10, camera.position.y / 10, 0);

		camera.update();
		backgroundCamera.update();

		spriteBatch.setProjectionMatrix(backgroundCamera.combined);

		spriteBatch.begin();
		for (int i = -1; i < 2; i++) {
			spriteBatch.draw(background, i * (background.getWidth() * Consts.HEIGHT * 2 / background.getHeight()),
					-Consts.HEIGHT, background.getWidth() * Consts.HEIGHT * 2 / background.getHeight(), Consts.HEIGHT * 2);
		}
		spriteBatch.end();

		spriteBatch.setProjectionMatrix(camera.combined);

		spriteBatch.begin();
		for(Renderable renderable : world.getRenderables()){
			renderable.render(delta, spriteBatch);
		}
		spriteBatch.end();
	}

	private void update(float delta){
		for(Updatable updatable : world.getUpdatables()){
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
