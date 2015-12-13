package com.smeanox.games.ld34.world;

import com.smeanox.games.ld34.Consts;

/**
 * Comment
 */
public class GameState {
	private static GameState singleton;

	private long money;

	private GameState(){
		money = Consts.HERO_START_MONEY;
	}

	public static GameState get(){
		if(singleton == null){
			singleton = new GameState();
		}
		return singleton;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	public void addMoney(long money){
		this.money += money;
	}
}
