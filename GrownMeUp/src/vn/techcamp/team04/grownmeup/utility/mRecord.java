package vn.techcamp.team04.grownmeup.utility;

import java.io.IOException;

import android.media.MediaRecorder;

/**
 * @author zendbui
 * @author 4-A Bui Trong Hieu
 */
public class mRecord {
	private MediaRecorder mRecorder = null;
	private boolean isRecording = false;

	public mRecord() {

	}

	/**
	 * Start recording audio file
	 * @param audioFilePath
	 * @throws IOException
	 */
	public void recordAudio(String audioFilePath) throws IOException {

		if (isRecording) {
			throw new IOException("still recording");
		}
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
	
	/**
	 * Stop recording and save audio file to SDCard
	 */
	public void stop() {
		if (isRecording) {
			mRecorder.stop();
			mRecorder.release();
			mRecorder = null;
			isRecording = false;
		}
	}

}
