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

	public static final int GROUND_HEIGHT = -50;
	public static final int GROUND_THICKNESS = 100;
	public static final float GRAVITY = -9.81f;
	public static final float FALLING_FOR_LIMIT = 0.2f;
	public static final float HERO_VELO = 5;

	public static final int KEY_PLANT_ACTION = Input.Keys.A;
	public static final int KEY_ATTACK_ACTION = Input.Keys.D;

	public static final int GROUND_TEX_OFFSET_Y = 50;
	public static final int GROUND_TEX_HEIGHT = 100;
	public static final int HERO_TEX_WIDTH = 24;
	public static final int HERO_TEX_HEIGHT = 24;
	public static final float HERO_TEX_ZOOM = 5;
}
