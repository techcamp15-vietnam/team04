package vn.techcamp.team04.grownmeup;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author zendbui
 * @author 4-B Bui Trong Hieu
 */
public class HighScoreActivity extends Activity implements OnClickListener {

	private ImageButton mBtnReport;
	private ImageView mBadge1;
	private ImageView mBadge2;
	private ImageView mBadge3;
	private ImageView mBadge4;
	private ImageView mBadge5;
	private ImageView mBadge6;
	private TextView mTvExplainBadge;

	
	public static String ACHIEVEMENT = "achievement";
	public static String badge1 = "complete first stage";
	public static String badge2 = "complete five stage";
	public static String badge3 = "answer correct an item 10 times";
	public static String badge4 = "learn 10 item";
	public static String badge5 = "fastest stage";
	public static String badge6 = "finish all stage";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.high_score_screen);
		initView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.high_score, menu);
		return true;
	}

	/**
	 * init and set listener for button.
	 * 
	 * @param no
	 * @return no
	 */
	private void initView() {
		mBtnReport = (ImageButton) findViewById(R.id.high_score_btn_report);
		mBadge1 = (ImageView) findViewById(R.id.high_score_img_achievement_1);
		mBadge2 = (ImageView) findViewById(R.id.high_score_img_achievement_2);
		mBadge3 = (ImageView) findViewById(R.id.high_score_img_achievement_3);
		mBadge4 = (ImageView) findViewById(R.id.high_score_img_achievement_4);
		mBadge5 = (ImageView) findViewById(R.id.high_score_img_achievement_5);
		mBadge6 = (ImageView) findViewById(R.id.high_score_img_achievement_6);
		mTvExplainBadge = (TextView)findViewById(R.id.high_socre_tv_explain_badge);
		mTvExplainBadge.setText("");
		
		mBtnReport.setOnClickListener(this);
		mBadge1.setOnClickListener(this);
		mBadge2.setOnClickListener(this);
		mBadge3.setOnClickListener(this);
		mBadge4.setOnClickListener(this);
		mBadge5.setOnClickListener(this);
		mBadge6.setOnClickListener(this);

		SharedPreferences settings = getSharedPreferences(ACHIEVEMENT, 0);
		boolean mValuebadge1 = settings.getBoolean(badge1, false);
		if (!mValuebadge1) {
			mBadge1.setImageResource(R.drawable.black_badge);
		}
		boolean mValuebadge2 = settings.getBoolean(badge2, false);
		if (!mValuebadge2) {
			mBadge2.setImageResource(R.drawable.black_badge);
		}
		boolean mValuebadge3 = settings.getBoolean(badge3, false);
		if (!mValuebadge3) {
			mBadge3.setImageResource(R.drawable.black_badge);
		}
		boolean mValuebadge4 = settings.getBoolean(badge4, false);
		if (!mValuebadge4) {
			mBadge4.setImageResource(R.drawable.black_badge);
		}
		float mValuebadge5 = settings.getFloat(badge5, (float)0.0);
		if (mValuebadge5== 0.0) {
			mBadge5.setImageResource(R.drawable.black_badge);
		}
		boolean mValuebadge6 = settings.getBoolean(badge6, false);
		if (!mValuebadge6) {
			mBadge6.setImageResource(R.drawable.black_badge);
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.high_score_btn_report:

			break;
		case R.id.high_score_img_achievement_1:
			Toast.makeText(getApplicationContext(), "badge 1",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.high_score_img_achievement_2:
			Toast.makeText(getApplicationContext(), "badge 2",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.high_score_img_achievement_3:
			Toast.makeText(getApplicationContext(), "badge 3",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.high_score_img_achievement_4:
			Toast.makeText(getApplicationContext(), "badge 4",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.high_score_img_achievement_5:
			Toast.makeText(getApplicationContext(), "badge 5",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.high_score_img_achievement_6:
			Toast.makeText(getApplicationContext(), "badge 6",
					Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

}
