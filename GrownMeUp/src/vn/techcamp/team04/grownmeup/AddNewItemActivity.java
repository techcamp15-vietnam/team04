package vn.techcamp.team04.grownmeup;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AddNewItemActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_item_screen);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_item, menu);
		return true;
	}

}
