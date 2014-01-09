package vn.techcamp.team04.grownmeup.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import vn.techcamp.team04.grownmeup.database.Database;
import android.content.Context;

/**
 * @author zendbui
 * @author 4-B Bui Trong Hieu
 */
public class mRandomItem {
	private Context context;

	public mRandomItem(Context context) {
		this.context = context;

	}

	/**
	 * @param subjectID
	 * @param itemID
	 * @return such as [1,1,3,7,2] - first value: position of correct answer -
	 *         next: id of item in quiz
	 */
	public ArrayList<String> random(int subjectID, int itemID) {
		ArrayList<String> result = new ArrayList<String>();
		Database db = new Database(this.context);
		ArrayList<String> subID = new ArrayList<String>();
		subID.add(subjectID + "");
		ArrayList<HashMap<String, String>> allItem = db.query(
				Database.ACTION_GET_ALL_ITEM, subID);

		int totalItem = allItem.size();
		Random r = new Random();
		int correctPosition = r.nextInt(4) + 1;
		result.add(correctPosition + "");
		for (int i = 1; i < 5; i++) {
			if (i == correctPosition) {
				result.add(itemID + "");
			} else {
				int posItem;
				do {
					posItem = r.nextInt(totalItem - 1) + 1;
				} while (posItem == itemID || result.contains(posItem+""));
				result.add(posItem + "");
			}
		}
		db.close();
		return result;
	}

}