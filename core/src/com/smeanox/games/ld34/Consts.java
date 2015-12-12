package com.smeanox.games.ld34;

import com.badlogic.gdx.Input;

/**
 * Comment
 */
public class Consts {
	public static final int DEV_WIDTH = 800;
	public static final int DEV_HEIGHT = 480;
	public static float WIDTH = DEV_WIDTH;
	public static float HEIGHT = DEV_HEIGHT;

	public static final int CAMERA_OFFSET_X = 250;
	public static final int GROUND_HEIGHT = -150;
	public static final int GROUND_THICKNESS = 500;
	public static final float GRAVITY = -400;
	public static final float FRICTION = 0.8f;
	public static final float BOUNCINESS_MIN_Y = 40;
	public static final float BOUNCINESS_MIN_X = 10;
	public static final float BOUNCINESS = 0.25f;
	public static final float FALLING_FOR_LIMIT = 0.2f;
	public static final float HERO_VELO = 150;
	public static final float HERO_CLIMB_VELO = 60f;
	public static final float HERO_START_LIVES = 100f;
	public static final float HERO_JUMP_VELO = 10f;
	public static final float BUILDING_TOP_MARGIN = 10f;
	public static final float PLANT_TOP_MARGIN = 10f;

	public static final int KEY_PLANT_ACTION = Input.Keys.A;
	public static final int KEY_ATTACK_ACTION = Input.Keys.D;

	public static final int LAYER_WORLD = 0;
	public static final int LAYER_GROUND = 1;
	public static final int LAYER_BUILDING = 2;
	public static final int LAYER_PLANT = 3;
	public static final int LAYER_HERO = 10;

	public static final int GROUNDPART_MIN_DIST = 100;
	public static final int GROUNDPART_MAX_DIST = 250;
	public static final int GROUNDPART_MIN_WIDTH = 900;
	public static final int GROUNDPART_MAX_WIDTH = 3000;
	public static final int GROUNDPART_END_MIN_WIDTH = 25;
	public static final int GROUNDPART_END_MAX_WIDTH = 200;
	public static final int BUILDING_MIN_DIST = 25;
	public static final int BUILDING_MAX_DIST = 250;
	public static final int BUILDING_MIN_WIDTH = 3;
	public static final int BUILDING_MAX_WIDTH = 9;
	public static final int BUILDING_MIN_HEIGHT = 2;
	public static final int BUILDING_MAX_HEIGHT = 14;

	public static final int GROUND_TEX_OFFSET_Y = 50;
	public static final int GROUND_TEX_HEIGHT = 100;
	public static final int HERO_TEX_WIDTH = 24;
	public static final int HERO_TEX_HEIGHT = 24;
	public static final int BUILDING_TEX_WIDTH = 32;
	public static final int BUILDING_TEX_HEIGHT = 32;
	public static final int VINE_STEP = 8;
	public static final int VINE_TEXTURE_HEIGHT = 16;
	public static final int VINE_TEXTURE_WIDTH = 16;
	public static final float VINE_GROW_RATE = 60;

	public static final int THORN_TEX_WIDTH = 32;
	public static final int THORN_TEX_HEIGHT = 32;

	public static final float HERO_TEX_ZOOM = 2.4f;
	public static final float BUILDING_TEX_ZOOM = HERO_TEX_ZOOM * 0.75f;
	public static final float VINE_TEX_ZOOM = HERO_TEX_ZOOM;
	public static final float THORN_TEX_ZOOM = HERO_TEX_ZOOM;
}
