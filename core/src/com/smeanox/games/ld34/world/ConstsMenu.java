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

	public static final int ITEMS_OFFSET_X = 100;
	public static final int ITEMS_ICON_OFFSET_X = 50;
	public static final int ITEMS_OFFSET_Y = 200;
	public static final int ITEMS_SPACING_Y = 40;
	public static final int FONT_SIZE = 3;
	public static final int ICON_SIZE = 4;
	public static final Color INACTIVE_ITEM_COLOR = Color.WHITE;
	public static final Color ACTIVE_ITEM_COLOR = Color.RED;

	static {
		GAME_TITLE_SPLIT = GAME_TITLE.split("\n");
	}
}
