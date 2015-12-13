package com.smeanox.games.ld34.world;

import com.smeanox.games.ld34.Consts;

/**
 * Comment
 */
public class GameState {
	private static GameState singleton;

	private long money;
	private long bridges = 0;
	private long roses = 0;
	private long healthUpgrades = 0;
	private long damageUpgrades = 0;

	private GameState(){
		money = Consts.HERO_START_MONEY;
	}

	public static GameState get(){
		if(singleton == null){
			singleton = new GameState();
		}
		return singleton;
	}

	public float getHeroHealth(){
		return Consts.HERO_START_LIVES * (float)Math.pow(Consts.UPGRADE_HEALTH_BONUS, healthUpgrades);
	}
	public float getHeroDamage(){
		return Consts.HERO_DAMAGE * (float)Math.pow(Consts.UPGRADE_DAMAGE_BONUS, damageUpgrades);
	}
	
	public boolean isAbleToUpgradeHealth(){
		return getMoney() >= getHeroHealthUpgradeCost();
	}

	public boolean isAbleToUpgradeDamage(){
		return getMoney() >= getHeroDamageUpgradeCost();
	}

	public long getHeroDamageUpgradeCost(){
		return (long)Math.floor(Consts.UPGRADE_DAMAGE_COST * (float)Math.pow(Consts.UPGRADE_DAMAGE_BONUS, damageUpgrades));
	}

	public long getHeroHealthUpgradeCost(){
		return (long)Math.floor(Consts.UPGRADE_HEALTH_COST * (float) Math.pow(Consts.UPGRADE_HEALTH_BONUS, healthUpgrades));
	}
	
	public void upgradeHealth(){
		if (!isAbleToUpgradeHealth()) return;
		healthUpgrades++;
		money -= getHeroHealthUpgradeCost();
	}

	public void upgradeDamage(){
		if (!isAbleToUpgradeDamage()) return;
		damageUpgrades++;
		money -= getHeroDamageUpgradeCost();
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
