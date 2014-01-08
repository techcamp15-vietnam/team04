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

	public static final String KEY_SUCCESS = "success";
	public static final String KEY_FAILURE = "failure";
	public static final String KEY_DUPLICATE = "duplicate";

	private String[] allColumnsSubject = { mSQLiteHelper.SUBJECT_ID,
			mSQLiteHelper.SUBJECT_NAME };
	private String[] allColumnsStage = { mSQLiteHelper.STAGE_ID,
			mSQLiteHelper.SUBJECT_ID, mSQLiteHelper.STAGE_NAME,
			mSQLiteHelper.STAGE_NUMBER };
	private String[] allColumnsItem = { mSQLiteHelper.ITEM_ID,
			mSQLiteHelper.SUBJECT_ID, mSQLiteHelper.ITEM_NAME,
			mSQLiteHelper.ITEM_IMG_LINK, mSQLiteHelper.ITEM_AUDIO_LINK };
	private String[] allColumnsStageDetail = { mSQLiteHelper.STAGE_ID,
			mSQLiteHelper.ITEM_ID };

	private static final String[][] DEFAUT_VALUE = {
			{ "Home", "chair", "fan", "fork", "knife", "pressure cooker",
					"rice cooker", "shoe", "socket", "table", "tv", "bag",
					"bowl", "cup", "door", "dvd player", "remote", "water can",
					"lock" }, { "Animal" }, { "Car" }, { "City" },
			{ "Clothes" }, { "Color" }, { "Plant" }, { "School" } };

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
	 * @param content
	 * @return
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

				int resultInsertStage = (int) database.insert(
						mSQLiteHelper.TABLE_STAGE, null, newStage);
				ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
				HashMap<String, String> addingStageResult = new HashMap<String, String>();
				if (resultInsertStage == -1) {
					addingStageResult.put(KEY_FAILURE, "failure");
				} else {
					for (int i = 3; i < content[0].size(); i++) {
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
		return stage;
	}

	private HashMap<String, String> cursorToItem(Cursor cursor) {
		HashMap<String, String> item = new HashMap<String, String>();
		item.put(mSQLiteHelper.ITEM_ID, cursor.getLong(0) + "");
		item.put(mSQLiteHelper.SUBJECT_ID, cursor.getLong(1) + "");
		item.put(mSQLiteHelper.ITEM_NAME, cursor.getString(2) + "");
		item.put(mSQLiteHelper.ITEM_IMG_LINK, cursor.getString(3) + "");
		item.put(mSQLiteHelper.ITEM_AUDIO_LINK, cursor.getString(4) + "");
		return item;
	}

	/**
	 * @author 4-A trung hieu. Add default data (items, stages). Raw data store
	 *         in assets/
	 * @return true if success, false if otherwise.
	 */
	public boolean setDefaultData() {
		// insert all default object
		ArrayList<HashMap<String, String>> allSubject = new ArrayList<HashMap<String, String>>();
		allSubject = query(ACTION_GET_ALL_SUBJECT);
		if (allSubject == null || allSubject.size() == 0) {
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
		}
		return true;

	}

}
