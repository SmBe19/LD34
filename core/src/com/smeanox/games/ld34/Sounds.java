package com.smeanox.games.ld34;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

/**
 * Comment
 */
public class Sounds {
	private static Sounds singleton;

	public Sound axeswing;
	public Sound blub;
	public Sound coin;
	public Sound destroyCoin;
	public Sound hurt;
	public Sound land;
	public Sound miss;
	public Sound rose;
	public Sound selfhit;
	public Sound start;

	private List<Sound> sounds;

	private boolean muted;

	private Sounds(){
		muted = false;
		sounds = new ArrayList<Sound>();
	}

	public static Sounds get(){
		if (singleton == null){
			singleton = new Sounds();
		}
		return singleton;
	}

	public void load(){
		axeswing = Gdx.audio.newSound(Gdx.files.internal("sounds/axeswing.wav"));
		blub = Gdx.audio.newSound(Gdx.files.internal("sounds/blub.wav"));
		coin = Gdx.audio.newSound(Gdx.files.internal("sounds/coin.wav"));
		destroyCoin = Gdx.audio.newSound(Gdx.files.internal("sounds/destroyCoin.wav"));
		hurt = Gdx.audio.newSound(Gdx.files.internal("sounds/hurt.wav"));
		land = Gdx.audio.newSound(Gdx.files.internal("sounds/land.wav"));
		miss = Gdx.audio.newSound(Gdx.files.internal("sounds/miss.wav"));
		rose = Gdx.audio.newSound(Gdx.files.internal("sounds/rose.wav"));
		selfhit = Gdx.audio.newSound(Gdx.files.internal("sounds/selfhit.wav"));
		start = Gdx.audio.newSound(Gdx.files.internal("sounds/start.wav"));

		sounds.add(axeswing);
		sounds.add(blub);
		sounds.add(coin);
		sounds.add(destroyCoin);
		sounds.add(hurt);
		sounds.add(land);
		sounds.add(miss);
		sounds.add(rose);
		sounds.add(selfhit);
		sounds.add(start);
	}

	public long play(Sound sound){
		return sound.play();
	}

	public boolean isMuted() {
		return muted;
	}

	public void setMuted(boolean muted) {
		this.muted = muted;
	}

	public void dispose(){
		for(Sound sound : sounds){
			sound.dispose();
		}
	}
}
