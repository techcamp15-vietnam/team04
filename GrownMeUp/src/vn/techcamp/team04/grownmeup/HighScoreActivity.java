package vn.techcamp.team04.grownmeup;

import vn.techcamp.team04.grownmeup.utility.AchievementRules;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author zendbui
 * @author 4-B Bui Trong Hieu
 */
public class HighScoreActivity extends Activity implements OnClickListener {

	private ImageButton mBtnReport;
	private ImageView mBadge1;
	private ImageView mBadge2;
	private ImageView mBadge3;
	private ImageView mBadge4;
	private ImageView mBadge5;
	private ImageView mBadge6;
	private TextView mTvExplainBadge;

	private boolean mValuebadge1;
	private boolean mValuebadge2;
	private boolean mValuebadge3;
	private boolean mValuebadge4;
	private float mValuebadge5;
	private boolean mValuebadge6;

	private boolean isPlaying = false;
	public MediaPlayer sound;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.high_score_screen);
		initView();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.high_score, menu);
		return true;
	}

	/**
	 * init and set listener for button.
	 * 
	 * @param no
	 * @return no
	 */
	private void initView() {
		mBtnReport = (ImageButton) findViewById(R.id.high_score_btn_report);
		mBadge1 = (ImageView) findViewById(R.id.high_score_img_achievement_1);
		mBadge2 = (ImageView) findViewById(R.id.high_score_img_achievement_2);
		mBadge3 = (ImageView) findViewById(R.id.high_score_img_achievement_3);
		mBadge4 = (ImageView) findViewById(R.id.high_score_img_achievement_4);
		mBadge5 = (ImageView) findViewById(R.id.high_score_img_achievement_5);
		mBadge6 = (ImageView) findViewById(R.id.high_score_img_achievement_6);
		mTvExplainBadge = (TextView) findViewById(R.id.high_socre_tv_explain_badge);
		mTvExplainBadge.setText("");

		mBtnReport.setOnClickListener(this);
		mBadge1.setOnClickListener(this);
		mBadge2.setOnClickListener(this);
		mBadge3.setOnClickListener(this);
		mBadge4.setOnClickListener(this);
		mBadge5.setOnClickListener(this);
		mBadge6.setOnClickListener(this);

		SharedPreferences settings = getSharedPreferences(
				AchievementRules.ACHIEVEMENT, 0);
		mValuebadge1 = settings.getBoolean(AchievementRules.badge1, false);
		if (!mValuebadge1) {
			mBadge1.setImageResource(R.drawable.black_badge);
		}
		mValuebadge2 = settings.getBoolean(AchievementRules.badge2, false);
		if (!mValuebadge2) {
			mBadge2.setImageResource(R.drawable.black_badge);
		}
		mValuebadge3 = settings.getBoolean(AchievementRules.badge3, false);
		if (!mValuebadge3) {
			mBadge3.setImageResource(R.drawable.black_badge);
		}
		mValuebadge4 = settings.getBoolean(AchievementRules.badge4, false);
		if (!mValuebadge4) {
			mBadge4.setImageResource(R.drawable.black_badge);
		}
		mValuebadge5 = settings.getFloat(AchievementRules.badge5, (float) 0.0);
		if (mValuebadge5 == 0.0) {
			mBadge5.setImageResource(R.drawable.black_badge);
		}
		mValuebadge6 = settings.getBoolean(AchievementRules.badge6, false);
		if (!mValuebadge6) {
			mBadge6.setImageResource(R.drawable.black_badge);
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.high_score_btn_report:
			Intent statisticActivity = new Intent(this,
					StatisticPlayingResult.class);
			startActivity(statisticActivity);
			break;
		case R.id.high_score_img_achievement_1:
			mTvExplainBadge.setText(AchievementRules.badge1.toLowerCase());
			if (mValuebadge1) {
				if (isPlaying) {
					isPlaying = false;
					releaseSound();
				}
				isPlaying = true;
				playSound(R.raw.sound_right_1);
			} else {
				if (isPlaying) {
					isPlaying = false;
					releaseSound();
				}
				isPlaying = true;
				playSound(R.raw.sound_wrong_1);

			}
			break;
		case R.id.high_score_img_achievement_2:
			mTvExplainBadge.setText(AchievementRules.badge2.toLowerCase());
			if (mValuebadge2) {
				if (isPlaying) {
					isPlaying = false;
					releaseSound();
				}
				isPlaying = true;
				playSound(R.raw.sound_right_1);

			} else {
				if (isPlaying) {
					isPlaying = false;
					releaseSound();
				}
				isPlaying = true;
				playSound(R.raw.sound_wrong_1);

			}
			break;
		case R.id.high_score_img_achievement_3:
			mTvExplainBadge.setText(AchievementRules.badge3.toLowerCase());
			if (mValuebadge3) {
				if (isPlaying) {
					isPlaying = false;
					releaseSound();
				}
				isPlaying = true;
				playSound(R.raw.sound_right_1);

			} else {
				if (isPlaying) {
					isPlaying = false;
					releaseSound();
				}
				isPlaying = true;
				playSound(R.raw.sound_wrong_1);

			}
			break;
		case R.id.high_score_img_achievement_4:
			mTvExplainBadge.setText(AchievementRules.badge4.toLowerCase());
			if (mValuebadge4) {
				if (isPlaying) {
					isPlaying = false;
					releaseSound();
				}
				isPlaying = true;
				playSound(R.raw.sound_right_1);

			} else {
				if (isPlaying) {
					isPlaying = false;
					releaseSound();
				}
				isPlaying = true;
				playSound(R.raw.sound_wrong_1);

			}
			break;
		case R.id.high_score_img_achievement_5:
			mTvExplainBadge.setText(AchievementRules.badge5.toLowerCase()
					+ " : " + (int)mValuebadge5 + "s");
			if (mValuebadge5 != 0.0) {
				if (isPlaying) {
					isPlaying = false;
					releaseSound();
				}
				isPlaying = true;
				playSound(R.raw.sound_right_1);

			} else {
				if (isPlaying) {
					isPlaying = false;
					releaseSound();
				}
				isPlaying = true;
				playSound(R.raw.sound_wrong_1);

			}
			break;
		case R.id.high_score_img_achievement_6:
			mTvExplainBadge.setText(AchievementRules.badge6.toLowerCase());
			if (mValuebadge6) {
				if (isPlaying) {
					isPlaying = false;
					releaseSound();
				}
				isPlaying = true;
				playSound(R.raw.sound_right_1);

			} else {
				if (isPlaying) {
					isPlaying = false;
					releaseSound();
				}
				isPlaying = true;
				playSound(R.raw.sound_wrong_1);

			}
			break;
		default:
			break;
		}
	}

	private synchronized void playSound(int idSound) {
		if (sound != null) {
			sound.release();
			sound = null;
		}
		sound = MediaPlayer.create(HighScoreActivity.this, idSound);
		if (sound != null) {
			sound.seekTo(0);
			sound.start();
		}
		Runtime.getRuntime().gc();
	}

	private void releaseSound() {
		if (sound != null) {
			sound.release();
			sound = null;
		}
	}

	private void pauseSound() {
		if (sound != null) {
			sound.pause();
		}
	}

	private void startSound() {
		if (sound != null) {
			sound.start();
		}
	}

}
