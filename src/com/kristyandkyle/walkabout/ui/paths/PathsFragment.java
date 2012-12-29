package com.kristyandkyle.walkabout.ui.paths;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.kristyandkyle.walkabout.R;
import com.kristyandkyle.walkabout.providers.WalkAbout;

public class PathsFragment extends SherlockListFragment {

	SimpleCursorAdapter mAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		Cursor pathCursor = null;
		ContentResolver contentResolver = getActivity().getContentResolver();
		pathCursor = contentResolver.query(WalkAbout.Paths.PATHS_URI, null, null, null, null);
		mAdapter = new SimpleCursorAdapter(getSherlockActivity(), android.R.layout.simple_list_item_1, pathCursor, new String[] { WalkAbout.Paths.NAME }, new int[] { android.R.id.text1 }, 0);
		
		setListAdapter(mAdapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.paths_fragment, container, false);
		return view;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		TextView textView = (TextView) v.findViewById(android.R.id.text1);
		
		Intent intent = new Intent(getSherlockActivity(), PathActivity.class);
		intent.putExtra("pathName", textView.getText());
		startActivity(intent);
	}
	
}
