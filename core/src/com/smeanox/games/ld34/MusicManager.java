package com.smeanox.games.ld34;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.MathUtils;
import com.smeanox.games.ld34.world.Updatable;

import java.util.ArrayList;

/**
 * Comment
 */
public class MusicManager implements Updatable {
	private static MusicManager singleton;

	private ArrayList<Music> songs;
	private Music activeSong;

	private boolean playing;

	private MusicManager(){
		songs = new ArrayList<Music>();
		activeSong = null;
	}

	public static MusicManager get(){
		if(singleton == null){
			singleton = new MusicManager();
		}
		return singleton;
	}

	public void load(){
		songs.add(Gdx.audio.newMusic(Gdx.files.internal("music/blub3.0.mp3")));

		for(Music song : songs){
			song.setVolume(Consts.SOUND_MUSIC_VOLUME);
		}
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
		if (activeSong != null) {
			if (playing) {
				activeSong.play();
			} else {
				activeSong.pause();
			}
		}
	}

	@Override
	public void update(float delta) {
		if(!playing){
			return;
		}

		if(activeSong == null || !activeSong.isPlaying()){
			playNextSong();
		}
	}

	public void playNextSong(){
		if(activeSong != null){
			activeSong.stop();
		}
		activeSong = songs.get(MathUtils.random(songs.size() - 1));
		activeSong.play();
	}

	public void dispose(){
		for(Music song : songs){
			song.dispose();
		}
	}
}
