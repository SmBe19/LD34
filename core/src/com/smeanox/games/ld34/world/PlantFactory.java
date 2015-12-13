package com.smeanox.games.ld34.world;

/**
 * Comment
 */
public class PlantFactory {

	public static Plant getRandomPlant(World world, float x0, float y0){
		return new Thorn(world, x0, y0);
	}
    public static PlantFactory getInstance(){
        return new PlantFactory();
    }
    public static Plant justGimmeTheFrikkinNoicePlantPlox(World world, float x0, float y0, float targetHeight){
        return new Vine(world, x0, y0, targetHeight);
    }
}
