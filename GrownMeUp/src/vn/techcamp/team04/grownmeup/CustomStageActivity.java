package vn.techcamp.team04.grownmeup;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

/**
 * @author 4-A bui trung hieu.
 * 
 */
public class CustomStageActivity extends Activity implements OnClickListener {
	private ImageButton btnCreateCustomStage;
	private ImageButton btnDeleteCustomStage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_stage_screen);
		initView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.custom_stage, menu);
		return true;
	}

	/**
	 * @init and set listener for button.
	 * @param no
	 * @return no
	 */
	private void initView() {
		btnCreateCustomStage = (ImageButton) findViewById(R.id.custom_stage_create);
		btnDeleteCustomStage = (ImageButton) findViewById(R.id.custom_stage_delete);

		btnCreateCustomStage.setOnClickListener(this);
		btnDeleteCustomStage.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.custom_stage_create:

			break;
		case R.id.custom_stage_delete:

			break;
		default:
			break;
		}
	}

}
