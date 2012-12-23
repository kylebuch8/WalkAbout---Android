package com.kristyandkyle.walkabout.ui.paths;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.kristyandkyle.walkabout.R;

public class PathsActivity extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setContentView(R.layout.paths_activity);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);	
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
}
