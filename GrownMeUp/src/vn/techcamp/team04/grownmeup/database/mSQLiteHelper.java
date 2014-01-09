package vn.techcamp.team04.grownmeup.database;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author buihieu
 * 
 */
public class mSQLiteHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "QUIZ_DATABASE";
	private static final int DATABASE_VERSION = 1;

	/**
	 * List table in database
	 */
	public static final String TABLE_SUBJECT = "subject";
	public static final String TABLE_STAGE = "stage";
	public static final String TABLE_ITEM = "item";
	public static final String TABLE_STAGE_DETAIL = "stage_detail";

	/**
	 * List row in database
	 */
	public static final String SUBJECT_ID = "subject_id";
	public static final String SUBJECT_NAME = "subject_name";
	public static final String STAGE_ID = "stage_id";
	public static final String STAGE_NAME = "stage_name";
	public static final String STAGE_NUMBER = "stage_number";
	public static final String STAGE_STATUS = "stage_status";
	public static final String ITEM_ID = "item_id";
	public static final String ITEM_NAME = "item_name";
	public static final String ITEM_IMG_LINK = "item_img_link";
	public static final String ITEM_AUDIO_LINK = "item_audio_link";
	public static final String ITEM_CORRECT_ANSWER = "item_correct_answer";
	public static final String ITEM_WRONG_ANSWER = "item_wrong_answer";

	/**
	 * Creating database statement
	 */
	private static final String CREATE_TABLE_SUBJECT = "CREATE TABLE "
			+ TABLE_SUBJECT + " (" + SUBJECT_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + SUBJECT_NAME
			+ " VARCHAR " + ");";

	private static final String CREATE_TABLE_STAGE = "CREATE TABLE "
			+ TABLE_STAGE + " (" + STAGE_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + SUBJECT_ID
			+ " INTEGER, " + STAGE_NAME + " VARCHAR, " + STAGE_NUMBER
			+ " INTEGER, " + STAGE_STATUS + " INTEGER " + ");";

	private static final String CREATE_TABLE_ITEM = "CREATE TABLE "
			+ TABLE_ITEM + " (" + ITEM_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + SUBJECT_ID
			+ " INTEGER, " + ITEM_NAME + " VARCHAR, " + ITEM_IMG_LINK
			+ " VARCHAR, " + ITEM_AUDIO_LINK + " VARCHAR, "
			+ ITEM_CORRECT_ANSWER + "INTEGER, " + ITEM_WRONG_ANSWER
			+ "INTEGER " + ");";

	private static final String CREATE_TABLE_STAGE_DETAIL = "CREATE TABLE "
			+ TABLE_STAGE_DETAIL + " (" + STAGE_ID + " INTEGER, " + ITEM_ID
			+ " INTEGER, " + "PRIMARY KEY " + "(" + STAGE_ID + ", " + ITEM_ID
			+ ")" + ");";

	public mSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SUBJECT);
		db.execSQL(CREATE_TABLE_STAGE);
		db.execSQL(CREATE_TABLE_ITEM);
		db.execSQL(CREATE_TABLE_STAGE_DETAIL);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_SUBJECT);
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_STAGE);
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_ITEM);
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_STAGE_DETAIL);
		onCreate(db);

	}
}
