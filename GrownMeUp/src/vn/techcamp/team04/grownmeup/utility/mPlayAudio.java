package vn.techcamp.team04.grownmeup.utility;

import java.io.IOException;

import android.media.MediaPlayer;

/**
 * @author zendbui
 * @author 4-B Bui Trong Hieu
 */
public class mPlayAudio {

	private MediaPlayer mPlayer = null;
	private boolean isPlaying = false;
	
	public mPlayAudio() {

	}

	/**
	 * Start playing a audio file
	 * @param audioFilePath
	 * @throws IOException
	 */
	public void play(String audioFilePath) throws IOException {
		if (isPlaying) {
			throw new IOException("still playing");
		}
		isPlaying = true;
		mPlayer = new MediaPlayer();
		mPlayer.setDataSource(audioFilePath);
		mPlayer.prepare();
		mPlayer.start();
	}

	/**
	 *　Stop playing audio file 
	 */
	public void stop() {
		if (isPlaying) {
			isPlaying = false;
			mPlayer.release();
			mPlayer = null;
		}
	}
}
