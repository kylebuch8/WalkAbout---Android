package com.kristyandkyle.walkabout.ui.paths;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.kristyandkyle.walkabout.R;

public class PathsFragment extends SherlockListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String[] paths = new String[] {"Around the Tower", "The Ultimate WalkAbout", "The Roundabout to Teeter", "Around the Block", "Tower to McDonalds"};
		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, paths));
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
