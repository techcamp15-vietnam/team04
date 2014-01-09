package vn.techcamp.team04.grownmeup.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import vn.techcamp.team04.grownmeup.database.Database;
import android.content.Context;

public class mRandomItem {
	private Context context;
	private Database db;

	public mRandomItem(Context context) {
		this.context = context;
		db = new Database(this.context);
	}

	public ArrayList<String> random(int subjectID, int itemID) {
		ArrayList<String> name = new ArrayList<String>();
		ArrayList<String> subID = new ArrayList<String>();
		subID.add(subjectID + "");
		ArrayList<HashMap<String, String>> allItem = db.query(
				Database.ACTION_GET_ALL_ITEM, subID);

		int totalItem = allItem.size();
		Random r = new Random();
		int correctPosition = r.nextInt(4) + 1;
		name.add(correctPosition + "");
		for (int i = 1; i < 5; i++) {
			if (i == correctPosition) {
				name.add(itemID + "");
			} else {
				int posItem;
				do {
					posItem = r.nextInt(totalItem - 1) + 1;
				} while (posItem == itemID);
				name.add(posItem + "");
			}
		}
		return name;
	}
	public void close(){
		db.close();
	}
}