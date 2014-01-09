package vn.techcamp.team04.grownmeup.utility;

import java.util.Locale;

import android.content.Context;
import android.speech.tts.TextToSpeech;

/**
 * @author zendbui
 * @author 4-B Bui Trong Hieu
 */
public class mTTS {

	private TextToSpeech tts;

	public mTTS(Context context, final float speedrate) {

		this.tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
			@Override
			public void onInit(int status) {
				if (status != TextToSpeech.ERROR) {
					tts.setLanguage(Locale.UK);
					tts.setSpeechRate(speedrate);
				}
			}
		});
	}

	/**
	 * Speak out a text
	 * 
	 * @param text
	 */
	public void speakText(String text) {
		tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);

	}

	/**
	 * Stop speak
	 * 
	 * @author 4-A bui trung hieu
	 * @param
	 */
	public void stop() {
		tts.stop();
	}

	/**
	 * Close textToSpeech function, call when destroy activity
	 */
	public void close() {
		if (this.tts != null) {
			this.tts.stop();
			this.tts.shutdown();
		}
	}

}
