package com.smeanox.games.ld34.world;

import com.badlogic.gdx.math.MathUtils;
import com.smeanox.games.ld34.Consts;

/**
 * Comment
 */
public class GameState {
	private static GameState singleton;

	private long money;
	private long bridges;
	private long roses;
	private long healthUpgrades;
	private long damageUpgrades;

	private long lastMoney;
	private float lastMoneyTime;
	private float moneyGrowth;
	private long knownMoney;

	private GameState(){
		money = Consts.HERO_START_MONEY;
		roses = Consts.HERO_START_ROSES;
		bridges = Consts.HERO_START_BRIDGES;
		healthUpgrades = 0;
		damageUpgrades = 0;

		moneyGrowth = 1;
		lastMoneyTime = 0;
		lastMoney = money;
		knownMoney = money;
	}

	public static GameState get(){
		if(singleton == null){
			singleton = new GameState();
		}
		return singleton;
	}

	public float getHeroHealth(){
		return Consts.HERO_START_LIVES + Consts.UPGRADE_HEALTH_AMOUNT * healthUpgrades;
	}
	public float getHeroDamage(){
		return Consts.HERO_START_DAMAGE + Consts.UPGRADE_DAMAGE_AMOUNT * damageUpgrades;
	}
	
	public boolean isAbleToUpgradeHealth(){
		return getMoney() >= getHeroHealthUpgradeCost();
	}

	public boolean isAbleToUpgradeDamage(){
		return getMoney() >= getHeroDamageUpgradeCost();
	}
	
	public boolean isAbleToBuyRose(){
		return getMoney() >= getRoseCost();
	}
	
	public boolean isAbleToBuyBridge(){
		return getMoney() >= getBridgeCost();
	}


	public long getHeroDamageUpgradeCost(){
		return (long)Math.floor(Consts.UPGRADE_DAMAGE_COST * (float)Math.pow(Consts.UPGRADE_DAMAGE_BONUS, damageUpgrades));
	}

	public long getHeroHealthUpgradeCost(){
		return (long)Math.floor(Consts.UPGRADE_HEALTH_COST * (float) Math.pow(Consts.UPGRADE_HEALTH_BONUS, healthUpgrades));
	}

	public long getBridgeCost(){
		return (long)(Math.pow(Consts.BRIDGE_COST_MULT, bridges)*Consts.BRIDGE_BASE_COST);
	}
	
	public long getRoseCost(){
		return (long)(Math.pow(Consts.ROSE_COST_MULT, roses)*Consts.ROSE_BASE_COST);
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

	public void buyRose(){
		if (!isAbleToBuyRose()) return;
		setRoses(getRoses() + 1);
		money -= getRoseCost();
	}

	public void buyBridge(){
		if (!isAbleToBuyBridge()) return;
		setBridges(getBridges() + 1);
		money -= getBridgeCost();
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

	public long getBridges() {
		return bridges;
	}

	public long getRoses() {
		return roses;
	}

	public void setBridges(long bridges) {
		this.bridges = bridges;
	}

	public void setRoses(long roses) {
		this.roses = roses;
	}

	public float getMoneyGrowth(){
		return Math.max(Math.min(1.5f, moneyGrowth), 0.5f);
	}

	public long getVisibleMoney(float totalTime) {
		float delta = totalTime - lastMoneyTime;
		if(knownMoney != money){
			lastMoneyTime = totalTime;
			if(delta > 0.1e-5 && delta < 1){
				lastMoney += delta * (knownMoney - lastMoney) / Consts.COINS_COUNT_DURATION;
			} else {
				lastMoney = knownMoney;
			}
			moneyGrowth = 1;
			delta = 0;
			knownMoney = money;
		}
		if(lastMoney == money){
			return money;
		}
		if(delta < 0.1e-5){
			moneyGrowth = 1;
			return lastMoney;
		}
		if(delta > 1){
			moneyGrowth = 1;
			lastMoney = money;
			return money;
		}

		moneyGrowth = 1 + 2 * (delta - delta*delta);

		return MathUtils.round(lastMoney + delta * (money - lastMoney) / Consts.COINS_COUNT_DURATION);
	}
}
