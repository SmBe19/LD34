package com.smeanox.games.ld34;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Comment
 */
public class Sounds {
	private static Sounds singleton;

	public Sound axeswing;
	public Sound blub;
	public Sound climb;
	public Sound coin;
	public Sound destroyCoin;
	public Sound fall;
	public Sound hurt;
	public Sound land;
	public Sound miss;
	public Sound plant;
	public Sound rose;
	public Sound selfhit;
	public Sound start;
	public Sound walk;

	private Map<Sound, Long> lastPlayTime;
	private List<Sound> sounds;

	private boolean muted;

	private Sounds(){
		muted = Consts.IS_DEFAULT_MUTED;
		sounds = new ArrayList<Sound>();
		lastPlayTime = new HashMap<Sound, Long>();
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
		climb = Gdx.audio.newSound(Gdx.files.internal("sounds/climb.wav"));
		coin = Gdx.audio.newSound(Gdx.files.internal("sounds/coin.wav"));
		destroyCoin = Gdx.audio.newSound(Gdx.files.internal("sounds/destroyCoin.wav"));
		fall = Gdx.audio.newSound(Gdx.files.internal("sounds/fall.wav"));
		hurt = Gdx.audio.newSound(Gdx.files.internal("sounds/hurt.wav"));
		land = Gdx.audio.newSound(Gdx.files.internal("sounds/land.wav"));
		miss = Gdx.audio.newSound(Gdx.files.internal("sounds/miss.wav"));
		plant = Gdx.audio.newSound(Gdx.files.internal("sounds/plant.wav"));
		rose = Gdx.audio.newSound(Gdx.files.internal("sounds/rose.wav"));
		selfhit = Gdx.audio.newSound(Gdx.files.internal("sounds/selfhit.wav"));
		start = Gdx.audio.newSound(Gdx.files.internal("sounds/start.wav"));
		walk = Gdx.audio.newSound(Gdx.files.internal("sounds/walk.wav"));

		sounds.add(axeswing);
		sounds.add(blub);
		sounds.add(climb);
		sounds.add(coin);
		sounds.add(destroyCoin);
		sounds.add(fall);
		sounds.add(hurt);
		sounds.add(land);
		sounds.add(miss);
		sounds.add(plant);
		sounds.add(rose);
		sounds.add(selfhit);
		sounds.add(start);
		sounds.add(walk);
	}

	public long playOne(Sound sound){
		return playOne(sound, Consts.SOUND_DURATION);
	}

	public long playOne(Sound sound, long offset){
		if(muted){
			return -1;
		}
		if(lastPlayTime.containsKey(sound) && System.currentTimeMillis() - lastPlayTime.get(sound) < offset){
			return -1;
		}
		return play(sound);
	}

	public long play(Sound sound){
		if(muted){
			return -1;
		}
		lastPlayTime.put(sound, System.currentTimeMillis());
		return sound.play(Consts.SOUND_EFFECT_VOLUME);
	}

	public void stop(Sound sound){
		sound.stop();
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
