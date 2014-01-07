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
 * @author hiepns
 * 
 */
public class LearningActivity extends Activity implements OnClickListener {

	private ImageView imgv_learning_image;
	private TextView tv_meaning;
	private ImageButton btn_next;
	private ImageButton btn_prev;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.learning_screen);
		initView();
	}

	public void initView() {
		imgv_learning_image = (ImageView) findViewById(R.id.imgv_learning_image);
		tv_meaning = (TextView) findViewById(R.id.tv_meaning);
		btn_next = (ImageButton) findViewById(R.id.btn_next);
		btn_prev = (ImageButton) findViewById(R.id.btn_prev);

		imgv_learning_image.setOnClickListener(this);
		tv_meaning.setOnClickListener(this);
		btn_next.setOnClickListener(this);
		btn_prev.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
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
