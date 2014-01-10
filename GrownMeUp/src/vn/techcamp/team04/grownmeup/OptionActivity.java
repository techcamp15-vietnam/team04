package vn.techcamp.team04.grownmeup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vn.techcamp.team04.grownmeup.database.Database;
import vn.techcamp.team04.grownmeup.database.mSQLiteHelper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
	private AlertDialog levelDialog;
	private Database db;
	private ArrayList<HashMap<String, String>> allSubject;
	private CharSequence[] allSubs;
	public static final String OPTION = "option";
	public static final String DEFAULT_SUBJECT = "default_subject";
	private SharedPreferences settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.option_screen);
		initView();
		db = new Database(this);
		allSubs = getAllSubject();
		settings = getSharedPreferences(OPTION, 0);
	}

	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
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
			ChooseDefaultSubjectDialog(allSubs);
			break;
		case R.id.option_about:

			break;
		default:
			break;
		}
	}

	/**
	 * get all subject.
	 * 
	 * @return all subject in array, sort by subjectID
	 */
	private CharSequence[] getAllSubject() {
		List<String> listSubject = new ArrayList<String>();
		allSubject = db.query(Database.ACTION_GET_ALL_SUBJECT, null);
		CharSequence[] allSubs = new CharSequence[allSubject.size()];
		for (int i = 0; i < allSubject.size(); i++) {
			allSubs[i] = allSubject.get(i).get(mSQLiteHelper.SUBJECT_NAME);
		}
		return allSubs;

	}

	private void ChooseDefaultSubjectDialog(CharSequence[] allSubjects) {
		int checked;
		checked = settings.getInt(DEFAULT_SUBJECT, 1);
		// Creating and Building the Dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Select The Default Subject");
		builder.setSingleChoiceItems(allSubjects, checked - 1,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int item) {
						SharedPreferences.Editor editor = settings.edit();
						editor.putInt(DEFAULT_SUBJECT, item + 1);
						Log.d("default subject selected", item + 1 + "");
						editor.commit();
						levelDialog.dismiss();
					}
				});
		levelDialog = builder.create();
		levelDialog.show();

	}
}
