package com.smeanox.games.ld34.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.smeanox.games.ld34.Consts;
import com.smeanox.games.ld34.Icons;
import com.smeanox.games.ld34.Textures;

/**
 * Comment
 */
public class WeatherSimulation implements Updatable {
	private World world;
	private ParticleSystem snowSystem;
	private ParticleSystem rainSystem;

	private float passedTime;
	private int season, oldSeason;

	public WeatherSimulation(World world) {
		this.world = world;
		world.getUpdatables().add(this);

		passedTime = MathUtils.random(0f, Consts.WEATHER_YEAR_LENGTH);

		season = -1;
		oldSeason = -1;

		initParticles();
	}

	private void initParticles(){
		snowSystem = new ParticleSystem(world, "snow", null, Consts.LAYER_WEATHER, Textures.get().particle, Color.WHITE, 0.75f, 10, 1, 0.01f, 0.001f, 0, 0, Consts.WIDTH*2, 10, 0, 0, 100, 100);
		rainSystem = new ParticleSystem(world, "rain", null, Consts.LAYER_WEATHER, Textures.get().particle, Color.BLUE, 0.5f, 10, 1, 0.01f, 0.001f, 0, 0, Consts.WIDTH*2, 10, 0, 0, 100, 100);
	}

	@Override
	public void update(float delta) {
		passedTime += delta;
		passedTime %= Consts.WEATHER_YEAR_LENGTH;

		snowSystem.setStartX(world.getHero().getX());
		snowSystem.setStartY(world.getHero().getY() + Consts.WEATHER_OFFSET_Y);
		rainSystem.setStartX(world.getHero().getX());
		rainSystem.setStartY(world.getHero().getY() + Consts.WEATHER_OFFSET_Y);

		season = MathUtils.floor(passedTime / Consts.WEATHER_YEAR_LENGTH * 4);
		if (season != oldSeason) {
			System.out.println("new season: " + season);
			endSeason();
		}

		// Winter
		switch (season){
			case 0:
				winter();
				break;
			case 1:
				spring();
				break;
			case 2:
				summer();
				break;
			case 3:
				autumn();
				break;
		}

		snowSystem.setGenerating(true);

		oldSeason = season;
	}

	private void endSeason() {
		resetSystem(snowSystem);
		resetSystem(rainSystem);
	}

	private void resetSystem(ParticleSystem system){
		system.setGenerating(false);
		system.setTimeout(Float.POSITIVE_INFINITY);
		system.setAutoDisable(Float.POSITIVE_INFINITY);
	}

	private void winter(){
		if(snowSystem.isGenerating() || rainSystem.isGenerating()){
			return;
		}
	}

	private void spring(){
		if(snowSystem.isGenerating() || rainSystem.isGenerating()){
			return;
		}
	}

	private void summer(){
		if(snowSystem.isGenerating() || rainSystem.isGenerating()){
			return;
		}
	}

	private void autumn(){
		if(snowSystem.isGenerating() || rainSystem.isGenerating()){
			return;
		}
	}
}
