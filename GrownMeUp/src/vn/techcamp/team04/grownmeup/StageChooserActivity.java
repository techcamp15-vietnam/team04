package vn.techcamp.team04.grownmeup;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class StageChooserActivity extends Activity implements OnClickListener {
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stage_chooser_screen);
		initView();
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

		btnNext.setOnClickListener(this);
		btnPrev.setOnClickListener(this);

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

			break;
		case R.id.btn_prev:

			break;

		default:
			break;
		}
	}

}
