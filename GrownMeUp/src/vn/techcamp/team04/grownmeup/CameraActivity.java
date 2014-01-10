package vn.techcamp.team04.grownmeup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.techcamp.team04.grownmeup.utility.CameraCropOption;
import vn.techcamp.team04.grownmeup.utility.CameraCropOptionAdapter;
import vn.techcamp.team04.grownmeup.utility.Utility;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * @author hiepns
 * 
 */
public class CameraActivity extends Activity implements SurfaceHolder.Callback {

	private static final String TAG = "CameraActivity";

	public static final int PICK_FROM_CAMERA = 1;
	public static final int CROP_FROM_CAMERA = 2;
	public static final int PICK_FROM_FILE = 3;
	public static final int EDIT_CROPPED_PHOTO = 4;

	private Camera camera;
	private boolean isPreviwRunning = false;
	private SimpleDateFormat timeStampFormat = new SimpleDateFormat(
			"yyyyMMddHHmmssSS");
	private SurfaceView surfaceView;
	private SurfaceHolder surfaceHolder;
	private Uri targetResource = Media.EXTERNAL_CONTENT_URI;
	private Uri pictureUri;
	private ImageButton cameraBtn;
	private ImageButton saveBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);

		setContentView(R.layout.camera_layout);
		surfaceView = (SurfaceView) findViewById(R.id.camera_surface);
		cameraBtn = (ImageButton) findViewById(R.id.btn_capture);
		saveBtn = (ImageButton) findViewById(R.id.btn_save);
		saveBtn.setVisibility(View.GONE);
		Log.d(TAG, "got surface view");
		surfaceHolder = surfaceView.getHolder();
		Log.d(TAG, "got surface holder");
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		Log.d(TAG, "surface holder type was set");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		Log.d(TAG, "onRestoreInstanceState");
		super.onRestoreInstanceState(savedInstanceState);
	};

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		Log.d(TAG, "onSaveInstanceState");
		super.onSaveInstanceState(outState);
	};

	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		Log.d(TAG, "onKeyDown");
		if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			Log.d(TAG, "prepare to take picture");
			takePicture();
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Log.d(TAG, "prepare save result");
			saveResult(RESULT_CANCELED);
			return super.onKeyDown(keyCode, event);
		}
		return false;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:

			break;
		case R.id.btn_capture:
			takePicture();
			break;
		case R.id.btn_gallary:
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);

			startActivityForResult(
					Intent.createChooser(intent, "Complete action using"),
					PICK_FROM_FILE);
			break;
		case R.id.btn_recapture:
			camera.startPreview();
			cameraBtn.setVisibility(View.VISIBLE);
			saveBtn.setVisibility(View.GONE);
			break;
		case R.id.btn_save:
			saveBtn.setClickable(false);
			mhandler.sendEmptyMessageDelayed(1, 3000);
			if (pictureUri != null)
				doCrop();
			break;
		default:
			break;
		}
	}

	Handler mhandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			saveBtn.setClickable(true);
		}

	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		cameraBtn.setVisibility(View.VISIBLE);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case PICK_FROM_CAMERA:
				doCrop();
				// File delFile = new File(mImageCaptureUri.getPath());
				// deleteDirectory(delFile);
				break;
			case PICK_FROM_FILE:
				pictureUri = data.getData();
				doCrop();
				break;
			case CROP_FROM_CAMERA: {
				Bitmap photo = data.getExtras().getParcelable("data");
				String imagePath = saveImageToExternalStorage(photo);
				Intent returnIntent = new Intent();
				returnIntent.putExtra("imagePath", imagePath);
				setResult(RESULT_OK, returnIntent);
				Log.i("CROP_FROM_CAMERA", "CROP_FROM_CAMERA");

				finish();
			}
				break;
			case EDIT_CROPPED_PHOTO: {
				Bitmap photo = data.getExtras().getParcelable("data");
				String imagePath = saveImageToExternalStorage(photo);
				Intent returnIntent = new Intent();
				returnIntent.putExtra("imagePath", imagePath);
				setResult(RESULT_OK, returnIntent);

				finish();
			}
				break;
			}
		} else if ((resultCode == RESULT_CANCELED)
				&& (requestCode == EDIT_CROPPED_PHOTO)) {
			doCrop();
		}
	}

	private void doCrop() {
		final ArrayList<CameraCropOption> cropOptions = new ArrayList<CameraCropOption>();

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setType("image/*");

		List<ResolveInfo> list = getPackageManager().queryIntentActivities(
				intent, 0);

		int size = list.size();

		if (size == 0) {
			Toast.makeText(this, "Can not find image crop app",
					Toast.LENGTH_SHORT).show();
			return;
		} else {
			intent.setData(pictureUri);

			intent.putExtra("outputX", 300);
			intent.putExtra("outputY", 300);
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);

			if (size == 1) {
				Intent i = new Intent(intent);
				ResolveInfo res = list.get(0);

				i.setComponent(new ComponentName(res.activityInfo.packageName,
						res.activityInfo.name));
				startActivityForResult(i, CROP_FROM_CAMERA);
			} else {
				for (ResolveInfo res : list) {
					final CameraCropOption co = new CameraCropOption();

					co.title = getPackageManager().getApplicationLabel(
							res.activityInfo.applicationInfo);
					co.icon = getPackageManager().getApplicationIcon(
							res.activityInfo.applicationInfo);
					co.appIntent = new Intent(intent);
					co.appIntent
							.setComponent(new ComponentName(
									res.activityInfo.packageName,
									res.activityInfo.name));
					cropOptions.add(co);
				}
				CameraCropOptionAdapter adapter = new CameraCropOptionAdapter(
						getApplicationContext(), cropOptions);

				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Choose Crop App");
				builder.setAdapter(adapter,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int item) {
								startActivityForResult(
										cropOptions.get(item).appIntent,
										CROP_FROM_CAMERA);
							}
						});

				builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						if (pictureUri != null) {
							getContentResolver().delete(pictureUri, null, null);
							pictureUri = null;
						}
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
		}
	}

	@Override
	protected void onResume() {
		Log.d(TAG, "OnResume()");
		super.onResume();
	};

	@Override
	protected void onStop() {
		Log.d(TAG, "onStop()");
		super.onStop();
	};

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.d(TAG, "surfaceChanged()");
		if (isPreviwRunning) {
			Log.d(TAG, "preview is running and need to be stopped");
			camera.stopPreview();
			Log.d(TAG, "preview is stopped");
		}
		Log.d(TAG, "prepare to set camera parameters");
		Camera.Parameters p = camera.getParameters();
		Log.d(TAG, "got old parameters");
		p.setPreviewSize(width, height);
		Log.d(TAG, "set width, height");
		// camera.setParameters(p);
		Log.d(TAG, "set camera parameters");
		try {
			camera.setPreviewDisplay(holder);
			Log.d(TAG, "set camera preview display");
		} catch (Exception ex) {
			Log.e(TAG, "surfaceChanged exception caught: " + ex);
		}
		camera.startPreview();
		isPreviwRunning = true;
		Log.d(TAG, "camera preview is started");
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "surfaceCreated()");
		camera = Camera.open();
		Log.d(TAG, "camera opened");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "surfaceDestroyed()");
		camera.stopPreview();
		Log.d(TAG, "camera preview is stopped");
		isPreviwRunning = false;
		camera.release();
		Log.d(TAG, "camera released");
	}

	private void takePicture() {
		Log.d(TAG, "takePicture()");
		try {
			pictureUri = prepareImageFile(timeStampFormat.format(new Date()),
					"Android Camera Image");
			Log.d(TAG, "picture uri: " + pictureUri.getPath());

			// Camera.Parameters cp = camera.getParameters();
			//
			// List<Size> sl = cp.getSupportedPictureSizes();
			//
			// int w = 600, h = 600;
			// for (Size s : sl) {
			// w = s.width;
			// h = s.height;
			// break;
			//
			// }
			//
			// cp.setPictureSize(w, h);
			//
			// camera.setParameters(cp);

			camera.takePicture(mShutterCallback, mPictureCallbackRaw,
					mPictureCallbackJpeg);
		} catch (Exception ex) {
			Log.e(TAG, "takePicture() exception caught: " + ex);
			saveResult(RESULT_CANCELED);
		}
	};

	private void saveResult(int resultCode) {
		Log.d(TAG, "saveResult()");
		if (resultCode == RESULT_OK) {
			/*
			 * Log.d(TAG, "RESULT_OK"); Intent pictureTakenIntent = new
			 * Intent(); pictureTakenIntent.setData(pictureUri); Log.d(TAG,
			 * "data was set"); setResult(resultCode, pictureTakenIntent);
			 * Log.d(TAG, "result is set");
			 */

		} else {
			Log.d(TAG, "resultCode != RESULT_OK");
			// setResult(resultCode);
			pictureUri = null;
		}
		Log.d(TAG, "prepare to finish activity");
		// finish();
		// overridePendingTransition(R.anim.diagslide_enter,
		// R.anim.diagslide_leave);
	}

	private Uri prepareImageFile(String imageFileName, String imageDescription) {
		Log.d(TAG, "prepared file name: " + imageFileName);
		ContentValues values = new ContentValues();
		values.put(MediaColumns.TITLE, imageFileName);
		if (imageDescription != null) {
			values.put(ImageColumns.DESCRIPTION, "Android Camera Image");
		}
		Log.d(TAG, "prepared values");
		return getContentResolver().insert(targetResource, values);
	}

	Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {
		@Override
		public void onShutter() {
			AudioManager mgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
			mgr.playSoundEffect(AudioManager.FLAG_PLAY_SOUND);
		}
	};

	Camera.PictureCallback mPictureCallbackRaw = new Camera.PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.d(TAG, "Picture taken, data = " + data);
			Log.d(TAG, "Prepare to start camera preview");
			cameraBtn.setVisibility(View.GONE);
			saveBtn.setVisibility(View.VISIBLE);
			// camera.startPreview();
		}
	};

	Camera.PictureCallback mPictureCallbackJpeg = new Camera.PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.d(TAG, "onPictureTaken()");
			try {
				Log.d(TAG, "prepare to open fileOutputStream");
				OutputStream fileOutputStream = getContentResolver()
						.openOutputStream(pictureUri);
				Options option = new Options();
				option.inSampleSize = 6;
				Bitmap btm = BitmapFactory.decodeByteArray(data, 0,
						data.length, option);

				if (btm.getWidth() > btm.getHeight()) {
					Matrix mtx = new Matrix();
					mtx.setRotate(90);
					btm = Bitmap.createBitmap(btm, 0, 0, btm.getWidth(),
							btm.getHeight(), mtx, true);
				}
				btm.compress(CompressFormat.JPEG, 50, fileOutputStream);
				fileOutputStream.close();
			} catch (Exception e) {
				Log.e(TAG, "mPictureCallbackJpeg exception caught: " + e);
			}
		}
	};

	public String saveImageToExternalStorage(Bitmap image) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String currentDateandTime = sdf.format(new Date());
		String fullPath = Utility
				.getCustomItemsImageFilePath(currentDateandTime + ".png");
		try {
			OutputStream fOut = null;
			File file = new File(fullPath);

			if (file.exists())
				file.delete();

			file.createNewFile();
			fOut = new FileOutputStream(file);
			// 100 means no compression, the lower you go, the stronger the
			// compression
			image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			fOut.flush();
			fOut.close();
		} catch (Exception e) {
			Log.e("saveToExternalStorage()", e.getMessage());
		}
		return fullPath;
	}

}