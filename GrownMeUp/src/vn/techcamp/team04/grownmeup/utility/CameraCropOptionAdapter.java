package vn.techcamp.team04.grownmeup.utility;

import java.util.ArrayList;

import vn.techcamp.team04.grownmeup.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class CameraCropOptionAdapter extends ArrayAdapter<CameraCropOption> {
	private ArrayList<CameraCropOption> mOptions;
	private LayoutInflater mInflater;

	public CameraCropOptionAdapter(Context context,
			ArrayList<CameraCropOption> options) {
		super(context, R.layout.camera_crop_selector, options);

		mOptions = options;

		mInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		if (convertView == null)
			convertView = mInflater
					.inflate(R.layout.camera_crop_selector, null);

		CameraCropOption item = mOptions.get(position);

		if (item != null) {
			((ImageView) convertView.findViewById(R.id.iv_icon))
					.setImageDrawable(item.icon);
			return convertView;
		}

		return null;
	}
}