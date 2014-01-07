package vn.techcamp.team04.grownmeup;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class StageChooserActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stage_chooser_screen);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stage_chooser, menu);
		return true;
	}

}
