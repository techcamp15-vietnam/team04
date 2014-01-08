package vn.techcamp.team04.grownmeup;

import vn.techcamp.team04.grownmeup.database.Database;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

/**
 * @author hiepns
 * 
 */
public class MainMenuActivity extends Activity implements OnClickListener {

	private ImageButton btnLearn;
	private ImageButton btnPlay;
	private ImageButton btnHighScore;
	private ImageButton btnOption;
	private ImageButton btnExit;
	private Database db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu_screen);
		initView();
		db = new Database(this);
		db.setDefaultData();
	}

	public void initView() {
		btnLearn = (ImageButton) findViewById(R.id.btn_learn);
		btnPlay = (ImageButton) findViewById(R.id.btn_play);
		btnHighScore = (ImageButton) findViewById(R.id.btn_highscore);
		btnOption = (ImageButton) findViewById(R.id.btn_option);
		btnExit = (ImageButton) findViewById(R.id.btn_exit);

		btnLearn.setOnClickListener(this);
		btnPlay.setOnClickListener(this);
		btnHighScore.setOnClickListener(this);
		btnOption.setOnClickListener(this);
		btnExit.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_learn:
			Intent learningActivity = new Intent(this, LearningActivity.class);
			startActivity(learningActivity);
			break;
		case R.id.btn_play:
			Intent subjectChooserActivity = new Intent(this,
					SubjectChooserActivity.class);
			startActivity(subjectChooserActivity);
			break;
		case R.id.btn_highscore:
			break;
		case R.id.btn_option:
			Intent optionIntent = new Intent(this, OptionActivity.class);
			startActivity(optionIntent);

			break;
		case R.id.btn_exit:
			break;

		default:
			break;
		}

	}

}
