package vn.techcamp.team04.grownmeup;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

/**
 * @author trung hieu
 * 
 */
public class AddNewItemActivity extends Activity implements OnClickListener {
	private ImageButton btnChoose;
	private ImageButton btnTakePic;
	private ImageButton btnSave;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_item_screen);
		initView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_item, menu);
		return true;
	}

	/**
	 * @init and set listener for button.
	 * @param no
	 * @return no
	 */
	private void initView() {
		btnChoose = (ImageButton) findViewById(R.id.add_item_btn_image_choose);
		btnTakePic = (ImageButton) findViewById(R.id.add_item_btn_camera);
		btnSave = (ImageButton) findViewById(R.id.add_item_btn_save);

		btnChoose.setOnClickListener(this);
		btnTakePic.setOnClickListener(this);
		btnSave.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.add_item_btn_image_choose:

			break;
		case R.id.add_item_btn_camera:

			break;
		case R.id.add_item_btn_save:

			break;
		default:
			break;
		}
	}

}
