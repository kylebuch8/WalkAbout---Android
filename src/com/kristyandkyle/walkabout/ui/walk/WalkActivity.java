package com.kristyandkyle.walkabout.ui.walk;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.kristyandkyle.walkabout.R;
import com.kristyandkyle.walkabout.providers.WalkAbout;

public class WalkActivity extends SherlockFragmentActivity implements CancelWalkDialogFragment.CancelWalkDialogListener, LoaderManager.LoaderCallbacks<Cursor> {
	
	private static final int PATH_LIST_LOADER = 0x01;
	private long mPathId;
	private TextView txtTimer;
	private Button btnStartStop;
	private Button btnSave;
	private Handler handler;
	private Runnable runnable;
	private Boolean isRunning = false;
	private long mStartTime = 0;
	private long mPauseTime = 0;
	private long mTotalDuration = 0;
	private int notifyID = 1;

	@Override
	protected void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setContentView(R.layout.walk_activity);
		
		// get the path from the database
		mPathId = getIntent().getLongExtra("pathId", 0);
		getSupportLoaderManager().initLoader(PATH_LIST_LOADER, null, this);
		
		txtTimer = (TextView) findViewById(R.id.walkActivity_txtTimer);
		btnStartStop = (Button) findViewById(R.id.walkActivity_btnStartStop);
		btnStartStop.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (isRunning) {
					stopTimer();
				} else {
					startTimer();
				}
			}
		});
		
		btnSave = (Button) findViewById(R.id.walkActivity_btnSave);		
		btnSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ContentValues values = new ContentValues();
				values.put(WalkAbout.WalkAbouts.PATH_ID, mPathId);
				values.put(WalkAbout.WalkAbouts.DURATION, mTotalDuration);
				values.put(WalkAbout.WalkAbouts.TIMESTAMP, System.currentTimeMillis());
				
				Uri insertUri = getContentResolver().insert(WalkAbout.WalkAbouts.WALKABOUTS_URI, values);
				String walkId = insertUri.getLastPathSegment();
				
				Intent intent = new Intent(getApplicationContext(), WalkCompletedActivity.class);
				intent.putExtra("walkId", walkId);
				startActivity(intent);
			}
		});
		
		runnable = new Runnable() {
			
			@Override
			public void run() {
				final long currentTime = System.currentTimeMillis();
				final long elapsedTime = currentTime + mTotalDuration - mStartTime;
				
				int seconds = (int) (elapsedTime / 1000);
				int minutes = seconds / 60;
				seconds = seconds % 60;
				
				String secondsText = (seconds < 10) ? "0" + seconds : "" + seconds;
				String minutesText = (minutes < 10) ? "0" + minutes : "" + minutes;
				
				txtTimer.setText(minutesText + ":" + secondsText);
				
				// add a delay to adjust for computation time
		        long delay = (1000 - (elapsedTime % 1000));
				
				handler.postDelayed(this, delay);
			}
		};
		
		handler = new Handler();
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	protected void stopTimer() {
		isRunning = false;
		
		mPauseTime = System.currentTimeMillis();
		mTotalDuration = mPauseTime - mStartTime + mTotalDuration;
		
		handler.removeCallbacks(runnable);
		
		btnStartStop.setText("Start Walking");
		btnSave.setVisibility(View.VISIBLE);
		
		hideNotification();
	}

	protected void startTimer() {
		isRunning = true;
		
		mStartTime = System.currentTimeMillis();
		handler.post(runnable);
		
		btnStartStop.setText("Stop Walking");
		btnSave.setVisibility(View.INVISIBLE);
		
		showNotification();
	}
	
	private void showNotification() {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
			.setSmallIcon(R.drawable.ic_menu_share)
			.setContentTitle("WalkAbout")
			.setContentText("You're Walking!!!")
			.setTicker("Beginning WalkAbout...")
			.setOngoing(true);
		
		Intent resultIntent = new Intent(this, WalkActivity.class);
		
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addNextIntent(resultIntent);
		
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		
		mBuilder.setContentIntent(resultPendingIntent);
		
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(notifyID, mBuilder.build());
	}
	
	private void hideNotification() {
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.cancel(notifyID);
	}

	@Override
	public void onBackPressed() {
		if (isRunning) {
			DialogFragment cancelWalkDialog = new CancelWalkDialogFragment();
			cancelWalkDialog.show(getSupportFragmentManager(), "cancel");
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				if (isRunning) {
					DialogFragment cancelWalkDialog = new CancelWalkDialogFragment();
					cancelWalkDialog.show(getSupportFragmentManager(), "cancel");
				} else {
					finish();
				}
				break;
	
			default:
				break;
		}
	
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		stopTimer();
		finish();
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// do nothing
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Uri uri = Uri.parse(WalkAbout.Paths.PATHS_URI + "/" + mPathId);
		CursorLoader cursorLoader = new CursorLoader(this, uri, null, null, null, null);
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		cursor.moveToPosition(0);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		
	}

}
