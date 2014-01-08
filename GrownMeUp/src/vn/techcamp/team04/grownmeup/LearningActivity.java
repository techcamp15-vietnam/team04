package vn.techcamp.team04.grownmeup;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import vn.techcamp.team04.grownmeup.database.Database;
import vn.techcamp.team04.grownmeup.database.mSQLiteHelper;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author hieu 4-A
 * 
 */
public class LearningActivity extends Activity implements OnClickListener {

	private ImageView imgvLearningImage;
	private TextView tvMeaning;
	private ImageButton btnNext;
	private ImageButton btnPrev;
	private Database db;
	private ArrayList<HashMap<String, String>> allItem;
	private int currentItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.learning_screen);
		initView();
		db = new Database(this);
		currentItem = 0;
		ArrayList<String> currentSubject = new ArrayList<String>();
		currentSubject.add("1");
		allItem = db.query(Database.ACTION_GET_ALL_ITEM, currentSubject);
		ViewItem();
	}

	/**
	 * @author hieu 4-A
	 */
	public void initView() {
		imgvLearningImage = (ImageView) findViewById(R.id.imgv_learning_image);
		tvMeaning = (TextView) findViewById(R.id.tv_meaning);
		btnNext = (ImageButton) findViewById(R.id.btn_next);
		btnPrev = (ImageButton) findViewById(R.id.btn_prev);

		imgvLearningImage.setOnClickListener(this);
		tvMeaning.setOnClickListener(this);
		btnNext.setOnClickListener(this);
		btnPrev.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.learning, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgv_learning_image:

			break;
		case R.id.tv_meaning:

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
					getImageFileName(allItem.get(currentItem).get(
							mSQLiteHelper.ITEM_IMG_LINK)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Drawable d = Drawable.createFromStream(is, null);
		imgvLearningImage.setImageDrawable(d);

	}

	private String getImageFileName(String name) {
		return name + ".png";
	}

	private String getSoundFIleName(String name) {
		return name + ".mp3";
	}
}
