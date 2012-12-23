package com.kristyandkyle.walkabout.ui.walk;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.kristyandkyle.walkabout.R;

public class WalkActivity extends SherlockFragmentActivity implements CancelWalkDialogFragment.CancelWalkDialogListener {
	
	private TextView txtTimer;
	private Button btnStartStop;
	private Handler handler;
	private Runnable runnable;
	private Boolean isRunning = false;
	private long mStartTime = 0;
	private long mPauseTime = 0;
	private long mTotalDuration = 0;

	@Override
	protected void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setContentView(R.layout.walk_activity);
		
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
	}

	protected void startTimer() {
		isRunning = true;
		
		mStartTime = System.currentTimeMillis();
		handler.post(runnable);
		
		btnStartStop.setText("Stop Walking");
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

}
