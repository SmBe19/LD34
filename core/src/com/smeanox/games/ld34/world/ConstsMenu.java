package com.smeanox.games.ld34.world;

import com.badlogic.gdx.graphics.Color;

/**
 * Comment
 */
public class ConstsMenu {
	public static final String GAME_TITLE = "Mike's Garden\nSimulater 2015\nPlatinum Edition";
	public static final String[] GAME_TITLE_SPLIT;
	public static final int TITLE_OFFSET_X = 100;
	public static final int TITLE_OFFSET_Y = 50;
	public static final Color TITLE_COLOR = new Color(0.2f, 0.8f, 0.2f, 1);
	public static final Color TITLE_COLOR_SHADOW = new Color(0.6f, 1f, 0.6f, 1);

	public static final int FONT_SIZE = 3;
	public static final int ICON_SIZE = 4;
	public static final int ITEMS_OFFSET_X = 100;
	public static final int ITEMS_ICON_OFFSET_X = 50;
	public static final int ITEMS_PRICE_OFFSET_X = 70;
	public static final int ITEMS_PRICE_ICON_OFFSET_X = 60;
	public static final int ITEMS_PRICE_OFFSET_PER_CHAR = 8 * FONT_SIZE;
	public static final int ITEMS_OFFSET_Y = 200;
	public static final int ITEMS_SPACING_Y = 40;
	public static final Color INACTIVE_ITEM_COLOR = Color.WHITE;
	public static final Color ACTIVE_ITEM_COLOR = Color.RED;
	public static final Color CAN_BUY_ITEM_COLOR = Color.WHITE;
	public static final Color CANNOT_BUT_ITEM_COLOR = Color.DARK_GRAY;

	public static final int KEY_ICON_SIZE = 4;
	public static final int KEY_FONT_SIZE = 2;
	public static final int KEY_TEXT_OFFSET_X = 10;
	public static final int KEY_TEXT_OFFSET_Y = 8;
	public static final int KEY_ATTACK_X = 42;
	public static final int KEY_ATTACK_Y = 42;
	public static final int KEY_PLANT_X = 10;
	public static final int KEY_PLANT_Y = 42;

	static {
		GAME_TITLE_SPLIT = GAME_TITLE.split("\n");
	}
}
