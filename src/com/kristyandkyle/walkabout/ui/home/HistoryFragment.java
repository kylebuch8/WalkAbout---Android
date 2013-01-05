package com.kristyandkyle.walkabout.ui.home;

import com.kristyandkyle.walkabout.R;
import com.kristyandkyle.walkabout.providers.WalkAbout;
import com.kristyandkyle.walkabout.ui.walk.WalkCompletedActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;

public class HistoryFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

	private static final int HISTORY_LIST_LOADER = 0x01;
	private SimpleCursorAdapter mAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//String[] uiBindFrom = { WalkAbout.WalkAbouts.DURATION, WalkAbout.Paths.NAME };
		String[] uiBindFrom = { WalkAbout.WalkAbouts.DURATION, WalkAbout.WalkAbouts.TIMESTAMP };
		int[] uiBindTo = { android.R.id.text1, android.R.id.text2 };
		
		getActivity().getSupportLoaderManager().initLoader(HISTORY_LIST_LOADER, null, this);
		mAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_2, null, uiBindFrom, uiBindTo, 0);
		mAdapter.setViewBinder(new ViewBinder() {
			
			@Override
			public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
				Log.d("WalkAbout", cursor.getString(columnIndex));
				
				if (columnIndex == cursor.getColumnIndex(WalkAbout.WalkAbouts.DURATION)) {
					String duration = WalkAbout.WalkAbouts.formatDuration(cursor.getString(columnIndex));
					((TextView) view).setText(duration);
					return true;
				}
				
				if (columnIndex == cursor.getColumnIndex(WalkAbout.WalkAbouts.TIMESTAMP)) {
					String timestamp = WalkAbout.WalkAbouts.formatTimestamp(cursor.getString(columnIndex));
					((TextView) view).setText(timestamp);
					return true;
				}
				
				return false;
			}
		});
		
		setListAdapter(mAdapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.test_fragment, container, false);
		return view;
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getActivity(), WalkCompletedActivity.class);
		intent.putExtra("walkId", String.valueOf(id));
		intent.putExtra("fromHome", true);
		
		startActivity(intent);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		CursorLoader cursorLoader = new CursorLoader(getActivity(), WalkAbout.WalkAbouts.WALKABOUTS_URI, null, null, null, null);
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		mAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}

}
