package com.kristyandkyle.walkabout.ui.walk;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class CancelWalkDialogFragment extends DialogFragment {
	
	public interface CancelWalkDialogListener {
		public void onDialogPositiveClick(DialogFragment dialog);
		public void onDialogNegativeClick(DialogFragment dialog);
	}
	
	CancelWalkDialogListener mListener;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("Cancel WalkAbout?")
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mListener.onDialogPositiveClick(CancelWalkDialogFragment.this);
				}
			})
			.setNegativeButton("No", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mListener.onDialogNegativeClick(CancelWalkDialogFragment.this);
				}
			});
		
		return builder.create();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		// verify that the host activity implements the callback interface
		try {
			mListener = (CancelWalkDialogListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement CancelWalkDialogListener");
		}
	}
	
}
