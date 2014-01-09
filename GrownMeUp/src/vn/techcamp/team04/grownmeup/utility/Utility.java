package vn.techcamp.team04.grownmeup.utility;

import android.os.Environment;

public class Utility {
	public static final String CUSTOM_ITEMS_IMAGE_DIRECTORY = "/GrowMeUp/items/images";
	public static final String CUSTOM_ITEMS_SOUND_DIRECTORY = "/GrowMeUp/items/sounds";

	public static String getCustomItemsImageFilePath(String filename) {
		return Environment.getExternalStorageDirectory().getPath()
				+ CUSTOM_ITEMS_IMAGE_DIRECTORY + "/" + filename;
	}

	public static String getCusTomItemsSoundFilePath(String filename) {
		return Environment.getExternalStorageDirectory().getPath()
				+ CUSTOM_ITEMS_SOUND_DIRECTORY + "/" + filename;
	}
}
