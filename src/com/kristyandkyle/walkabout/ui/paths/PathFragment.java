package com.kristyandkyle.walkabout.ui.paths;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.database.Cursor;

import com.actionbarsherlock.app.SherlockFragment;
import com.kristyandkyle.walkabout.R;
import com.kristyandkyle.walkabout.providers.WalkAbout;

public class PathFragment extends SherlockFragment implements LoaderManager.LoaderCallbacks<Cursor> {
	
	private static final int WAYPOINTS_LOADER = 0;	
	private Uri mWaypointsUri = WalkAbout.Waypoints.CONTENT_URI;
	private TextView mTxtPathName;
	private TextView mTxtPathDistance;
	private long mPathId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mPathId = getActivity().getIntent().getLongExtra("pathId", 0);
		
		//getLoaderManager().initLoader(WAYPOINTS_LOADER, null, this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.path_fragment, container, false);	
		updatePathView(view);
		return view;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		switch (id) {
			case WAYPOINTS_LOADER:
				
				return new CursorLoader(getActivity(),
						Uri.parse(mWaypointsUri + "/" + mPathId),
						null,
						null,
						null,
						null
					);
				
			default:
				return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		int loaderId = loader.getId();
		
		switch (loaderId) {
			case WAYPOINTS_LOADER:
				updateWayPointsView(cursor);
				break;
	
			default:
				break;
			}
	}
	
	private void updatePathView(View view) {
		mTxtPathName = (TextView) view.findViewById(R.id.pathFragment_pathName);
		mTxtPathName.setText(getActivity().getIntent().getStringExtra("pathName"));
		
		mTxtPathDistance = (TextView) view.findViewById(R.id.pathFragment_pathDistance);
		mTxtPathDistance.setText(getActivity().getIntent().getStringExtra("pathDistance"));
	}

	private void updateWayPointsView(Cursor cursor) {
		while (cursor.moveToNext()) {
			
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// do nothing
	}

}
