package com.kristyandkyle.walkabout.ui.paths;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.kristyandkyle.walkabout.R;
import com.kristyandkyle.walkabout.ui.walk.WalkActivity;

public class PathActivity extends SherlockFragmentActivity {
	
	private long mPathId;

	@Override
	protected void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setContentView(R.layout.path_activity);
		
		PathFragment pathFragment = (PathFragment) getSupportFragmentManager().findFragmentById(R.id.pathFragment);
		
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
