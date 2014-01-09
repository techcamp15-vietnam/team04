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
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Nguyen Sinh Hiep 4-C
 * 
 */
public class SubjectChooserActivity extends Activity implements OnClickListener {
	private ImageButton btnNext;
	private ImageButton btnPrev;

	private ImageView imgvTopLeft;
	private ImageView imgvTopRight;
	private ImageView imgvBottomLeft;
	private ImageView imgvBottomRight;

	private TextView tvTopLeft;
	private TextView tvTopRight;
	private TextView tvBottomLeft;
	private TextView tvBottomRight;

	private LinearLayout lnTopLeft;
	private LinearLayout lnTopRight;
	private LinearLayout lnBottomLeft;
	private LinearLayout lnBottomRight;

	private Database db;
	private ArrayList<HashMap<String, String>> allSubject;
	private String itemTopLeft;
	private String itemTopRight;
	private String itemBottomLeft;
	private String itemBottomRight;

	private int currentSubject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subject_chooser_screen);
		initView();
		initSubject();
	}

	public void initView() {
		btnNext = (ImageButton) findViewById(R.id.btn_next);
		btnPrev = (ImageButton) findViewById(R.id.btn_prev);

		imgvTopLeft = (ImageView) findViewById(R.id.imgv_top_left);
		imgvTopRight = (ImageView) findViewById(R.id.imgv_top_right);
		imgvBottomLeft = (ImageView) findViewById(R.id.imgv_bottom_left);
		imgvBottomRight = (ImageView) findViewById(R.id.imgv_bottom_right);

		tvTopLeft = (TextView) findViewById(R.id.tv_top_left);
		tvTopRight = (TextView) findViewById(R.id.tv_top_right);
		tvBottomLeft = (TextView) findViewById(R.id.tv_bottom_left);
		tvBottomRight = (TextView) findViewById(R.id.tv_bottom_right);

		lnTopLeft = (LinearLayout) findViewById(R.id.ln_subject_top_left);
		lnTopRight = (LinearLayout) findViewById(R.id.ln_subject_top_right);
		lnBottomLeft = (LinearLayout) findViewById(R.id.ln_subject_bottom_left);
		lnBottomRight = (LinearLayout) findViewById(R.id.ln_subject_bottom_right);

		btnNext.setOnClickListener(this);
		btnPrev.setOnClickListener(this);
		lnTopLeft.setOnClickListener(this);
		lnTopRight.setOnClickListener(this);
		lnBottomLeft.setOnClickListener(this);
		lnBottomRight.setOnClickListener(this);

	}

	public void initSubject() {
		db = new Database(this);
		allSubject = db.query(Database.ACTION_GET_ALL_SUBJECT);

		currentSubject = 0;
		loadSubject();

	}

	public void loadSubject() {
		if (currentSubject < allSubject.size()) {
			itemTopLeft = allSubject.get(currentSubject).get(
					mSQLiteHelper.SUBJECT_NAME);
		}
		if (currentSubject + 1 < allSubject.size()) {
			itemTopRight = allSubject.get(currentSubject + 1).get(
					mSQLiteHelper.SUBJECT_NAME);
		}
		if (currentSubject + 2 < allSubject.size()) {
			itemBottomLeft = allSubject.get(currentSubject + 2).get(
					mSQLiteHelper.SUBJECT_NAME);
		}
		if (currentSubject + 3 < allSubject.size()) {
			itemBottomRight = allSubject.get(currentSubject + 3).get(
					mSQLiteHelper.SUBJECT_NAME);
		}

		InputStream is = null;
		if (itemTopLeft != null) {
			tvTopLeft.setText(itemTopLeft);
			try {
				is = getAssets().open("image/subject/" + itemTopLeft + ".png");
			} catch (IOException e) {
				e.printStackTrace();
			}
			Drawable d = Drawable.createFromStream(is, null);
			imgvTopLeft.setImageDrawable(d);
		}
		if (itemTopRight != null) {
			tvTopRight.setText(itemTopRight);
			try {
				is = getAssets().open("image/subject/" + itemTopRight + ".png");
			} catch (IOException e) {
				e.printStackTrace();
			}
			Drawable d = Drawable.createFromStream(is, null);
			imgvTopRight.setImageDrawable(d);
		}
		if (itemBottomLeft != null) {
			tvBottomLeft.setText(itemBottomLeft);
			try {
				is = getAssets().open(
						"image/subject/" + itemBottomLeft + ".png");
			} catch (IOException e) {
				e.printStackTrace();
			}
			Drawable d = Drawable.createFromStream(is, null);
			imgvBottomLeft.setImageDrawable(d);
		}
		if (itemBottomRight != null) {
			tvBottomRight.setText(itemBottomRight);
			try {
				is = getAssets().open(
						"image/subject/" + itemBottomRight + ".png");
			} catch (IOException e) {
				e.printStackTrace();
			}
			Drawable d = Drawable.createFromStream(is, null);
			imgvBottomRight.setImageDrawable(d);
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
			if (currentSubject + 4 < allSubject.size() - 1) {
				this.currentSubject += 4;
				loadSubject();
			}

			break;
		case R.id.btn_prev:
			if (currentSubject >= 4) {
				this.currentSubject -= 4;
				loadSubject();
			}

			break;

		case R.id.ln_subject_top_left:
			startStageChooser(currentSubject);

			break;
		case R.id.ln_subject_top_right:
			startStageChooser(currentSubject + 1);
			break;
		case R.id.ln_subject_bottom_left:
			startStageChooser(currentSubject + 2);

			break;
		case R.id.ln_subject_bottom_right:
			startStageChooser(currentSubject + 3);

			break;

		default:
			break;
		}
	}

	public void startStageChooser(int subjectID) {
		Intent intent = new Intent(SubjectChooserActivity.this,
				StageChooserActivity.class);
		intent.putExtra("subjectID", subjectID + 1);
		startActivity(intent);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		db.close();

	}

}
