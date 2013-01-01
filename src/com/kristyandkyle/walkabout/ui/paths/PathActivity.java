package com.kristyandkyle.walkabout.ui.paths;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.kristyandkyle.walkabout.R;
import com.kristyandkyle.walkabout.providers.WalkAbout;
import com.kristyandkyle.walkabout.ui.walk.WalkActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

public class PathActivity extends SherlockFragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {
	
	private static final int PATH_LIST_LOADER = 0x01;
	private long mPathId;
	private TextView mTxtPathName;

	@Override
	protected void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setContentView(R.layout.path_activity);
		
		PathFragment pathFragment = (PathFragment) getSupportFragmentManager().findFragmentById(R.id.pathFragment);
		
		mPathId = getIntent().getLongExtra("pathId", 0);
		mTxtPathName = (TextView) pathFragment.getView().findViewById(R.id.pathFragment_pathName);
		
		Button btnStartWalk = (Button) pathFragment.getView().findViewById(R.id.pathFragment_btnStartWalk);
		btnStartWalk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), WalkActivity.class);
				intent.putExtra("pathId", mPathId);
				startActivity(intent);
			}
		});
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);	
		
		getSupportLoaderManager().initLoader(PATH_LIST_LOADER, null, this);
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
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Uri uri = Uri.parse(WalkAbout.Paths.PATHS_URI + "/" + mPathId);
		CursorLoader cursorLoader = new CursorLoader(this, uri, null, null, null, null);
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		cursor.moveToPosition(0);
		
		mTxtPathName.setText(cursor.getString(cursor.getColumnIndex(WalkAbout.Paths.NAME)));
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
