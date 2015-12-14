package com.smeanox.games.ld34.world;

import com.badlogic.gdx.math.MathUtils;

/**
 * Comment
 */
public class TauntManager {

	private World world;
	private String currentTaunt;

	public static final String[] falling = {"mike out of bounds", "you fell", "still falling...", "It's pretty deep\nisn't it?"};
	public static final String[] thorn = {"It's just a plant", "It didn't even move", "Why didn't you\nuse your axe?", "You have an axe\ndon't you?", "What were you looking at?"};
	public static final String[] groundEnemy = {"Brains...", "Flowers...", "Don't step on the flowers!", "What did you do?"};
	public static final String[] selfHit = {"Congrats now you're dead", "Seriously?", "Don't do that", "Just don't do that", "Seriously\nDon't do that", "It's not that hard\nto usa an axe"};
	public static final String[] unknown = {"no idea"};

	public TauntManager(World world){
		this.world = world;

		currentTaunt = "";
	}

	public void setRandomTaunt(String[] list){
		if(!world.getHero().isAlive()){
			return;
		}

		currentTaunt = list[MathUtils.random(list.length - 1)];
	}

	public String getCurrentTaunt() {
		return currentTaunt;
	}

	public void setCurrentTaunt(String currentTaunt) {
		this.currentTaunt = currentTaunt;
	}
}
