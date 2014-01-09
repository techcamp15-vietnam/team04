package vn.techcamp.team04.grownmeup;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vn.techcamp.team04.grownmeup.database.Database;
import vn.techcamp.team04.grownmeup.database.mSQLiteHelper;
import vn.techcamp.team04.grownmeup.utility.mTTS;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class StatisticPlayingResult extends Activity implements
		OnClickListener, OnItemSelectedListener {

	private ImageView imgvItemImage;
	private TextView tvMeaning;
	private ImageButton btnNext;
	private ImageButton btnPrev;
	private TextView tvResultCorrect;
	private TextView tvResultWrong;
	private Spinner spSubject;

	private mTTS tts;

	private Database db;
	private ArrayList<HashMap<String, String>> allItem;
	private int currentSubject;
	private int currentItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statistic_playing_result_screen);

		initView();

		tts = new mTTS(this, 0.7f);

		db = new Database(this);

		ArrayList<HashMap<String, String>> allSubject = db.query(
				Database.ACTION_GET_ALL_SUBJECT, null);

		List<String> listSubject = new ArrayList<String>();
		for (int i = 0; i < allSubject.size(); i++) {
			listSubject.add(allSubject.get(i).get(mSQLiteHelper.SUBJECT_NAME));
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, listSubject);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spSubject.setAdapter(dataAdapter);

		currentItem = 0;
		currentSubject = 1;
		ArrayList<String> loadedSubject = new ArrayList<String>();
		loadedSubject.add("1");
		allItem = db.query(Database.ACTION_GET_ALL_ITEM_SORT_BY_ANSWER,
				loadedSubject);
		ViewItem();
	}

	public void initView() {
		imgvItemImage = (ImageView) findViewById(R.id.imgv_item_image);
		tvMeaning = (TextView) findViewById(R.id.tv_meaning);
		tvResultCorrect = (TextView) findViewById(R.id.tv_result_correct);
		tvResultWrong = (TextView) findViewById(R.id.tv_result_wrong);
		btnNext = (ImageButton) findViewById(R.id.btn_next);
		btnPrev = (ImageButton) findViewById(R.id.btn_prev);
		spSubject = (Spinner) findViewById(R.id.spinner_statistic_list_subject);

		imgvItemImage.setOnClickListener(this);
		tvMeaning.setOnClickListener(this);
		btnNext.setOnClickListener(this);
		btnPrev.setOnClickListener(this);
		spSubject.setOnItemSelectedListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.statistic_playing_result, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		tts.close();
		db.close();
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgv_item_image:
			String audioFilePath = allItem.get(currentItem).get(
					mSQLiteHelper.ITEM_AUDIO_LINK);
			if (audioFilePath == null || audioFilePath.length() == 0) {
				tts.speakText(allItem.get(currentItem).get(
						mSQLiteHelper.ITEM_NAME));
			}
			break;
		case R.id.tv_meaning:

			break;
		case R.id.tv_result_correct:

			break;
		case R.id.tv_result_wrong:
			
			break;
		case R.id.btn_next:
			if (currentItem < allItem.size() - 1) {
				currentItem++;
				ViewItem();
			} else {
				currentItem = 0;
				ViewItem();
			}
			break;
		case R.id.btn_prev:
			if (currentItem == 0) {
				currentItem = allItem.size() - 1;
				ViewItem();
			} else {
				currentItem--;
				ViewItem();
			}
			break;

		default:
			break;
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		if (currentSubject == (pos + 1)) {
			return;
		}
		currentSubject = pos + 1;
		currentItem = 0;
		ArrayList<String> loadedSubject = new ArrayList<String>();
		loadedSubject.add(currentSubject + "");
		allItem = db.query(Database.ACTION_GET_ALL_ITEM_SORT_BY_ANSWER,
				loadedSubject);
		ViewItem();

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// Another interface callback
	}

	private void ViewItem() {
		InputStream is = null;
		Log.e("statistic item", allItem.get(currentItem).toString());
		try {
			is = getAssets().open(
					allItem.get(currentItem).get(mSQLiteHelper.ITEM_IMG_LINK));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Drawable d = Drawable.createFromStream(is, null);
		imgvItemImage.setImageDrawable(d);
		tvMeaning
				.setText(allItem.get(currentItem).get(mSQLiteHelper.ITEM_NAME));
		tvResultCorrect.setText(allItem.get(currentItem).get(
				"Correct: " + mSQLiteHelper.ITEM_CORRECT_ANSWER));
		tvResultWrong.setText(allItem.get(currentItem).get(
				"Wrong: " + mSQLiteHelper.ITEM_WRONG_ANSWER));
	}
}
