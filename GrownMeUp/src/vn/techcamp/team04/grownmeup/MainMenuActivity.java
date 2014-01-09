package vn.techcamp.team04.grownmeup;

import java.io.File;

import vn.techcamp.team04.grownmeup.database.Database;
import vn.techcamp.team04.grownmeup.utility.AchievementRules;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

/**
 * @author Nguyen Sinh Hiep 4-C
 * 
 */
public class MainMenuActivity extends Activity implements OnClickListener {

	public static final String PREFS_NAME = "Setting";

	private ImageButton btnLearn;
	private ImageButton btnPlay;
	private ImageButton btnHighScore;
	private ImageButton btnOption;
	private ImageButton btnExit;
	private Database db;

	public MediaPlayer sound;
	private boolean isMute;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu_screen);
		initView();

		db = new Database(this);
		db.setDefaultData();

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		boolean silent = settings.getBoolean("silentMode", false);
		// playLoopSound(R.raw.sound_background_menu);

		createDefaultValue();
		creatCustomItemDirecrory();
	}

	public void initView() {
		btnLearn = (ImageButton) findViewById(R.id.btn_learn);
		btnPlay = (ImageButton) findViewById(R.id.btn_play);
		btnHighScore = (ImageButton) findViewById(R.id.btn_highscore);
		btnOption = (ImageButton) findViewById(R.id.btn_option);
		btnExit = (ImageButton) findViewById(R.id.btn_exit);

		btnLearn.setOnClickListener(this);
		btnPlay.setOnClickListener(this);
		btnHighScore.setOnClickListener(this);
		btnOption.setOnClickListener(this);
		btnExit.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_learn:
			Intent learningActivity = new Intent(this, LearningActivity.class);
			startActivity(learningActivity);
			break;
		case R.id.btn_play:
			Intent subjectChooserActivity = new Intent(this,
					SubjectChooserActivity.class);
			startActivity(subjectChooserActivity);
			break;
		case R.id.btn_highscore:
			Intent highScoreActivity = new Intent(this, HighScoreActivity.class);
			startActivity(highScoreActivity);
			break;
		case R.id.btn_option:
			Intent optionIntent = new Intent(this, OptionActivity.class);
			startActivity(optionIntent);

			break;
		case R.id.btn_exit:
			break;

		default:
			break;
		}

	}

	private synchronized void playSound(int idSound) {
		if (this.isMute == false) {
			if (sound != null) {
				sound.release();
				sound = null;
				// Log.v("Huy bo", String.valueOf(i));
			}
			// Log.v("Khoi tao", String.valueOf(i));
			sound = MediaPlayer.create(MainMenuActivity.this, idSound);
			if (sound != null) {
				sound.seekTo(0);
				sound.start();
			}
			Runtime.getRuntime().gc();
		}
	}

	private void releaseSound() {
		if (this.isMute == false && sound != null) {
			sound.release();
			sound = null;
			// Log.v("Con lai", String.valueOf(i));
		}
	}

	private void pauseSound() {
		if (this.isMute == false && sound != null) {
			sound.pause();
		}
	}

	private void startSound() {
		if (this.isMute == false && sound != null) {
			sound.start();
		}
	}

	private void playLoopSound(int idSound) {
		if (this.isMute == false) {
			if (sound != null) {
				sound.release();
				sound = null;
			}
			sound = MediaPlayer.create(MainMenuActivity.this, idSound);
			if (sound != null) {
				sound.seekTo(0);
				sound.setLooping(true);
				sound.start();
			}
			Runtime.getRuntime().gc();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		releaseSound();
	}

	@Override
	public void onResume() {
		super.onResume();
		startSound();
	}

	@Override
	public void onPause() {
		super.onPause();
		pauseSound();

	}

	/**
	 * @author zendbui
	 * @author 4-B Bui Trong Hieu Create default value for apps: database and
	 *         achievement
	 */
	private void createDefaultValue() {
		db = new Database(this);
		db.setDefaultData();

		SharedPreferences settings = getSharedPreferences(
				AchievementRules.ACHIEVEMENT, 0);
		if (!settings.contains(AchievementRules.badge1)) {
			SharedPreferences.Editor editor = settings.edit();
			editor.putInt(AchievementRules.TOTAL, AchievementRules.totalStage);
			editor.putInt(AchievementRules.COMPLETED_STAGES, 0);
			editor.putBoolean(AchievementRules.badge1, false);
			editor.putBoolean(AchievementRules.badge2, false);
			editor.putBoolean(AchievementRules.badge3, false);
			editor.putBoolean(AchievementRules.badge4, false);
			editor.putFloat(AchievementRules.badge5, (float) 0.0);
			editor.putBoolean(AchievementRules.badge6, false);
			editor.commit();
		}
	}

	/**
	 * @author 4-A Bui Trung Hieu
	 * @return true if directory create, false if othewise.
	 */
	private boolean creatCustomItemDirecrory() {
		File customItemsSoundDirectory = new File(Environment
				.getExternalStorageDirectory().getPath()
				+ "GrowMeUp/items/images");
		File customItemsImageDirectory = new File(Environment
				.getExternalStorageDirectory().getPath()
				+ "/GrowMeUp/items/sounds");
		if (!customItemsImageDirectory.isDirectory()) {
			customItemsImageDirectory.mkdirs();
			customItemsSoundDirectory.mkdirs();
		}
		return false;
	}
}
