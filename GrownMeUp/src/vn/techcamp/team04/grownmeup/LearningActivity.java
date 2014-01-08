package vn.techcamp.team04.grownmeup;

import android.os.Bundle;
import android.app.Activity;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.learning_screen);
		initView();
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

			break;
		case R.id.btn_prev:

			break;
		default:
			break;
		}

	}

}