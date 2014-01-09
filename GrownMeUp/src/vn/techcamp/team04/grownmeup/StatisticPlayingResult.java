package vn.techcamp.team04.grownmeup;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import vn.techcamp.team04.grownmeup.database.Database;
import vn.techcamp.team04.grownmeup.database.mSQLiteHelper;
import vn.techcamp.team04.grownmeup.utility.mTTS;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class StatisticPlayingResult extends Activity implements OnClickListener {

	private ImageView imgvItemImage;
	private TextView tvMeaning;
	private ImageButton btnNext;
	private ImageButton btnPrev;
	private TextView tvResult;
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
		currentItem = 0;
		currentSubject = 1;
		ArrayList<String> currentSubject = new ArrayList<String>();
		currentSubject.add("1");
		allItem = db.query(Database.ACTION_GET_ALL_ITEM, currentSubject);
		ViewItem();
	}

	public void initView() {
		imgvItemImage = (ImageView) findViewById(R.id.imgv_item_image);
		tvMeaning = (TextView) findViewById(R.id.tv_meaning);
		tvResult = (TextView) findViewById(R.id.tv_result);
		btnNext = (ImageButton) findViewById(R.id.btn_next);
		btnPrev = (ImageButton) findViewById(R.id.btn_prev);

		imgvItemImage.setOnClickListener(this);
		tvMeaning.setOnClickListener(this);
		btnNext.setOnClickListener(this);
		btnPrev.setOnClickListener(this);
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
		super.onDestroy();
	}

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
		case R.id.tv_result:

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

	private void ViewItem() {
		InputStream is = null;
		Log.e("", allItem.get(currentItem).get(mSQLiteHelper.ITEM_IMG_LINK));
		try {
			is = getAssets().open(
					allItem.get(currentItem).get(mSQLiteHelper.ITEM_IMG_LINK));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Drawable d = Drawable.createFromStream(is, null);
		Log.d("", d.toString());
		imgvItemImage.setImageDrawable(d);
		tvMeaning
				.setText(allItem.get(currentItem).get(mSQLiteHelper.ITEM_NAME));
		tvResult.setText(allItem.get(currentItem).get(
				mSQLiteHelper.ITEM_CORRECT_ANSWER)
				+ " / "
				+ allItem.get(currentItem).get(mSQLiteHelper.ITEM_WRONG_ANSWER));
	}
}
