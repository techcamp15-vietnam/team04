package vn.techcamp.team04.grownmeup;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class EditStageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stage_detail_screen);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_stage, menu);
		return true;
	}

}
