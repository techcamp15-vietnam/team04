package vn.techcamp.team04.grownmeup;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import vn.techcamp.team04.grownmeup.database.Database;
import vn.techcamp.team04.grownmeup.database.mSQLiteHelper;
import vn.techcamp.team04.grownmeup.utility.Utility;
import vn.techcamp.team04.grownmeup.utility.mPlayAudio;
import vn.techcamp.team04.grownmeup.utility.mRecord;
import vn.techcamp.team04.grownmeup.utility.mTTS;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * @author 4-A bui trung hieu.
 * 
 */
public class AddNewItemActivity extends Activity implements OnClickListener,
		OnItemSelectedListener {
	private ImageButton btnTakePic;
	private ImageButton btnRecord;
	private ImageButton btnRecording;
	private ImageButton btnPlay;
	private ImageButton btnPlaying;
	private ImageButton btnSave;
	private EditText edtDescription;
	private Spinner spAllSubjects;
	Drawable d;
	String imagePath;
	private mTTS mTTS;
	private mRecord mRecord;
	private mPlayAudio mPlayAudio;
	private boolean isRecording = false;
	private boolean hasRecorded = false;
	private String subjectID;
	private Database db;
	ArrayList<HashMap<String, String>> allSubject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_item_screen);
		db = new Database(this);
		initView();
		try {
			d = Drawable.createFromStream(getAssets().open("image/char.png"),
					null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mRecord = new mRecord();
		mTTS = new mTTS(this, 0.7f);

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
		btnRecording = (ImageButton) findViewById(R.id.add_item_btn_recording);
		btnPlaying = (ImageButton) findViewById(R.id.add_item_btn_playing);
		edtDescription = (EditText) findViewById(R.id.add_item_edt_description);
		spAllSubjects = (Spinner) findViewById(R.id.add_item_spinner_list_subject);

		btnTakePic.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		btnPlay.setOnClickListener(this);
		btnRecord.setOnClickListener(this);
		btnPlaying.setOnClickListener(this);
		btnRecording.setOnClickListener(this);
		spAllSubjects.setOnItemSelectedListener(this);

		// add all subject to spinner
		List<String> listSubject = new ArrayList<String>();
		allSubject = db.query(Database.ACTION_GET_ALL_SUBJECT, null);
		for (int i = 0; i < allSubject.size(); i++) {
			listSubject.add(allSubject.get(i).get(mSQLiteHelper.SUBJECT_NAME));
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, listSubject);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spAllSubjects.setAdapter(dataAdapter);
	}

	@Override
	protected void onDestroy() {
		mTTS.close();
		mRecord.stop();
		db.close();
		super.onDestroy();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.add_item_btn_record:
			try {
				isRecording = true;
				btnRecord.setVisibility(View.GONE);
				btnRecording.setVisibility(View.VISIBLE);
				btnPlay.setEnabled(false);
				Log.d("Sound file path",
						Utility.getCusTomItemsSoundFilePath("tmp.3gp"));
				mRecord.recordAudio(Utility
						.getCusTomItemsSoundFilePath("tmp.3gp"));
			} catch (IOException e) {
				e.printStackTrace();
				Toast.makeText(this, "Cannot record.", Toast.LENGTH_SHORT)
						.show();
			}

			break;
		case R.id.add_item_btn_camera:
			imagePath = Utility.getCustomItemsImageFilePath("pig.png");
			Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
			btnTakePic.setImageBitmap(bitmap);

			break;

		case R.id.add_item_btn_recording:
			isRecording = false;
			hasRecorded = true;
			Log.d("Record", "had recorded");
			btnRecord.setVisibility(View.VISIBLE);
			btnRecording.setVisibility(View.GONE);
			btnPlay.setEnabled(true);
			mRecord.stop();
			break;
		case R.id.add_item_btn_save:
			if (imagePath != null && imagePath.length() != 0
					&& edtDescription.getText().toString().length() != 0
					&& isRecording == false) {
				// add new item and exit

				ArrayList<String> newItem = new ArrayList<String>();
				newItem.add(subjectID); // subjectID
				newItem.add(edtDescription.getText().toString()); // description
				newItem.add(imagePath); // image path
				if (hasRecorded) {
					// rename temp recorded file
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyyMMdd_HHmmss");
					String currentDateandTime = sdf.format(new Date());
					File from = new File(
							Utility.getCusTomItemsSoundFilePath("tmp.3gp"));
					File to = new File(
							Utility.getCusTomItemsSoundFilePath(currentDateandTime
									+ ".3gp"));
					from.renameTo(to);
					Log.d("", to.getPath());
					newItem.add(to.getPath()); // recorded audio
				} else {
					newItem.add(""); // no audio
				}
				Log.e("newItems", newItem.toString());
				db.query(Database.ACTION_ADD_NEW_ITEM, newItem);
				Log.d("Save", "save to database");
				Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
				finish();
			}
			break;
		case R.id.add_item_btn_play:
			if (isRecording == false) {
				try {
					Log.d("click add_item_btn_play", "start playing");
					// had recorded file.Now play that.
					btnRecord.setEnabled(false);
					mPlayAudio = new mPlayAudio();
					mPlayAudio.play(Utility
							.getCusTomItemsSoundFilePath("tmp.3gp"));
					btnRecord.setEnabled(true);
				} catch (IOException e) {
					Log.d("AddNewItemActivity", "Not has recorded.Play tts.");
					String description = edtDescription.getText().toString();
					if (description.length() != 0) {
						btnRecord.setEnabled(false);
						mTTS.speakText(description);
						btnRecord.setEnabled(true);
					} else {
						Log.d("AddNewItemActivity", "description empty.");
					}

				}
			} else {
				Log.d("click add_item_btn_play", "still playing");
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		subjectID = allSubject.get(arg2).get(mSQLiteHelper.SUBJECT_ID);
		Log.d("choose subject", subjectID);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}
}
