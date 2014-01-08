package vn.techcamp.team04.grownmeup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

/**
 * @author 4-A bui trung hieu.
 * 
 */
public class OptionActivity extends Activity implements OnClickListener {
	private ImageButton btnAddItem;
	private ImageButton btnCustomStage;
	private ImageButton btnlanguage;
	private ImageButton btnAbout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.option_screen);
		initView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.option, menu);
		return true;
	}

	/**
	 * init and set listener for button.
	 * 
	 * @param
	 * @return
	 */
	private void initView() {
		btnAddItem = (ImageButton) findViewById(R.id.option_add_item);
		btnCustomStage = (ImageButton) findViewById(R.id.option_custom_stage);
		btnlanguage = (ImageButton) findViewById(R.id.option_language);
		btnAbout = (ImageButton) findViewById(R.id.option_about);

		btnAddItem.setOnClickListener(this);
		btnCustomStage.setOnClickListener(this);
		btnlanguage.setOnClickListener(this);
		btnAbout.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.option_add_item:
			Intent addNewItemItent = new Intent(this, AddNewItemActivity.class);
			startActivity(addNewItemItent);

			break;
		case R.id.option_custom_stage:
			Intent customStageItent = new Intent(this,
					CustomStageActivity.class);
			startActivity(customStageItent);

			break;
		case R.id.option_language:

			break;
		case R.id.option_about:

			break;
		default:
			break;
		}
	}
}
