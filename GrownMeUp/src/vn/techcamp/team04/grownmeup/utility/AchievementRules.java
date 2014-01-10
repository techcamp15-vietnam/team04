package vn.techcamp.team04.grownmeup.utility;

import java.util.ArrayList;
import java.util.HashMap;

import vn.techcamp.team04.grownmeup.database.Database;
import vn.techcamp.team04.grownmeup.database.mSQLiteHelper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author zendbui
 * @author 4-B Bui Trong Hieu
 */
public class AchievementRules {
	public static String ACHIEVEMENT = "achievement";
	public static String blank_badge = "no badge";
	public static String badge1 = "complete first stage";
	public static String badge2 = "complete 5 stage";
	public static String badge3 = "first times answer correct";
	public static String badge4 = "answer correct an item 10 times";
	public static String badge5 = "fastest stage";
	public static String badge6 = "finish all stage";

	public static String TOTAL = "total stage";
	public static String COMPLETED_STAGES = "completed stage";
	public static int totalStage = 10;

	private Context context;

	public AchievementRules(Context context) {
		this.context = context;
	}

	/**
	 * @return　badge received
	 * @request call when finish a stage
	 */
	public ArrayList<String> checkNumberCompleteStage() {
		SharedPreferences settings = this.context.getSharedPreferences(
				ACHIEVEMENT, 0);
		int current = settings.getInt(COMPLETED_STAGES, 0) + 1;
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(AchievementRules.COMPLETED_STAGES, current);
		ArrayList<String> result = new ArrayList<String>();
		String check = null;
		String receiveBadge = null;

		switch (current) {
		case 1:
			check = "true";
			receiveBadge = badge1 + "";
			editor.putBoolean(AchievementRules.badge1, true);
			break;
		case 5:
			check = "true";
			receiveBadge = badge2 + "";
			editor.putBoolean(AchievementRules.badge2, true);
			break;
		case 10:
			check = "true";
			receiveBadge = badge6 + "";
			editor.putBoolean(AchievementRules.badge6, true);
			break;
		default:
			check = "false";
			receiveBadge = blank_badge + "";
			break;
		}
		editor.commit();
		result.add(check);
		result.add(receiveBadge);
		return result;
	}

	/**
	 * @param itemID
	 * @return　badge received
	 * @request call after increase count_answer of item
	 */
	public ArrayList<String> checkNumberCorrectedAnswer(int itemID) {
		ArrayList<HashMap<String, String>> item = new ArrayList<HashMap<String, String>>();
		Database db = new Database(this.context);
		ArrayList<String> itemValue = new ArrayList<String>();
		itemValue.add(itemID + "");
		item = db.query(Database.ACTION_GET_ITEM, itemValue);
		int correct = Integer.parseInt(item.get(0).get(
				mSQLiteHelper.ITEM_CORRECT_ANSWER));
		SharedPreferences settings = this.context.getSharedPreferences(
				ACHIEVEMENT, 0);
		SharedPreferences.Editor editor = settings.edit();
		ArrayList<String> result = new ArrayList<String>();
		String check = null;
		String receiveBadge = null;
		switch (correct) {
		case 1:
			check = "true";
			receiveBadge = badge3 + "";
			editor.putBoolean(AchievementRules.badge3, true);
			break;
		case 10:
			check = "true";
			receiveBadge = badge4 + "";
			editor.putBoolean(AchievementRules.badge4, true);
			break;
		default:
			check = "false";
			receiveBadge = blank_badge + "";
			break;
		}
		editor.commit();
		result.add(check);
		result.add(receiveBadge);
		db.close();
		return result;
	}

	/**
	 * @param timeRecorded
	 * @return badge received
	 */
	public String checkFastestStage(float timeRecorded) {
		String receiveBadge = null;
		SharedPreferences settings = this.context.getSharedPreferences(
				ACHIEVEMENT, 0);
		float currentTimeRecord = settings.getFloat(badge5, (float) 0.0);
		if (currentTimeRecord == 0.0 || currentTimeRecord < timeRecorded) {
			currentTimeRecord = timeRecorded;
			SharedPreferences.Editor editor = settings.edit();
			editor.putFloat(badge5, currentTimeRecord);
			editor.commit();
			receiveBadge = badge5;
		} else {
			receiveBadge = blank_badge;
		}
		return receiveBadge;
	}
}
