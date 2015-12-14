package com.smeanox.games.ld34.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.smeanox.games.ld34.Consts;
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
		snowSystem = new ParticleSystem(world, null, Consts.LAYER_WEATHER, Textures.get().particle, new Color(1, 1, 1, 0.8f), 0.75f, 10, 1, 0.01f, 0.001f, 0, 0, Consts.WIDTH*2, 10, 0, 0, 100, 100);
		rainSystem = new ParticleSystem(world, null, Consts.LAYER_WEATHER, Textures.get().particle, new Color(0, 0, 1, 0.5f), 0.5f, 10, 1, 0.01f, 0.001f, 0, 0, Consts.WIDTH*2, 10, 0, 0, 100, 100);
	}

	@Override
	public void update(float delta) {
		if(Consts.DISABLE_WEATHER){
			return;
		}
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

		if(!snowSystem.isGenerating() && !rainSystem.isGenerating()) {
			// Winter
			switch (season) {
				case 0:
					winter(delta);
					break;
				case 1:
					spring(delta);
					break;
				case 2:
					summer(delta);
					break;
				case 3:
					autumn(delta);
					break;
			}
		}

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

	private void winter(float delta){
		if(MathUtils.randomBoolean(Consts.WEATHER_STORM_PROBABILITY * delta)){
			snowStorm();
		} else if(MathUtils.randomBoolean(Consts.WEATHER_NORMAL_PROBABILITY * delta)){
			snowNormal();
		}
	}

	private void spring(float delta){
		if(MathUtils.randomBoolean(Consts.WEATHER_STORM_PROBABILITY * delta)){
			snowNormal();
		} else if(MathUtils.randomBoolean(Consts.WEATHER_NORMAL_PROBABILITY * delta)){
			rainNormal();
		}
	}

	private void summer(float delta){
		if(MathUtils.randomBoolean(Consts.WEATHER_STORM_PROBABILITY * delta)){
			rainStorm();
		} else if(MathUtils.randomBoolean(Consts.WEATHER_NORMAL_PROBABILITY * delta)){
			rainNormal();
		}
	}

	private void autumn(float delta){
		if(MathUtils.randomBoolean(Consts.WEATHER_STORM_PROBABILITY * delta)){
			rainStorm();
		} else if(MathUtils.randomBoolean(Consts.WEATHER_NORMAL_PROBABILITY * delta)){
			snowNormal();
		}
	}

	private void snowStorm(){
		System.out.println("snow storm");
		initSystem(snowSystem, Consts.WEATHER_STORM_MAX_VELO, Consts.WEATHER_STORM_MIN_RATE, Consts.WEATHER_STORM_MAX_RATE);
	}

	private void snowNormal(){
		System.out.println("snow normal");
		initSystem(snowSystem, Consts.WEATHER_NORMAL_MAX_VELO, Consts.WEATHER_NORMAL_MIN_RATE, Consts.WEATHER_NORMAL_MAX_RATE);
	}

	private void rainStorm(){
		System.out.println("rain storm");
		initSystem(rainSystem, Consts.WEATHER_STORM_MAX_VELO, Consts.WEATHER_STORM_MIN_RATE, Consts.WEATHER_STORM_MAX_RATE);
	}

	private void rainNormal(){
		System.out.println("rain normal");
		initSystem(rainSystem, Consts.WEATHER_NORMAL_MAX_VELO, Consts.WEATHER_NORMAL_MIN_RATE, Consts.WEATHER_NORMAL_MAX_RATE);
	}

	private void initSystem(ParticleSystem system, float maxVelo, float minRate, float maxRate){
		float duration = MathUtils.random(Consts.WEATHER_MIN_DURATION, Consts.WEATHER_MAX_DURATION);
		system.setAutoDisable(duration);
		system.setStartVeloX(MathUtils.randomTriangular(-maxVelo, maxVelo));
		system.setStartVeloXRand(system.getStartVeloX() / 10);
		system.setRate(MathUtils.randomTriangular(minRate, maxRate));
		system.setRateRand(system.getRate() / 10);
		system.setGenerating(true);
	}
}
