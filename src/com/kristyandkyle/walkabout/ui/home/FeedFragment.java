package com.kristyandkyle.walkabout.ui.home;

import com.kristyandkyle.walkabout.R;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class FeedFragment extends ListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String[] countries = new String[] {"Bhutan", "Canada", "China", "Colombia", "France", "Germany", "Italy", "Jamaica", "Kazakhstan", "Kenya", "South Africa", "United States"};
		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, countries));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.test_fragment, container, false);
		return view;
	}

}
