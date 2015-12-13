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

	public static final int GROUND_TEX_OFFSET_Y = 50;
	public static final int GROUND_TEX_HEIGHT = 100;
	public static final int HERO_TEX_WIDTH = 24;
	public static final int HERO_TEX_HEIGHT = 24;
	public static final int BUILDING_TEX_WIDTH = 32;
	public static final int BUILDING_TEX_HEIGHT = 32;
	public static final int VINE_TEXTURE_HEIGHT = 16;
	public static final int VINE_TEXTURE_WIDTH = 16;
	public static final int BRIDGE_TEXTURE_HEIGHT = 16;
	public static final int BRIDGE_TEXTURE_WIDTH = 16;

	public static final float HERO_TEX_ZOOM = 2.5f;
	public static final float BUILDING_TEX_ZOOM = HERO_TEX_ZOOM * 0.75f;
	public static final float VINE_TEX_ZOOM = HERO_TEX_ZOOM;
	public static final float BRIDGE_TEX_ZOOM = HERO_TEX_ZOOM;
	public static final float THORN_TEX_ZOOM = HERO_TEX_ZOOM;
	public static final float COIN_TEX_ZOOM = HERO_TEX_ZOOM;

	public static final int CAMERA_OFFSET_X = 250;
	public static final int GROUND_HEIGHT = -150;
	public static final int GROUND_THICKNESS = 500;
	public static final float GRAVITY = -350;
	public static final float FRICTION = 0.8f;
	public static final float BOUNCINESS_MIN_Y = 40;
	public static final float BOUNCINESS_MIN_X = 10;
	public static final float BOUNCINESS = 0.25f;
	public static final float FALLING_FOR_LIMIT = 0.2f;
	public static final float HERO_VELO = 150;
	public static final float HERO_CLIMB_VELO = 60f;
	public static final float HERO_START_LIVES = 10;
	public static final long HERO_START_MONEY = 100;
	public static final float HERO_DAMAGE = 2;
	public static final float HERO_JUMP_VELO = 300;
	public static final float PLANT_TOP_MARGIN = 10;
	public static final float HERO_ATTACK_RANGE_X = HERO_TEX_WIDTH * HERO_TEX_ZOOM + 50;
	public static final float HERO_ATTACK_RANGE_Y = HERO_TEX_HEIGHT * HERO_TEX_ZOOM;

	public static final int KEY_PLANT_ACTION = Input.Keys.A;
	public static final int KEY_ATTACK_ACTION = Input.Keys.D;

	public static final int LAYER_WORLD = 0;
	public static final int LAYER_GROUND = 1;
	public static final int LAYER_BUILDING = 2;
	public static final int LAYER_ROSE = 3;
	public static final int LAYER_PLANT = 4;
	public static final int LAYER_ENEMY = 5;
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
	public static final int PLANT_MIN_DIST = 400;
	public static final int PLANT_MAX_DIST = 800;
	public static final float PLANT_ON_ROOF_CHANCE = 0.5f;
	public static final float VINE_ON_CLIMBING_OFFSET_X = 100;
	public static final float VINE_ON_CLIMBING_OFFSET_Y = 20;
	public static final int COIN_MIN_DIST = 20;
	public static final int COIN_MAX_DIST = 80;
	public static final float COIN_ON_ROOF_CHANCE = 0.8f;
	public static final int VINE_STEP = 8;
	public static final float VINE_GROW_RATE = 70;
	public static final float VINE_START_LIVES = Float.POSITIVE_INFINITY;
	public static final int BRIDGE_STEP = 8;
	public static final float BRIDGE_GROW_RATE = 200;
	public static final float BRIDGE_START_LIVES = Float.POSITIVE_INFINITY;

	public static final int ENEMY_TEX_HEIGHT = 24;
	public static final int ENEMY_TEX_WIDTH = 24;
	public static final float ENEMY_TEX_ZOOM = HERO_TEX_ZOOM;
	public static final int ENEMY_MIN_DIST = 200;
	public static final int ENEMY_MAX_DIST = 400;
	public static final float ENEMY_DAMAGE_PER_SECOND = 3;
	public static final float ENEMY_DAMAGE_ON_TOP = 0;
	public static final float ENEMY_ON_ROOF_CHANCE = 0.8f;
	public static final float ENEMY_SPEED = 32;
	public static final float ENEMY_DIST_TO_HERO = 500;
	public static final float ENEMY_START_LIVES = 1;
	public static final float ENEMY_LIVES_PER_DIST = 0.0001f;

	public static final int THORN_TEX_WIDTH = 32;
	public static final int THORN_TEX_HEIGHT = 32;
	public static final float THORN_DAMAGE_PER_SECOND = 10;
	public static final float THORN_DAMAGE_ON_TOP = 4;
	public static final float THORN_START_LIVES = 1;
	public static final float THORN_LIVES_PER_DIST = 0.0001f;

	public static final int COIN_TEX_WIDTH = 16;
	public static final int COIN_TEX_HEIGHT = 16;
	public static final float COIN_START_LIVES = 1;
	public static final float COIN_OFFSET_Y = 12;
	public static final int COIN_LOG_ADD_PER_DIST = 10000;
	public static final float COIN_ATTRACTION = -130000f;
	public static final float COIN_VELOCITY = 800f;

	public static final int HERO_SPAMMING_LIMIT = 100;
	public static final int HERO_SPAMMING_DAMAGE = 1;
	public static final float HERO_SPAMMING_TIME_LIMIT = 10;
	public static final float DEATH_TIMEOUT = 2;

	public static final float CAMERA_SHAKE_LANDING_PER_VELO_Y = 0.0001f;
	public static final float CAMERA_SHAKE_ATTACK_PER_DAMAGE = 30;


	public static final int ROSE_TEX_HEIGHT = 24;
	public static final int ROSE_TEX_WIDTH = 24;
	public static final float ROSE_TEX_ZOOM = HERO_TEX_ZOOM;
	public static final float ROSE_HP_BONUS = 0.5f; //relative to full hp
	public static final float ROSE_START_LIVES = 1;

	public static final float UPGRADE_HEALTH_BONUS = 1.5f;
	public static final float UPGRADE_DAMAGE_BONUS = 1.5f;
	public static final float UPGRADE_HEALTH_COST = 1.5f;
	public static final float UPGRADE_DAMAGE_COST = 1.5f;

	public static final int UI_MONEY_X = 60;
	public static final int UI_MONEY_Y = 10;
	public static final int UI_MONEY_ICON_OFFSET_X = -50;
	public static final int UI_MONEY_ICON_OFFSET_Y = 0;
	public static final int UI_MONEY_FONT_SIZE = 3;
	public static final int UI_MONEY_ICON_SIZE = 4;
	public static final int UI_LIVES_X = 100;
	public static final int UI_LIVES_Y = 10;
	public static final int UI_LIVES_ICON_OFFSET_X = -50;
	public static final int UI_LIVES_ICON_OFFSET_Y = 0;
	public static final int UI_LIVES_FONT_SIZE = 4;
	public static final int UI_LIVES_ICON_SIZE = 4;
}
