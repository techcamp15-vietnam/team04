package vn.techcamp.team04.grownmeup.utility;

import java.io.File;
import java.io.IOException;

import android.media.MediaPlayer;
import android.os.Environment;

public class mPlayAudio {

	private MediaPlayer mPlayer = null;
	private boolean isPlaying = false;
	
	public mPlayAudio() {

	}

	public void play(String AudioName) throws IOException {
		if (isPlaying) {
			throw new IOException("still playing");
		}
		isPlaying = true;
		String audioFilePath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + File.separator + AudioName + ".3gp";
		mPlayer = new MediaPlayer();
		mPlayer.setDataSource(audioFilePath);
		mPlayer.prepare();
		mPlayer.start();
	}

	public void stop() {
		if (isPlaying) {
			isPlaying = false;
			mPlayer.release();
			mPlayer = null;
		}
	}
}
