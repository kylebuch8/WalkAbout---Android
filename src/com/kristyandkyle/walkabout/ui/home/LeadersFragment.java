package com.kristyandkyle.walkabout.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.actionbarsherlock.app.SherlockListFragment;

public class LeadersFragment extends SherlockListFragment {
	
	private ArrayAdapter<String> mAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String[] values = new String[] { "Kyle Buchanan", "Kristy Buchanan", "Cyrus Buchanan", "Ramona Buchanan", "Mikey Buchanan", "Jim Buchanan", "Vicky Buchanan", "Amanda Buchanan", "Nate Buchanan" };
		
		mAdapter = new ArrayAdapter<String>(getSherlockActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, values);
		setListAdapter(mAdapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
}
