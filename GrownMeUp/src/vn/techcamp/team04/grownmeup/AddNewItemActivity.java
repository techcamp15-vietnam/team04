package vn.techcamp.team04.grownmeup;

import java.io.IOException;

import vn.techcamp.team04.grownmeup.utility.mPlayAudio;
import vn.techcamp.team04.grownmeup.utility.mRecord;
import vn.techcamp.team04.grownmeup.utility.mTTS;
import vn.techcamp.team04.grownmeup.utility.Utility;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * @author 4-A bui trung hieu.
 * 
 */
public class AddNewItemActivity extends Activity implements OnClickListener {
	private ImageButton btnTakePic;
	private ImageButton btnRecord;
	private ImageButton btnRecording;
	private ImageButton btnPlay;
	private ImageButton btnPlaying;
	private ImageButton btnSave;
	private EditText edtDescription;
	Drawable d;
	private mTTS mTTS;
	private mRecord mRecord;
	private mPlayAudio mPlayAudio;
	private boolean isRecording = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_item_screen);
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
		mPlayAudio = new mPlayAudio();
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

		btnTakePic.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		btnPlay.setOnClickListener(this);
		btnRecord.setOnClickListener(this);
		btnPlaying.setOnClickListener(this);
		btnRecording.setOnClickListener(this);

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

			break;

		case R.id.add_item_btn_recording:
			isRecording = false;
			btnRecord.setVisibility(View.VISIBLE);
			btnRecording.setVisibility(View.GONE);
			btnPlay.setEnabled(true);
			mRecord.stop();
			break;
		case R.id.add_item_btn_save:

			break;
		case R.id.add_item_btn_play:
			if (isRecording == false) {
				try {
					// had recorded file.Now play that.
					btnPlay.setVisibility(View.GONE);
					btnPlaying.setVisibility(View.VISIBLE);
					btnRecord.setEnabled(false);
					mPlayAudio.play(Utility
							.getCusTomItemsSoundFilePath("tmp.3gp"));
					btnPlay.setVisibility(View.VISIBLE);
					btnPlaying.setVisibility(View.GONE);
					btnRecord.setEnabled(true);
				} catch (IOException e) {
					Log.d("AddNewItemActivity", "Not has recorded.Play tts.");
					String description = edtDescription.getText().toString();
					if (description.length() != 0) {
						btnPlay.setVisibility(View.GONE);
						btnPlaying.setVisibility(View.VISIBLE);
						btnRecord.setEnabled(false);
						mTTS.speakText(description);
						btnPlay.setVisibility(View.VISIBLE);
						btnPlaying.setVisibility(View.GONE);
						btnRecord.setEnabled(true);
					} else {
						Log.d("AddNewItemActivity", "description empty.");
					}

				}
			}
			break;
		case R.id.add_item_btn_playing:
			btnPlay.setVisibility(View.VISIBLE);
			btnPlaying.setVisibility(View.GONE);
			btnRecord.setEnabled(false);
			mTTS.stop();
			break;
		default:
			break;
		}
	}
}
