package com.kristyandkyle.walkabout.ui.home;

import com.kristyandkyle.walkabout.R;
import com.kristyandkyle.walkabout.providers.WalkAbout;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;

public class HistoryFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

	private static final int HISTORY_LIST_LOADER = 0x01;
	private SimpleCursorAdapter mAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String[] uiBindFrom = { WalkAbout.WalkAbouts.DURATION, WalkAbout.WalkAbouts.PATH_ID };
		int[] uiBindTo = { android.R.id.text1, android.R.id.text2 };
		
		getActivity().getSupportLoaderManager().initLoader(HISTORY_LIST_LOADER, null, this);
		mAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_2, null, uiBindFrom, uiBindTo, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		setListAdapter(mAdapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.test_fragment, container, false);
		return view;
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
