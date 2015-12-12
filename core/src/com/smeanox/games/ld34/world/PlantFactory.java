package com.smeanox.games.ld34.world;

/**
 * Comment
 */
public class PlantFactory {
	public Plant getRandomPlant(World world, float x0, float y0, float targetHeight){
		return new Vine(world, x0, y0, targetHeight);
	}
}
