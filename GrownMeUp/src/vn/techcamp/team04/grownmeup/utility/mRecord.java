package vn.techcamp.team04.grownmeup.utility;

import java.io.File;
import java.io.IOException;

import android.media.MediaRecorder;
import android.os.Environment;

/**
 * @author zendbui
 * @author 4-A Bui Trong Hieu
 */
public class mRecord {
	private static final String LOG_TAG = "AudioRecordTest";
	private MediaRecorder mRecorder = null;
	private boolean isRecording = false;

	public mRecord() {

	}

	/**
	 * @param AudioName
	 * @throws IOException
	 */
	public void recordAudio(String AudioName) throws IOException {

		if (isRecording) {
			throw new IOException("still recording");
		}
		// TODO: file assect file
		String audioFilePath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + File.separator + AudioName + ".3gp";

		isRecording = true;

		try {
			mRecorder = new MediaRecorder();
			mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			mRecorder.setOutputFile(audioFilePath);
			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			mRecorder.prepare();
		} catch (Exception e) {
			e.printStackTrace();
		}

		mRecorder.start();
	}

	public void stop() {
		if (isRecording) {
			mRecorder.stop();
			mRecorder.release();
			mRecorder = null;
			isRecording = false;
		}
	}

	/*
	 * public void playAudio () throws IOException { mPlayer = new
	 * MediaPlayer(); mPlayer.setDataSource(audioFilePath); mPlayer.prepare();
	 * mPlayer.start(); }
	 */
}
