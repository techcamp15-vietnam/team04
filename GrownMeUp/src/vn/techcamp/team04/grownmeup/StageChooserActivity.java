package vn.techcamp.team04.grownmeup;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import vn.techcamp.team04.grownmeup.database.Database;
import vn.techcamp.team04.grownmeup.database.mSQLiteHelper;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * @author Nguyen Sinh Hiep 4-C
 * 
 */
public class StageChooserActivity extends Activity implements OnClickListener {
	private ImageButton btnNext;
	private ImageButton btnPrev;

	private ImageView imgvStagePos1;
	private ImageView imgvStagePos2;
	private ImageView imgvStagePos3;
	private ImageView imgvStagePos4;
	private ImageView imgvStagePos5;
	private ImageView imgvStagePos6;

	private Database db;
	private ArrayList<HashMap<String, String>> allStage;

	private int subjectID;
	private int currentStage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stage_chooser_screen);

		subjectID = getIntent().getExtras().getInt("subjectID", 0);

		initView();
		initStage();
	}

	public void initView() {
		btnNext = (ImageButton) findViewById(R.id.btn_next);
		btnPrev = (ImageButton) findViewById(R.id.btn_prev);

		imgvStagePos1 = (ImageView) findViewById(R.id.imgv_stage_pos_1);
		imgvStagePos2 = (ImageView) findViewById(R.id.imgv_stage_pos_2);
		imgvStagePos3 = (ImageView) findViewById(R.id.imgv_stage_pos_3);
		imgvStagePos4 = (ImageView) findViewById(R.id.imgv_stage_pos_4);
		imgvStagePos5 = (ImageView) findViewById(R.id.imgv_stage_pos_5);
		imgvStagePos6 = (ImageView) findViewById(R.id.imgv_stage_pos_6);

		btnNext.setOnClickListener(this);
		btnPrev.setOnClickListener(this);
		imgvStagePos1.setOnClickListener(this);
		imgvStagePos2.setOnClickListener(this);
		imgvStagePos3.setOnClickListener(this);
		imgvStagePos4.setOnClickListener(this);
		imgvStagePos5.setOnClickListener(this);
		imgvStagePos6.setOnClickListener(this);

	}

	public void initStage() {
		db = new Database(this);
		ArrayList<String> alSubjectID = new ArrayList<String>();
		alSubjectID.add(subjectID + "");
		allStage = db.query(Database.ACTION_GET_ALL_STAGE, alSubjectID);
		currentStage = 0;
		loadStage();

	}

	public void loadStage() {
		InputStream is = null;
		if (currentStage < allStage.size()) {

			String pos1 = allStage.get(currentStage).get(
					mSQLiteHelper.STAGE_NAME);
			if (pos1 != null) {
				try {
					is = getAssets().open("image/stage/" + pos1 + ".png");
				} catch (IOException e) {
					e.printStackTrace();
				}
				Drawable d = Drawable.createFromStream(is, null);
				imgvStagePos1.setImageDrawable(d);
			}
		}
		if (currentStage + 1 < allStage.size()) {
			String pos2 = allStage.get(currentStage + 1).get(
					mSQLiteHelper.STAGE_NAME);
			if (pos2 != null) {
				try {
					is = getAssets().open("image/stage/" + pos2 + ".png");
				} catch (IOException e) {
					e.printStackTrace();
				}
				Drawable d = Drawable.createFromStream(is, null);
				imgvStagePos2.setImageDrawable(d);
			}
		}
		if (currentStage + 2 < allStage.size()) {
			String pos3 = allStage.get(currentStage + 2).get(
					mSQLiteHelper.STAGE_NAME);
			if (pos3 != null) {
				try {
					is = getAssets().open("image/stage/" + pos3 + ".png");
				} catch (IOException e) {
					e.printStackTrace();
				}
				Drawable d = Drawable.createFromStream(is, null);
				imgvStagePos3.setImageDrawable(d);
			}
		}
		if (currentStage + 3 < allStage.size()) {
			String pos4 = allStage.get(currentStage + 3).get(
					mSQLiteHelper.STAGE_NAME);
			if (pos4 != null) {
				try {
					is = getAssets().open("image/stage/" + pos4 + ".png");
				} catch (IOException e) {
					e.printStackTrace();
				}
				Drawable d = Drawable.createFromStream(is, null);
				imgvStagePos4.setImageDrawable(d);
			}
		}
		if (currentStage + 4 < allStage.size()) {
			String pos5 = allStage.get(currentStage + 4).get(
					mSQLiteHelper.STAGE_NAME);
			if (pos5 != null) {
				try {
					is = getAssets().open("image/stage/" + pos5 + ".png");
				} catch (IOException e) {
					e.printStackTrace();
				}
				Drawable d = Drawable.createFromStream(is, null);
				imgvStagePos5.setImageDrawable(d);
			}
		}
		if (currentStage + 5 < allStage.size()) {
			String pos6 = allStage.get(currentStage + 5).get(
					mSQLiteHelper.STAGE_NAME);
			if (pos6 != null) {
				try {
					is = getAssets().open("image/stage/" + pos6 + ".png");
				} catch (IOException e) {
					e.printStackTrace();
				}
				Drawable d = Drawable.createFromStream(is, null);
				imgvStagePos6.setImageDrawable(d);
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.subject_chooser, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_next:
			if (currentStage + 4 < allStage.size() - 1) {
				this.currentStage += 6;
				loadStage();
			}

			break;
		case R.id.btn_prev:
			if (currentStage >= 6) {
				this.currentStage -= 6;
				loadStage();
			}

			break;
		case R.id.imgv_stage_pos_1:
			startPlayQuiz(currentStage);

			break;
		case R.id.imgv_stage_pos_2:
			startPlayQuiz(currentStage + 1);

			break;
		case R.id.imgv_stage_pos_3:
			startPlayQuiz(currentStage + 2);
			break;
		case R.id.imgv_stage_pos_4:
			startPlayQuiz(currentStage + 3);
			break;
		case R.id.imgv_stage_pos_5:
			startPlayQuiz(currentStage + 4);
			break;
		case R.id.imgv_stage_pos_6:
			startPlayQuiz(currentStage + 5);
			break;

		default:
			break;
		}
	}

	public void startPlayQuiz(int stageID) {
		Intent intent = new Intent(StageChooserActivity.this,
				PlayingQuizActivity.class);
		intent.putExtra("stageID", stageID + 1);
		intent.putExtra("subjectID", subjectID);
		startActivity(intent);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		db.close();

	}

}
