package com.kristyandkyle.walkabout.ui.walk;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.kristyandkyle.walkabout.R;
import com.kristyandkyle.walkabout.providers.WalkAbout;
import com.kristyandkyle.walkabout.ui.MainActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.Button;

public class WalkCompletedActivity extends SherlockFragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {

	private static final int WALK_LOADER = 0x01;
	private String mWalkId;
	private Boolean mFromHome;
	
	@Override
	protected void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setContentView(R.layout.walk_completed_activity);
		
		mWalkId = getIntent().getStringExtra("walkId");
		mFromHome = getIntent().getBooleanExtra("fromHome", false);
		
		if (!mFromHome) {
			Button btnDone = (Button) findViewById(R.id.walkCompletedActivity_btnDone);
			btnDone.setVisibility(View.VISIBLE);
			btnDone.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					sendHome();
				}
			});
		} else {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		
		getSupportLoaderManager().initLoader(WALK_LOADER, null, this);
	}

	protected void sendHome() {
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		finish();
	}
	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				break;
	
			default:
				break;
		}
	
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		if (!mFromHome) {
			sendHome();
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Uri uri = Uri.parse(WalkAbout.WalkAbouts.WALKABOUTS_URI + "/" + mWalkId);
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
