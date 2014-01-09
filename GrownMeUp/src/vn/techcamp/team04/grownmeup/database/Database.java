package vn.techcamp.team04.grownmeup.database;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * @author zendbui
 * @author 4-B Bui Trong Hieu
 */

public class Database {
	private SQLiteDatabase database;
	private mSQLiteHelper dbHelper;

	private boolean isOpen = false;

	public static final int ACTION_GET_SUBJECT = 1;
	public static final int ACTION_GET_ALL_SUBJECT = 2;
	public static final int ACTION_ADD_NEW_SUBJECT = 3;
	public static final int ACTION_GET_STAGE = 4;
	public static final int ACTION_GET_STAGE_DETAILS = 5;
	public static final int ACTION_GET_ALL_STAGE = 6;
	public static final int ACTION_ADD_NEW_STAGE = 7;
	public static final int ACTION_GET_ITEM = 8;
	public static final int ACTION_GET_ALL_ITEM = 9;
	public static final int ACTION_ADD_NEW_ITEM = 10;
	public static final int ACTION_ADD_ITEM_ANSWER = 11;
	public static final int ACTION_ADD_STAGE_STATUS = 12;

	public static final String KEY_SUCCESS = "success";
	public static final String KEY_FAILURE = "failure";
	public static final String KEY_DUPLICATE = "duplicate";

	private String[] allColumnsSubject = { mSQLiteHelper.SUBJECT_ID,
			mSQLiteHelper.SUBJECT_NAME };
	private String[] allColumnsStage = { mSQLiteHelper.STAGE_ID,
			mSQLiteHelper.SUBJECT_ID, mSQLiteHelper.STAGE_NAME,
			mSQLiteHelper.STAGE_NUMBER, mSQLiteHelper.STAGE_STATUS };
	private String[] allColumnsItem = { mSQLiteHelper.ITEM_ID,
			mSQLiteHelper.SUBJECT_ID, mSQLiteHelper.ITEM_NAME,
			mSQLiteHelper.ITEM_IMG_LINK, mSQLiteHelper.ITEM_AUDIO_LINK,
			mSQLiteHelper.ITEM_CORRECT_ANSWER, mSQLiteHelper.ITEM_WRONG_ANSWER };
	private String[] allColumnsStageDetail = { mSQLiteHelper.STAGE_ID,
			mSQLiteHelper.ITEM_ID };
	// DEFAULT_VALUE format : {{subject1, item11, item12, item13},{subject2,
	// item21, item22, item23}}
	private static final String[][] DEFAUT_VALUE = {
			{ "Home", "chair", "fan", "fork", "knife", "pressure cooker",
					"rice cooker", "shoe", "socket", "table", "tv", "bag",
					"bowl", "cup", "door", "dvd player", "remote", "water can",
					"lock", "bottle", "trash can" },
			{ "Animal", "cat", "snake", "dog", "pig", "bee", "bird", "cow",
					"sheep", "elephant", "horse" }, { "Car" }, { "City" },
			{ "Clothes" }, { "Color" }, { "Plant" }, { "School" } };
	// DEFAUT_STAGE format : {{subjectID, stage_name, stage_number,
	// stage_status, item1ID, item2ID, item3ID, item4ID, item5ID}}
	private static final String[][] DEFAULT_STAGE = {
			{ "1", "stage_1", "1", "0", "1", "2", "3", "4", "5" },
			{ "1", "stage_2", "2", "0", "6", "7", "8", "9", "10" },
			{ "1", "stage_3", "3", "0", "11", "12", "13", "14", "15" },
			{ "1", "stage_4", "4", "0", "16", "17", "18", "19", "20" },
			{ "2", "stage_5", "5", "0", "21", "22", "23", "24", "25" },
			{ "2", "stage_6", "6", "0", "26", "27", "28", "29", "30" } };

	public Database(Context context) {
		dbHelper = new mSQLiteHelper(context);
		open();
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
		this.isOpen = true;
	}

	public void close() {
		dbHelper.close();
	}

	/**
	 * @param action
	 *            : from list action
	 * @param content
	 *            : subjectID or stageID or ... or nothing
	 * @return ArrayList
	 * @throws InvalidParameterException
	 * @throws UnsupportedOperationException
	 */
	public ArrayList<HashMap<String, String>> query(int action,
			ArrayList<String>... content) throws InvalidParameterException,
			UnsupportedOperationException {
		if (!this.isDatabaseOpen()) {
			return null;
		}
		switch (action) {
		case ACTION_GET_SUBJECT:
			// RETURN SUBJECT'S ATTRIBUTE BY GIVING SUBJECT_ID
			ArrayList<HashMap<String, String>> subject = new ArrayList<HashMap<String, String>>();
			if (content[0] instanceof ArrayList<?>) {
				int subjectID = Integer.parseInt(content[0].get(0));
				Cursor cursorSubject = database.query(
						mSQLiteHelper.TABLE_SUBJECT, allColumnsSubject,
						mSQLiteHelper.SUBJECT_ID + " = " + subjectID, null,
						null, null, null);
				cursorSubject.moveToFirst();
				while (!cursorSubject.isAfterLast()) {
					subject.add(cursorToSubject(cursorSubject));
					cursorSubject.moveToNext();
				}
				cursorSubject.close();
				return subject;
			} else {
				throw new InvalidParameterException(
						"content must be implement Subject ID.");
			}

		case ACTION_GET_ALL_SUBJECT:
			// RETURN ALL SUBJECT IN DATABASE
			ArrayList<HashMap<String, String>> listSubject = new ArrayList<HashMap<String, String>>();
			Cursor cursorListSubject = database.query(
					mSQLiteHelper.TABLE_SUBJECT, allColumnsSubject, null, null,
					null, null, null);
			cursorListSubject.moveToFirst();
			while (!cursorListSubject.isAfterLast()) {
				listSubject.add(cursorToSubject(cursorListSubject));
				cursorListSubject.moveToNext();
			}
			cursorListSubject.close();
			return listSubject;

		case ACTION_ADD_NEW_SUBJECT:
			// CREATE A NEW SUBJECT AND STORE IN DATABASE
			if (!this.isDatabaseWriteable()) {
				return null;
			}
			if (content[0] instanceof ArrayList<?>) {
				ContentValues newSubject = new ContentValues();
				newSubject.put(mSQLiteHelper.SUBJECT_NAME, content[0].get(0));
				int resultAddSubject = (int) database.insert(
						mSQLiteHelper.TABLE_SUBJECT, null, newSubject);

				ArrayList<HashMap<String, String>> addSubject = new ArrayList<HashMap<String, String>>();
				HashMap<String, String> addSubjectSuccess = new HashMap<String, String>();
				if (resultAddSubject == -1) {
					addSubjectSuccess.put(KEY_FAILURE, "failure");
				} else {
					addSubjectSuccess.put(KEY_SUCCESS, "success");
				}
				addSubject.add(addSubjectSuccess);
				return addSubject;
			} else {
				throw new InvalidParameterException(
						"content must be implement Subject Name");
			}
		case ACTION_GET_STAGE:
			// RETURN STAGE'S ATTRIBUTE BY GIVING STAGE_ID
			ArrayList<HashMap<String, String>> stage = new ArrayList<HashMap<String, String>>();
			if (content[0] instanceof ArrayList<?>) {
				int stageID = Integer.parseInt(content[0].get(0));
				Cursor cursorStage = database.query(mSQLiteHelper.TABLE_STAGE,
						allColumnsStage, mSQLiteHelper.STAGE_ID + " = "
								+ stageID, null, null, null, null);
				cursorStage.moveToFirst();
				while (!cursorStage.isAfterLast()) {
					stage.add(cursorToStage(cursorStage));
					cursorStage.moveToNext();
				}
				cursorStage.close();
				return stage;
			} else {
				throw new InvalidParameterException(
						"content must be implement Stage ID");
			}

		case ACTION_GET_STAGE_DETAILS:
			// RETURN LIST ITEM IN A STAGE
			ArrayList<HashMap<String, String>> stageDetails = new ArrayList<HashMap<String, String>>();
			ArrayList<Integer> listItemInStage = new ArrayList<Integer>();
			if (content[0] instanceof ArrayList<?>) {
				int stageID = Integer.parseInt(content[0].get(0));
				Cursor cursorStageDetails = database.query(
						mSQLiteHelper.TABLE_STAGE_DETAIL,
						allColumnsStageDetail, mSQLiteHelper.STAGE_ID + " = "
								+ stageID, null, null, null, null);
				cursorStageDetails.moveToFirst();
				while (!cursorStageDetails.isAfterLast()) {
					listItemInStage.add((int) cursorStageDetails.getLong(1));
					cursorStageDetails.moveToNext();
				}
				cursorStageDetails.close();

				for (Iterator<Integer> iterator = listItemInStage.iterator(); iterator
						.hasNext();) {
					Integer itemID = iterator.next();
					Cursor cursorItemInStage = database.query(
							mSQLiteHelper.TABLE_ITEM, allColumnsItem,
							mSQLiteHelper.ITEM_ID + " = " + itemID, null, null,
							null, null);
					cursorItemInStage.moveToFirst();
					if (cursorItemInStage.isFirst()) {
						stageDetails.add(cursorToItem(cursorItemInStage));
					}
				}
				return stageDetails;
			} else {
				throw new InvalidParameterException(
						"content must be implement Stage ID");
			}
		case ACTION_GET_ALL_STAGE:
			// RETURN ALL STAGE IN DATABASE
			ArrayList<HashMap<String, String>> listStage = new ArrayList<HashMap<String, String>>();
			if (content[0] instanceof ArrayList<?>) {
				int subjectID = Integer.parseInt(content[0].get(0));
				Cursor cursorListStage = database.query(
						mSQLiteHelper.TABLE_STAGE, allColumnsStage,
						mSQLiteHelper.SUBJECT_ID + " = " + subjectID, null,
						null, null, null);
				cursorListStage.moveToFirst();
				while (!cursorListStage.isAfterLast()) {
					listStage.add(cursorToStage(cursorListStage));
					cursorListStage.moveToNext();
				}
				cursorListStage.close();
				return listStage;
			} else {
				throw new InvalidParameterException(
						"content must be implement Subject ID");
			}
		case ACTION_ADD_NEW_STAGE:
			// CREATE NEW STAGE AND STORE IN DATABASE
			if (!this.isDatabaseWriteable()) {
				return null;
			}
			if (content[0] instanceof ArrayList<?>) {
				ContentValues newStage = new ContentValues();
				newStage.put(mSQLiteHelper.SUBJECT_ID, content[0].get(0));
				newStage.put(mSQLiteHelper.STAGE_NAME, content[0].get(1));
				newStage.put(mSQLiteHelper.STAGE_NUMBER, content[0].get(2));
				newStage.put(mSQLiteHelper.STAGE_STATUS, 0 + "");
				int resultInsertStage = (int) database.insert(
						mSQLiteHelper.TABLE_STAGE, null, newStage);
				ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
				HashMap<String, String> addingStageResult = new HashMap<String, String>();
				if (resultInsertStage == -1) {
					addingStageResult.put(KEY_FAILURE, "failure");
				} else {
					for (int i = 4; i < content[0].size(); i++) {
						int itemID = Integer.parseInt(content[0].get(i));
						ContentValues newStageDetails = new ContentValues();
						newStageDetails.put(mSQLiteHelper.STAGE_ID,
								resultInsertStage);
						newStageDetails.put(mSQLiteHelper.ITEM_ID, itemID);
						int resultInsertStageDetail = (int) database.insert(
								mSQLiteHelper.TABLE_STAGE_DETAIL, null,
								newStageDetails);
						if (resultInsertStageDetail == -1) {
							addingStageResult.put(KEY_DUPLICATE,
									"duplicate stage detail");
						}
					}
					addingStageResult.put(KEY_SUCCESS, "success");
				}

				result.add(addingStageResult);
				return result;
			} else {
				throw new InvalidParameterException(
						"content must be implement Subject Name");
			}
		case ACTION_GET_ITEM:
			// RETURN ITEM'S ATTRIBUTE BY GIVING ITEM_ID
			ArrayList<HashMap<String, String>> item = new ArrayList<HashMap<String, String>>();
			if (content[0] instanceof ArrayList<?>) {
				int itemID = Integer.parseInt(content[0].get(0));
				Cursor cursorItem = database.query(mSQLiteHelper.TABLE_ITEM,
						allColumnsItem, mSQLiteHelper.ITEM_ID + " = " + itemID,
						null, null, null, null);
				cursorItem.moveToFirst();
				while (!cursorItem.isAfterLast()) {
					item.add(cursorToStage(cursorItem));
					cursorItem.moveToNext();
				}
				cursorItem.close();
				return item;
			} else {
				throw new InvalidParameterException(
						"content must be implement ITEM ID");
			}
		case ACTION_GET_ALL_ITEM:
			// RETURN ALL ITEM WHICH BELONG TO A SUBJECT
			ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
			if (content[0] instanceof ArrayList<?>) {
				int subjectID = Integer.parseInt(content[0].get(0));
				Cursor cursorItem = database.query(mSQLiteHelper.TABLE_ITEM,
						allColumnsItem, mSQLiteHelper.SUBJECT_ID + " = "
								+ subjectID, null, null, null, null);
				cursorItem.moveToFirst();
				while (!cursorItem.isAfterLast()) {
					listItem.add(cursorToItem(cursorItem));
					cursorItem.moveToNext();
				}
				cursorItem.close();
				return listItem;
			} else {
				throw new InvalidParameterException(
						"content must be implement Subject ID");
			}
		case ACTION_ADD_NEW_ITEM:
			// CREATE NEW ITEM AND ADD TO DATABASE
			if (!this.isDatabaseWriteable()) {
				return null;
			}
			if (content[0] instanceof ArrayList<?>) {
				ContentValues newItem = new ContentValues();
				newItem.put(mSQLiteHelper.SUBJECT_ID,
						Integer.parseInt(content[0].get(0) + ""));
				newItem.put(mSQLiteHelper.ITEM_NAME, content[0].get(1));
				newItem.put(mSQLiteHelper.ITEM_IMG_LINK, content[0].get(2));
				newItem.put(mSQLiteHelper.ITEM_AUDIO_LINK, content[0].get(3));
				newItem.put(mSQLiteHelper.ITEM_CORRECT_ANSWER, 0 + "");
				newItem.put(mSQLiteHelper.ITEM_WRONG_ANSWER, 0 + "");
				int Addingresult = (int) database.insert(
						mSQLiteHelper.TABLE_ITEM, null, newItem);

				ArrayList<HashMap<String, String>> addingItemResult = new ArrayList<HashMap<String, String>>();
				HashMap<String, String> hashmap = new HashMap<String, String>();
				if (Addingresult == -1) {
					hashmap.put(KEY_FAILURE, "failure");
				} else {
					hashmap.put(KEY_SUCCESS, "success");
				}
				addingItemResult.add(hashmap);
				return addingItemResult;
			} else {
				throw new InvalidParameterException(
						"content must be implement Subject Name");
			}
		case ACTION_ADD_ITEM_ANSWER:
			// AFTER USER ANSWER A INCREASE ANSWER
			if (!this.isDatabaseWriteable()) {
				return null;
			}
			if (content[0] instanceof ArrayList<?>) {
				ArrayList<HashMap<String, String>> updateItemResult = new ArrayList<HashMap<String, String>>();
				HashMap<String, String> hashmap = new HashMap<String, String>();
				int itemID = Integer.parseInt(content[0].get(0));
				String answer = content[0].get(1);
				Cursor cursorItem = database.query(mSQLiteHelper.TABLE_ITEM,
						allColumnsItem, mSQLiteHelper.ITEM_ID + " = " + itemID,
						null, null, null, null);
				cursorItem.moveToFirst();
				if (cursorItem.getCount() != 1) {
					cursorItem.close();
					hashmap.put(KEY_FAILURE, "failure");
				} else {
					ContentValues newValue = new ContentValues();
					newValue = newItemAttr(cursorItem, answer);
					cursorItem.close();
					int updateResult = database.update(
							mSQLiteHelper.TABLE_ITEM, newValue,
							mSQLiteHelper.ITEM_ID + " = " + itemID, null);
					if (updateResult == 1) {
						hashmap.put(KEY_SUCCESS, "success");
					} else {
						hashmap.put(KEY_FAILURE, "failure");
					}
				}
				updateItemResult.add(hashmap);
				return updateItemResult;
			} else {
				throw new InvalidParameterException(
						"content must be implement itemID and answer");
			}
		case ACTION_ADD_STAGE_STATUS:
			// input: stageID + number of correct answer
			if (!this.isDatabaseWriteable()) {
				return null;
			}
			if (content[0] instanceof ArrayList<?>) {
				ArrayList<HashMap<String, String>> updateStageResult = new ArrayList<HashMap<String, String>>();
				HashMap<String, String> hashmap = new HashMap<String, String>();
				int stageID = Integer.parseInt(content[0].get(0));
				int answer = Integer.parseInt(content[0].get(1));
				Cursor cursorStage = database.query(mSQLiteHelper.TABLE_STAGE,
						allColumnsStage, mSQLiteHelper.STAGE_ID + " = "
								+ stageID, null, null, null, null);
				cursorStage.moveToFirst();
				if (cursorStage.getCount() != 1) {
					cursorStage.close();
					hashmap.put(KEY_FAILURE, "failure");
				} else {
					ContentValues newValue = new ContentValues();
					newValue = newStageAttr(cursorStage, answer);
					cursorStage.close();
					int updateResult = database.update(
							mSQLiteHelper.TABLE_STAGE, newValue,
							mSQLiteHelper.STAGE_ID + " = " + stageID, null);
					if (updateResult == 1) {
						hashmap.put(KEY_SUCCESS, "success");
					} else {
						hashmap.put(KEY_FAILURE, "failure");
					}
				}
				updateStageResult.add(hashmap);
				return updateStageResult;
			} else {
				throw new InvalidParameterException(
						"content must be implement stageID and number of correct answer");
			}
		default:
			throw new UnsupportedOperationException();
		}

	}

	private boolean isDatabaseOpen() {
		return this.isOpen;
	}

	private boolean isDatabaseWriteable() {
		return !database.isReadOnly();
	}

	private HashMap<String, String> cursorToSubject(Cursor cursor) {
		HashMap<String, String> subject = new HashMap<String, String>();
		subject.put(mSQLiteHelper.SUBJECT_ID, cursor.getLong(0) + "");
		subject.put(mSQLiteHelper.SUBJECT_NAME, cursor.getString(1) + "");
		return subject;
	}

	private HashMap<String, String> cursorToStage(Cursor cursor) {
		HashMap<String, String> stage = new HashMap<String, String>();
		stage.put(mSQLiteHelper.STAGE_ID, cursor.getLong(0) + "");
		stage.put(mSQLiteHelper.SUBJECT_ID, cursor.getLong(1) + "");
		stage.put(mSQLiteHelper.STAGE_NAME, cursor.getString(2) + "");
		stage.put(mSQLiteHelper.STAGE_NUMBER, cursor.getString(3) + "");
		stage.put(mSQLiteHelper.STAGE_STATUS, cursor.getLong(4) + "");
		return stage;
	}

	private HashMap<String, String> cursorToItem(Cursor cursor) {
		HashMap<String, String> item = new HashMap<String, String>();
		item.put(mSQLiteHelper.ITEM_ID, cursor.getLong(0) + "");
		item.put(mSQLiteHelper.SUBJECT_ID, cursor.getLong(1) + "");
		item.put(mSQLiteHelper.ITEM_NAME, cursor.getString(2) + "");
		item.put(mSQLiteHelper.ITEM_IMG_LINK, cursor.getString(3) + "");
		item.put(mSQLiteHelper.ITEM_AUDIO_LINK, cursor.getString(4) + "");
		item.put(mSQLiteHelper.ITEM_CORRECT_ANSWER, cursor.getString(5) + "");
		item.put(mSQLiteHelper.ITEM_WRONG_ANSWER, cursor.getString(6) + "");
		return item;
	}

	/**
	 * @author 4-A bui trung hieu. Add default data (items, stages). Raw data
	 *         store in assets/.Run only on first run.
	 * @return true if success, false if otherwise.
	 */
	public boolean setDefaultData() {
		ArrayList<HashMap<String, String>> allSubject = new ArrayList<HashMap<String, String>>();
		allSubject = query(ACTION_GET_ALL_SUBJECT);
		// check if is first run. Default value insert only on first run.
		if (allSubject == null || allSubject.size() == 0) {
			// insert all default object
			for (int i = 0; i < DEFAUT_VALUE.length; i++) {
				ArrayList<String> newSubject = new ArrayList<String>();
				newSubject.add(DEFAUT_VALUE[i][0]);
				Log.e("newSubject", newSubject.get(0).toString());
				try {
					query(ACTION_ADD_NEW_SUBJECT, newSubject);
					// insert items to objects.
					for (int j = 1; j < DEFAUT_VALUE[i].length; j++) {
						ArrayList<String> newItem = new ArrayList<String>();
						newItem.add(i + 1 + ""); // subjectID
						newItem.add(DEFAUT_VALUE[i][j]); // description
						String image = DEFAUT_VALUE[i][j];
						image = "image/" + image.replaceAll(" ", "_") + ".png";
						Log.e("image link", image);
						newItem.add(image); // image path
						newItem.add(""); // no audio
						Log.e("newItems", newItem.toString());
						query(ACTION_ADD_NEW_ITEM, newItem);
					}

				} catch (Exception e) {
					Log.e("DATABASE ERROR", "Cannot insert "
							+ DEFAUT_VALUE[i][0] + "subject to database.");
				}
			}
			// insert default stage
			for (int i = 0; i < DEFAULT_STAGE.length; i++) {
				ArrayList<String> newStage = new ArrayList<String>();
				newStage.add(DEFAULT_STAGE[i][0]);// subjectID
				newStage.add(DEFAULT_STAGE[i][1]);// name
				newStage.add(DEFAULT_STAGE[i][2]);// number
				newStage.add(DEFAULT_STAGE[i][3]);// status
				newStage.add(DEFAULT_STAGE[i][4]);// item 1
				newStage.add(DEFAULT_STAGE[i][5]);// item 2
				newStage.add(DEFAULT_STAGE[i][6]);// item 3
				newStage.add(DEFAULT_STAGE[i][7]);// item 4
				newStage.add(DEFAULT_STAGE[i][8]);// item 5

				query(ACTION_ADD_NEW_STAGE, newStage);
			}
		}
		return true;
	}

	/**
	 * @author zendbui
	 * @author 4-B Bui Trong Hieu
	 * @param cursor
	 * @param answer
	 * @return contenValue that will be updated to database
	 */
	ContentValues newItemAttr(Cursor cursor, String answer) {
		ContentValues item = new ContentValues();
		int correct_answer = 0;
		int wrong_answer = 0;
		item.put(mSQLiteHelper.SUBJECT_ID, cursor.getLong(1) + "");
		item.put(mSQLiteHelper.ITEM_NAME, cursor.getString(2) + "");
		item.put(mSQLiteHelper.ITEM_IMG_LINK, cursor.getString(3) + "");
		item.put(mSQLiteHelper.ITEM_AUDIO_LINK, cursor.getString(4) + "");
		correct_answer = Integer.parseInt(cursor.getString(5));
		wrong_answer = Integer.parseInt(cursor.getString(6));
		if (answer.equalsIgnoreCase("true")) {
			correct_answer += 1;
		} else {
			wrong_answer += 1;
		}
		item.put(mSQLiteHelper.ITEM_CORRECT_ANSWER, correct_answer + "");
		item.put(mSQLiteHelper.ITEM_WRONG_ANSWER, wrong_answer + "");
		return item;
	}

	/**
	 * @author zendbui
	 * @author 4-B Bui Trong HIeu
	 * @param cursor
	 * @param correctAnswer
	 * @return contenValue that will be updated to database
	 */
	ContentValues newStageAttr(Cursor cursor, int correctAnswer) {
		ContentValues stage = new ContentValues();
		int currentCorrectAnswer = 0;
		stage.put(mSQLiteHelper.STAGE_NAME, cursor.getLong(1) + "");
		stage.put(mSQLiteHelper.STAGE_NUMBER, cursor.getString(2) + "");
		currentCorrectAnswer = Integer.parseInt(cursor.getString(3));
		if (currentCorrectAnswer < correctAnswer) {
			currentCorrectAnswer += 1;
		} else {
		}
		stage.put(mSQLiteHelper.STAGE_STATUS, currentCorrectAnswer + "");
		return stage;
	}

}
