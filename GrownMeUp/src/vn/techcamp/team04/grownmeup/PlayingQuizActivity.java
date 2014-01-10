package vn.techcamp.team04.grownmeup;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import vn.techcamp.team04.grownmeup.database.Database;
import vn.techcamp.team04.grownmeup.database.mSQLiteHelper;
import vn.techcamp.team04.grownmeup.utility.AchievementRules;
import vn.techcamp.team04.grownmeup.utility.mRandomItem;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Nguyen Sinh Hiep 4-C
 * 
 */
public class PlayingQuizActivity extends Activity implements OnClickListener {

	private int stageID;
	private int subjectID;
	private Database db;
	private ArrayList<HashMap<String, String>> allItem;

	private int MAX_TIME = 5000;

	// private Dialog dialog;

	private int correctAnswer;
	private int answer;

	private Drawable questionImage;
	private String correctAnswerText;

	private String questionText;
	private Drawable correctAnswerImage;

	private int currentItem;
	private String currentItemID;
	private int countCorrectAnswer = 0;

	private String ans1;
	private String ans2;
	private String ans3;
	private String ans4;

	private TextView tvTimeCounter;

	private boolean holdDialog;

	private boolean holdFinishStage;

	// quiz image_text
	private ImageView imgvQuestionImage;
	private Button btnAnswer1;
	private Button btnAnswer2;
	private Button btnAnswer3;
	private Button btnAnswer4;

	// quiz text image
	private TextView tvQuestionText;
	private ImageView imgvAnswer1;
	private ImageView imgvAnswer2;
	private ImageView imgvAnswer3;
	private ImageView imgvAnswer4;

	private CountDownTimer countDownTimer;

	private int quizType = 1;

	private boolean isMute;
	public MediaPlayer sound;

	// achievement
	private AchievementRules achievement;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Random r = new Random();
		quizType = r.nextInt(2) + 1;
		if (quizType == 1) {
			setContentView(R.layout.quiz_image_text_screen);
			initViewQuiz1();
		} else {
			setContentView(R.layout.quiz_text_image_screen);
			initViewQuiz2();

		}

		holdDialog = false;
		holdFinishStage = false;

		stageID = getIntent().getExtras().getInt("stageID", 0);
		subjectID = getIntent().getExtras().getInt("subjectID", 0);

		currentItem = 0;
		db = new Database(this);

		initTimeCounter();
		initQuiz();

		achievement = new AchievementRules(this);

		SharedPreferences settings = getSharedPreferences(
				MainMenuActivity.PREFS_NAME, 0);
		isMute = settings.getBoolean("isMute", false);

	}

	public void initViewQuiz1() {
		tvTimeCounter = (TextView) findViewById(R.id.tv_time_counter);

		// quiz image_text
		imgvQuestionImage = (ImageView) findViewById(R.id.iv_question_image);
		btnAnswer1 = (Button) findViewById(R.id.btn_answer1);
		btnAnswer2 = (Button) findViewById(R.id.btn_answer2);
		btnAnswer3 = (Button) findViewById(R.id.btn_answer3);
		btnAnswer4 = (Button) findViewById(R.id.btn_answer4);

		imgvQuestionImage.setOnClickListener(this);
		btnAnswer1.setOnClickListener(this);
		btnAnswer2.setOnClickListener(this);
		btnAnswer3.setOnClickListener(this);
		btnAnswer4.setOnClickListener(this);

	}

	public void initViewQuiz2() {
		tvTimeCounter = (TextView) findViewById(R.id.tv_time_counter);
		// quiz text image
		tvQuestionText = (TextView) findViewById(R.id.tv_question_text);
		imgvAnswer1 = (ImageView) findViewById(R.id.imgv_answer1);
		imgvAnswer2 = (ImageView) findViewById(R.id.imgv_answer2);
		imgvAnswer3 = (ImageView) findViewById(R.id.imgv_answer3);
		imgvAnswer4 = (ImageView) findViewById(R.id.imgv_answer4);

		tvQuestionText.setOnClickListener(this);
		imgvAnswer1.setOnClickListener(this);
		imgvAnswer2.setOnClickListener(this);
		imgvAnswer3.setOnClickListener(this);
		imgvAnswer4.setOnClickListener(this);

	}

	public void initTimeCounter() {
		if (countDownTimer == null) {
			countDownTimer = new CountDownTimer(MAX_TIME, 1000) {

				@Override
				public void onTick(long millisUntilFinished) {
					long second = (millisUntilFinished % 60000) / 1000;
					if ((millisUntilFinished % 60000) / 1000 < 10) {
						tvTimeCounter.setText(millisUntilFinished / 60000
								+ " : " + "0" + second);
					} else {
						tvTimeCounter.setText(millisUntilFinished / 60000
								+ " : " + second);
					}
				}

				@Override
				public void onFinish() {
					if (!holdFinishStage && !holdDialog) {
						chosenWrongAnswer();
					}

				}
			}.start();
		}
	}

	public void initQuiz() {

		ArrayList<String> alstage = new ArrayList<String>();
		alstage.add(stageID + "");
		allItem = db.query(Database.ACTION_GET_STAGE_DETAILS, alstage);
		Log.i("allItem", allItem.toString());
		loadItem();
	}

	public void loadItem() {
		// Random answer
		currentItemID = allItem.get(currentItem).get(mSQLiteHelper.ITEM_ID);
		mRandomItem randomAns = new mRandomItem(PlayingQuizActivity.this);
		ArrayList<String> arAns = randomAns.random(subjectID,
				Integer.parseInt(currentItemID));

		correctAnswer = Integer.parseInt(arAns.get(0));

		ArrayList<String> al1 = new ArrayList<String>();
		al1.add(arAns.get(1));
		ArrayList<HashMap<String, String>> allAns1 = db.query(
				Database.ACTION_GET_ITEM, al1);

		ArrayList<String> al2 = new ArrayList<String>();
		al2.add(arAns.get(2));
		ArrayList<HashMap<String, String>> allAns2 = db.query(
				Database.ACTION_GET_ITEM, al2);

		ArrayList<String> al3 = new ArrayList<String>();
		al3.add(arAns.get(3));
		ArrayList<HashMap<String, String>> allAns3 = db.query(
				Database.ACTION_GET_ITEM, al3);

		ArrayList<String> al4 = new ArrayList<String>();
		al4.add(arAns.get(4));
		ArrayList<HashMap<String, String>> allAns4 = db.query(
				Database.ACTION_GET_ITEM, al4);
		if (quizType == 1) {
			ans1 = allAns1.get(0).get(mSQLiteHelper.ITEM_NAME);
			ans2 = allAns2.get(0).get(mSQLiteHelper.ITEM_NAME);
			ans3 = allAns3.get(0).get(mSQLiteHelper.ITEM_NAME);
			ans4 = allAns4.get(0).get(mSQLiteHelper.ITEM_NAME);

			InputStream is = null;
			try {
				is = getAssets().open(
						allItem.get(currentItem).get(
								mSQLiteHelper.ITEM_IMG_LINK));
			} catch (IOException e) {
				e.printStackTrace();
			}
			questionImage = Drawable.createFromStream(is, null);
			imgvQuestionImage.setImageDrawable(questionImage);

			btnAnswer1.setText(ans1);
			btnAnswer2.setText(ans2);
			btnAnswer3.setText(ans3);
			btnAnswer4.setText(ans4);

			switch (correctAnswer) {
			case 1:
				correctAnswerText = ans1;
				break;
			case 2:
				correctAnswerText = ans2;
				break;
			case 3:
				correctAnswerText = ans3;
				break;
			case 4:
				correctAnswerText = ans4;
				break;

			default:
				break;
			}

		} else {
			questionText = allItem.get(currentItem)
					.get(mSQLiteHelper.ITEM_NAME);
			tvQuestionText.setText(questionText);

			InputStream is = null;
			try {
				is = getAssets().open(
						allAns1.get(0).get(mSQLiteHelper.ITEM_IMG_LINK));
			} catch (IOException e) {
				e.printStackTrace();
			}
			Drawable d1 = Drawable.createFromStream(is, null);
			imgvAnswer1.setImageDrawable(d1);

			try {
				is = getAssets().open(
						allAns2.get(0).get(mSQLiteHelper.ITEM_IMG_LINK));
			} catch (IOException e) {
				e.printStackTrace();
			}
			Drawable d2 = Drawable.createFromStream(is, null);
			imgvAnswer2.setImageDrawable(d2);

			try {
				is = getAssets().open(
						allAns3.get(0).get(mSQLiteHelper.ITEM_IMG_LINK));
			} catch (IOException e) {
				e.printStackTrace();
			}
			Drawable d3 = Drawable.createFromStream(is, null);
			imgvAnswer3.setImageDrawable(d3);

			try {
				is = getAssets().open(
						allAns4.get(0).get(mSQLiteHelper.ITEM_IMG_LINK));
			} catch (IOException e) {
				e.printStackTrace();
			}
			Drawable d4 = Drawable.createFromStream(is, null);
			imgvAnswer4.setImageDrawable(d4);

			switch (correctAnswer) {
			case 1:
				correctAnswerImage = d1;
				break;
			case 2:
				correctAnswerImage = d2;
				break;
			case 3:
				correctAnswerImage = d3;
				break;
			case 4:
				correctAnswerImage = d4;
				break;

			default:
				break;
			}

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.playing_quiz, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if (quizType == 1) {
			switch (v.getId()) {
			case R.id.btn_answer1:
				answer = 1;
				calculateResult();

				break;
			case R.id.btn_answer2:
				answer = 2;
				calculateResult();

				break;
			case R.id.btn_answer3:
				answer = 3;
				calculateResult();

				break;
			case R.id.btn_answer4:
				answer = 4;
				calculateResult();

				break;

			default:
				break;
			}
		} else {
			switch (v.getId()) {
			case R.id.imgv_answer1:
				answer = 1;
				calculateResult();
				break;
			case R.id.imgv_answer2:
				answer = 2;
				calculateResult();
				break;
			case R.id.imgv_answer3:
				answer = 3;
				calculateResult();
				break;
			case R.id.imgv_answer4:
				answer = 4;
				calculateResult();
				break;
			}
		}
	}

	public void calculateResult() {

		if (answer == correctAnswer) {
			chosenCorrectAnswer();
			playSound(R.raw.sound_right_en);
		} else {

			chosenWrongAnswer();
			playSound(R.raw.sound_wrong_en);
		}

	}

	public void displayNextQuestion() {
		countDownTimer.start();
		if (currentItem < 4) {
			currentItem++;
			loadItem();
		} else {
			finishStage();
		}

	}

	public void chosenCorrectAnswer() {

		countCorrectAnswer++;
		showDialog("Correct!", R.drawable.correct);

		ArrayList<String> alCorrectAns = new ArrayList<String>();
		alCorrectAns.add(currentItemID);
		alCorrectAns.add("true");
		db.query(Database.ACTION_ADD_ITEM_ANSWER, alCorrectAns);

		ArrayList<String> arr = achievement.checkNumberCorrectedAnswer(Integer
				.parseInt(currentItemID));
		if (arr.contains("true")) {
			if (arr.contains(AchievementRules.badge3)) {
				SharedPreferences settings = this.getSharedPreferences(
						AchievementRules.ACHIEVEMENT, 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean(AchievementRules.badge3, true);
				editor.commit();
			}

			if (arr.contains(AchievementRules.badge4)) {
				SharedPreferences settings = this.getSharedPreferences(
						AchievementRules.ACHIEVEMENT, 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean(AchievementRules.badge4, true);
				editor.commit();
			}
		}

	}

	public void chosenWrongAnswer() {

		if (quizType == 1) {
			showDialog(correctAnswerText, this.questionImage);
		} else {
			showDialog(questionText, correctAnswerImage);

		}
		ArrayList<String> alWrongAns = new ArrayList<String>();
		alWrongAns.add(currentItemID);
		alWrongAns.add("false");
		db.query(Database.ACTION_ADD_ITEM_ANSWER, alWrongAns);
	}

	public void showDialog(String content, int resourceID) {
		if (!holdDialog && PlayingQuizActivity.this != null) {
			holdDialog = true;
			final Dialog dialog = new Dialog(PlayingQuizActivity.this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setCancelable(false);
			dialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.setContentView(R.layout.correct_answer);

			TextView text = (TextView) dialog.findViewById(R.id.tv_answer);
			text.setText(content);
			ImageView image = (ImageView) dialog
					.findViewById(R.id.imgv_question);
			image.setImageResource(resourceID);
			dialog.show();
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					if (dialog != null) {
						dialog.dismiss();
						displayNextQuestion();
						holdDialog = false;
					}
				}
			}, 2000);
			/*
			 * new CountDownTimer(2000, 1000) {
			 * 
			 * @Override public void onTick(long millisUntilFinished) {
			 * 
			 * }
			 * 
			 * @Override public void onFinish() { if (dialog != null) {
			 * dialog.dismiss(); dialog = null; displayNextQuestion(); }
			 * 
			 * } }.start();
			 */
		}
	}

	public void showDialog(String content, Drawable questionImage) {
		if (!holdDialog && PlayingQuizActivity.this != null) {
			holdDialog = true;
			final Dialog dialog = new Dialog(PlayingQuizActivity.this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setCancelable(false);
			dialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.setContentView(R.layout.correct_answer);

			TextView text = (TextView) dialog.findViewById(R.id.tv_answer);
			text.setText(content);
			ImageView image = (ImageView) dialog
					.findViewById(R.id.imgv_question);
			image.setImageDrawable(questionImage);
			dialog.show();
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					if (dialog != null) {
						dialog.dismiss();
						displayNextQuestion();
						holdDialog = false;
					}
				}
			}, 2000);
			/*
			 * new CountDownTimer(2000, 1000) {
			 * 
			 * @Override public void onTick(long millisUntilFinished) {
			 * 
			 * }
			 * 
			 * @Override public void onFinish() { if (dialog != null) {
			 * dialog.dismiss(); dialog = null; displayNextQuestion(); }
			 * 
			 * } }.start();
			 */
		}
	}

	public void finishStage() {
		if (!holdFinishStage) {
			holdFinishStage = true;
			checkStageAchievement();
			new AlertDialog.Builder(this)
					.setTitle("Stage Result")
					.setMessage("Correct Answer: " + countCorrectAnswer + "/5")
					.setPositiveButton("Next Stage",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

									stageID++;
									countDownTimer.start();
									allItem.clear();
									allItem = null;
									countCorrectAnswer = 0;
									currentItem = 0;
									holdFinishStage = false;
									initQuiz();

								}
							})
					.setNegativeButton("RePlay",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									countDownTimer.start();
									allItem.clear();
									allItem = null;
									countCorrectAnswer = 0;
									currentItem = 0;
									holdFinishStage = false;
									initQuiz();

								}
							}).show();
		}
	}

	private synchronized void playSound(int idSound) {
		if (this.isMute == false) {
			if (sound != null) {
				sound.release();
				sound = null;
			}
			sound = MediaPlayer.create(PlayingQuizActivity.this, idSound);
			if (sound != null) {
				sound.seekTo(0);
				sound.start();
			}
		}
	}

	private void releaseSound() {
		if (sound != null) {
			sound.release();
			sound = null;
			// Log.v("Con lai", String.valueOf(i));
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

	private void playLoopSound(int idSound) {

		if (sound != null) {
			sound.release();
			sound = null;
		}
		sound = MediaPlayer.create(PlayingQuizActivity.this, idSound);
		if (sound != null) {
			sound.seekTo(0);
			sound.setLooping(true);
			sound.start();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		countDownTimer.cancel();
		db.close();
		releaseSound();

	}

	@Override
	public void onPause() {
		super.onPause();
		holdDialog = true;
		// db.close();
		pauseSound();
	}

	@Override
	public void onResume() {
		super.onResume();
		holdDialog = false;
		// db.open();
		startSound();
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	private void checkStageAchievement() {
		if (countCorrectAnswer == 5) {
			Log.d("complete stage", "complete !!!!!!!!");
			ArrayList<String> result = achievement.checkNumberCompleteStage();
			if (result.contains("true")) {
				if (result.contains(AchievementRules.badge1)) {
					SharedPreferences settings = PlayingQuizActivity.this
							.getSharedPreferences(AchievementRules.ACHIEVEMENT,
									0);
					SharedPreferences.Editor editor = settings.edit();
					editor.putBoolean(AchievementRules.badge1, true);
					editor.commit();
				}

				if (result.contains(AchievementRules.badge2)) {
					SharedPreferences settings = PlayingQuizActivity.this
							.getSharedPreferences(AchievementRules.ACHIEVEMENT,
									0);
					SharedPreferences.Editor editor = settings.edit();
					editor.putBoolean(AchievementRules.badge2, true);
					editor.commit();
				}
				if (result.contains(AchievementRules.badge6)) {
					SharedPreferences settings = PlayingQuizActivity.this
							.getSharedPreferences(AchievementRules.ACHIEVEMENT,
									0);
					SharedPreferences.Editor editor = settings.edit();
					editor.putBoolean(AchievementRules.badge6, true);
					editor.commit();
				}
			}
		}
	}
}
