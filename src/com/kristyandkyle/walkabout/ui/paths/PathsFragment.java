package com.kristyandkyle.walkabout.ui.paths;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.kristyandkyle.walkabout.R;
import com.kristyandkyle.walkabout.providers.WalkAbout;

public class PathsFragment extends SherlockListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

	private static final int PATHS_LIST_LOADER = 0x01;
	private SimpleCursorAdapter mAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String[] uiBindFrom = { WalkAbout.Paths.NAME, WalkAbout.Paths.DISTANCE };
		int[] uiBindTo = { android.R.id.text1, android.R.id.text2 };
		
		getActivity().getSupportLoaderManager().initLoader(PATHS_LIST_LOADER, null, this);
		mAdapter = new SimpleCursorAdapter(getSherlockActivity(), android.R.layout.simple_list_item_2, null, uiBindFrom, uiBindTo, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		setListAdapter(mAdapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.paths_fragment, container, false);
		return view;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getSherlockActivity(), PathActivity.class);
		intent.putExtra("pathId", id);
		startActivity(intent);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		CursorLoader cursorLoader = new CursorLoader(getSherlockActivity(), WalkAbout.Paths.PATHS_URI, null, null, null, null);
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
