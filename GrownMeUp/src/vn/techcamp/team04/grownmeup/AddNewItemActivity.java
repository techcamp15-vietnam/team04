package vn.techcamp.team04.grownmeup;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

/**
 * @author 4-A bui trung hieu.
 * 
 */
public class AddNewItemActivity extends Activity implements OnClickListener {
	private ImageButton btnTakePic;
	private ImageButton btnRecord;
	private ImageButton btnPlay;
	private ImageButton btnSave;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_item_screen);
		initView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add_item, menu);
		return true;
	}

	/**
	 * init and set listener for button.
	 * 
	 * @param
	 * @return
	 */
	private void initView() {
		btnTakePic = (ImageButton) findViewById(R.id.add_item_btn_camera);
		btnSave = (ImageButton) findViewById(R.id.add_item_btn_save);
		btnRecord = (ImageButton) findViewById(R.id.add_item_btn_record);
		btnPlay = (ImageButton) findViewById(R.id.add_item_btn_play);

		btnTakePic.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		btnPlay.setOnClickListener(this);
		btnRecord.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.add_item_btn_record:

			break;
		case R.id.add_item_btn_camera:

			break;
		case R.id.add_item_btn_save:

			break;
		case R.id.add_item_btn_play:

			break;

		default:
			break;
		}
	}

}
