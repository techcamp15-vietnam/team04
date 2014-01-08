package vn.techcamp.team04.grownmeup.utility;

import java.util.Locale;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

/**
 * @author zendbui
 * @author 4-B Bui Trong Hieu
 */
public class mTTS implements TextToSpeech.OnInitListener{
private TextToSpeech tts;
	
	public mTTS(Context context, String text){
		this.tts = new TextToSpeech(context, this);
		this.tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
		this.tts.stop();
		this.tts.shutdown();
	}
	
	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
            int result = this.tts.setLanguage(Locale.US);
            this.tts.setSpeechRate((float) 0.7);
            
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
            	Log.i("TTS", "init complete");
            }
 
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
		
	}
	
	/**
	 * Speak a text by Google-TextToSpeak
	 * @param text
	 */
	public void speeak(String text){
		this.tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}
	
	/**
	 * close TextToSpeak
	 */
	public void close(){
		if (this.tts != null) {
			this.tts.stop();
			this.tts.shutdown();
		}
	}
}
