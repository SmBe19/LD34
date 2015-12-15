package com.smeanox.games.ld34;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.MathUtils;
import com.smeanox.games.ld34.world.Updatable;

import java.util.ArrayList;

/**
 * Comment
 */
public class MusicManager implements Updatable, Music.OnCompletionListener {
	private static MusicManager singleton;

	private Music bass, sawbass1, sawbass2, sawbassarp, dance_arpeggio, dance_slow, hihat, jungle, keys,
			kick,lead_end,lead_loop,lead_opening,pad_snow1,pad_snow_high, bassgroove;

	private boolean playing;
	private int cycle = 8046;

	private MusicManager(){
		playing = !Consts.IS_DEFAULT_MUTED;
	}

	public static MusicManager get(){
		if(singleton == null){
			singleton = new MusicManager();
		}
		return singleton;
	}

	public void load(){
		bass = (Gdx.audio.newMusic(Gdx.files.internal("music/samples/bass.wav")));
		sawbass1 = (Gdx.audio.newMusic(Gdx.files.internal("music/samples/saw_bass1.wav")));
		sawbass2 = (Gdx.audio.newMusic(Gdx.files.internal("music/samples/saw_bass2.wav")));
		sawbassarp = (Gdx.audio.newMusic(Gdx.files.internal("music/samples/saw_bass_arpeggio.wav")));
		bass = (Gdx.audio.newMusic(Gdx.files.internal("music/samples/bass.wav")));
		bassgroove = (Gdx.audio.newMusic(Gdx.files.internal("music/samples/bassgroove.wav")));
		dance_arpeggio = (Gdx.audio.newMusic(Gdx.files.internal("music/samples/dance_argpeggio.wav")));
		dance_slow= (Gdx.audio.newMusic(Gdx.files.internal("music/samples/dance_slow.wav")));
		hihat= (Gdx.audio.newMusic(Gdx.files.internal("music/samples/hihat.wav")));
		jungle= (Gdx.audio.newMusic(Gdx.files.internal("music/samples/jungle.wav")));
		keys= (Gdx.audio.newMusic(Gdx.files.internal("music/samples/keys.wav")));
		kick= (Gdx.audio.newMusic(Gdx.files.internal("music/samples/kick.wav")));
		lead_end= (Gdx.audio.newMusic(Gdx.files.internal("music/samples/lead_end.wav")));
		lead_loop= (Gdx.audio.newMusic(Gdx.files.internal("music/samples/lead_loop.wav")));
		lead_opening= (Gdx.audio.newMusic(Gdx.files.internal("music/samples/lead_opening.wav")));
		pad_snow1= (Gdx.audio.newMusic(Gdx.files.internal("music/samples/pad_snow1.wav")));
		pad_snow_high= (Gdx.audio.newMusic(Gdx.files.internal("music/samples/pad_snow_high.wav")));

		bass.setOnCompletionListener(this);
		bass.play();
		sawbass1.play();
		sawbass2.play();
		sawbassarp.play();
		bass.play();
		bassgroove.play();
		dance_arpeggio .play();
		dance_slow.play();
		hihat.play();
		jungle.play();
		keys.play();
		kick.play();
		lead_end.play();
		lead_loop.play();
		lead_opening.play();
		pad_snow1.play();
		pad_snow_high.play();
		/*
		bass.pause();
		sawbass1.pause();
		sawbass2.pause();
		sawbassarp.pause();
		bass.pause();
		bassgroove.pause();
		dance_arpeggio .pause();
		dance_slow.pause();
		hihat.pause();
		jungle.pause();
		keys.pause();
		kick.pause();
		lead_end.pause();
		lead_loop.pause();
		lead_opening.pause();
		pad_snow1.pause();
		pad_snow_high.pause();*/

		bass.setLooping(true);
		sawbass1.setLooping(true);
		sawbass2.setLooping(true);
		sawbassarp.setLooping(true);
		bass.setLooping(true);
		bassgroove.setLooping(true);
		dance_arpeggio .setLooping(true);
		dance_slow.setLooping(true);
		hihat.setLooping(true);
		jungle.setLooping(true);
		keys.setLooping(true);
		kick.setLooping(true);
		lead_end.setLooping(true);
		lead_loop.setLooping(true);
		lead_opening.setLooping(true);
		pad_snow1.setLooping(true);
		pad_snow_high.setLooping(true);

		sawbass1.setPosition(0);
		sawbass2.setPosition(0);
		sawbassarp.setPosition(0);
		bass.setPosition(0);
		bassgroove.setPosition(0);
		dance_arpeggio .setPosition(0);
		dance_slow.setPosition(0);
		hihat.setPosition(0);
		jungle.setPosition(0);
		keys.setPosition(0);
		kick.setPosition(0);
		lead_end.setPosition(0);
		lead_loop.setPosition(0);
		lead_opening.setPosition(0);
		pad_snow1.setPosition(0);
		pad_snow_high.setPosition(0);
		onCompletion(bass);

		//song.setVolume(Consts.SOUND_MUSIC_VOLUME);
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}

	long nextCycle = -1;
	float lastPos = 1;

	@Override
	public void update(float delta) {
		//if (nextCycle == -1) nextCycle = System.currentTimeMillis();
		if (bass.getPosition() < lastPos){
			//nextCycle += cycle;
			onCompletion(bass);
		}
		lastPos = bass.getPosition();

		if (this.playing && bass.getVolume() < Consts.SOUND_MUSIC_VOLUME) onCompletion(bass);
	}

	public void playNextSong(){
	}

	public void dispose(){
	}

	@Override
	public void onCompletion(Music music) {
/*
		sawbass1.pause();
		sawbass2.pause();
		sawbassarp.pause();
		bass.pause();
		bassgroove.pause();
		dance_arpeggio .pause();
		dance_slow.pause();
		hihat.pause();
		jungle.pause();
		keys.pause();
		kick.pause();
		lead_end.pause();
		lead_loop.pause();
		lead_opening.pause();
		pad_snow1.pause();
		pad_snow_high.pause();*/
		System.out.println("playing");
		bass.setVolume(0);
		sawbass1.setVolume(0);
		sawbass2.setVolume(0);
		sawbassarp.setVolume(0);
		bass.setVolume(0);
		bassgroove.setVolume(0);
		dance_arpeggio .setVolume(0);
		dance_slow.setVolume(0);
		hihat.setVolume(0);
		jungle.setVolume(0);
		keys.setVolume(0);
		kick.setVolume(0);
		lead_end.setVolume(0);
		lead_loop.setVolume(0);
		lead_opening.setVolume(0);
		pad_snow1.setVolume(0);
		pad_snow_high.setVolume(0);
		if(!playing){
			return;
		}

		float  vol = Consts.SOUND_MUSIC_VOLUME;
		bass.setVolume(vol);


		int rnd = MathUtils.random(0,10);
		if (rnd < 7){
			sawbass1.setVolume(vol);
		}else if (rnd < 9){
			sawbass2.setVolume(vol);
		}else if (rnd < 11){
			sawbassarp.setVolume(vol);
		}

		rnd = MathUtils.random(0,10);

		if (rnd < 7){
			dance_slow.setVolume(vol);
		}else if (rnd < 11){
			dance_arpeggio.setVolume(vol);
		}


		rnd = MathUtils.random(0,10);

		if (rnd < 9){
			bassgroove.setVolume(vol);
		}else if (rnd < 11){
		}
		rnd = MathUtils.random(0,10);

		if (rnd < 9){
			keys.setVolume(vol);
		}else if (rnd < 11){
		}

		hihat.setVolume(0.1f);

		rnd = MathUtils.random(0,10);
		if (rnd < 7){
			kick.setVolume(vol);
		}else if (rnd < 11){
		}

		rnd = MathUtils.random(0,10);
		if (rnd < 4){
			jungle.setVolume(vol);
		}else if (rnd < 11){
		}

		rnd = MathUtils.random(0,10);
		if (rnd < 7){
			pad_snow1.setVolume(vol);
		}else if (rnd < 11){
			pad_snow_high.setVolume(vol);
		}


	}
}
