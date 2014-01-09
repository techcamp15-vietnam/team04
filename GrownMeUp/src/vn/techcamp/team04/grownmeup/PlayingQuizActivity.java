package vn.techcamp.team04.grownmeup;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

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

	// private Dialog dialog;

	private int correctAnswer;
	private int answer;

	private Drawable questionImage;
	private String correctAnswerText;

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
	private ImageView ivAnswer1;
	private ImageView ivAnswer2;
	private ImageView ivAnswer3;
	private ImageView ivAnswer4;

	private CountDownTimer countDownTimer;

	// achievement
	private AchievementRules achievement;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz_image_text_screen);
		// setContentView(R.layout.quiz_text_image_screen);

		holdDialog = false;
		holdFinishStage = false;

		stageID = getIntent().getExtras().getInt("stageID", 0);
		subjectID = getIntent().getExtras().getInt("subjectID", 0);

		currentItem = 0;
		db = new Database(this);

		initViewQuiz1();
		initTimeCounter();
		initQuiz();

		achievement = new AchievementRules(this);
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
		ivAnswer1 = (ImageView) findViewById(R.id.iv_answer1);
		ivAnswer2 = (ImageView) findViewById(R.id.iv_answer2);
		ivAnswer3 = (ImageView) findViewById(R.id.iv_answer3);
		ivAnswer4 = (ImageView) findViewById(R.id.iv_answer4);

		tvQuestionText.setOnClickListener(this);
		ivAnswer1.setOnClickListener(this);
		ivAnswer2.setOnClickListener(this);
		ivAnswer3.setOnClickListener(this);
		ivAnswer4.setOnClickListener(this);

	}

	public void initTimeCounter() {
		if (countDownTimer == null) {
			countDownTimer = new CountDownTimer(31000, 1000) {

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
					if (!holdFinishStage) {
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
		Log.i("ans", arAns.toString());

		correctAnswer = Integer.parseInt(arAns.get(0));

		ArrayList<String> al1 = new ArrayList<String>();
		al1.add(arAns.get(1));
		ArrayList<HashMap<String, String>> allAns1 = db.query(
				Database.ACTION_GET_ITEM, al1);
		ans1 = allAns1.get(0).get(mSQLiteHelper.ITEM_NAME);

		ArrayList<String> al2 = new ArrayList<String>();
		al2.add(arAns.get(2));
		ArrayList<HashMap<String, String>> allAns2 = db.query(
				Database.ACTION_GET_ITEM, al2);
		ans2 = allAns2.get(0).get(mSQLiteHelper.ITEM_NAME);

		ArrayList<String> al3 = new ArrayList<String>();
		al3.add(arAns.get(3));
		ArrayList<HashMap<String, String>> allAns3 = db.query(
				Database.ACTION_GET_ITEM, al3);
		ans3 = allAns3.get(0).get(mSQLiteHelper.ITEM_NAME);

		ArrayList<String> al4 = new ArrayList<String>();
		al4.add(arAns.get(4));
		ArrayList<HashMap<String, String>> allAns4 = db.query(
				Database.ACTION_GET_ITEM, al4);
		ans4 = allAns4.get(0).get(mSQLiteHelper.ITEM_NAME);

		InputStream is = null;
		try {
			is = getAssets().open(
					allItem.get(currentItem).get(mSQLiteHelper.ITEM_IMG_LINK));
		} catch (IOException e) {
			e.printStackTrace();
		}
		questionImage = Drawable.createFromStream(is, null);
		imgvQuestionImage.setImageDrawable(questionImage);

		btnAnswer1.setText(ans1);
		btnAnswer2.setText(ans2);
		btnAnswer3.setText(ans3);
		btnAnswer4.setText(ans4);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.playing_quiz, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
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
	}

	public void calculateResult() {
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
		if (answer == correctAnswer) {
			chosenCorrectAnswer();
		} else {

			chosenWrongAnswer();
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
		showDialog(correctAnswerText, this.questionImage);

		ArrayList<String> alWrongAns = new ArrayList<String>();
		alWrongAns.add(currentItemID);
		alWrongAns.add("false");
		db.query(Database.ACTION_ADD_ITEM_ANSWER, alWrongAns);
	}

	public void showDialog(String content, int resourceID) {
		if (!holdDialog && PlayingQuizActivity.this != null) {
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

	@Override
	public void onDestroy() {
		super.onDestroy();
		db.close();

	}

	@Override
	public void onPause() {
		super.onPause();
		holdDialog = true;
		db.close();
	}

	@Override
	public void onResume() {
		super.onResume();
		holdDialog = false;
		db.open();
	}

}
