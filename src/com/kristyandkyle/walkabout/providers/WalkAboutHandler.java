package com.kristyandkyle.walkabout.providers;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.kristyandkyle.walkabout.utils.RESTfulContentProvider;
import com.kristyandkyle.walkabout.utils.ResponseHandler;

public class WalkAboutHandler implements ResponseHandler {

	private RESTfulContentProvider mWalkAboutContentProvider;
	private String mQueryText;
	
	public WalkAboutHandler(RESTfulContentProvider restfulContentProvider, String requestTag) {
		mWalkAboutContentProvider = restfulContentProvider;
		mQueryText = requestTag;
	}

	@Override
	public void handleResponse(HttpResponse response, Uri uri) {
		try {
			parseEntity(response.getEntity());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void parseEntity(HttpEntity entity) throws IOException {
		String data = EntityUtils.toString(entity);
		try {
			JSONObject jsonResponse = new JSONObject(data);
			
			if (mQueryText.equals("paths")) {
				parsePaths(jsonResponse);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void parsePaths(JSONObject jsonResponse) {
		try {
			JSONArray pathsArray = jsonResponse.getJSONArray("paths");
			
			if (pathsArray.length() > 0) {
				for (int i = 0; i < pathsArray.length(); i++) {
					JSONObject path = pathsArray.getJSONObject(i);
					
					ContentValues pathEntry = new ContentValues();
					pathEntry.put(WalkAbout.Paths.ID, path.getString("id"));
					pathEntry.put(WalkAbout.Paths.NAME, path.getString("name"));
					pathEntry.put(WalkAbout.Paths.DISTANCE, path.getString("distance"));
					
					SQLiteDatabase db = mWalkAboutContentProvider.getDatabase();
					Uri providerUri = mWalkAboutContentProvider.insert(WalkAbout.Paths.PATHS_URI, pathEntry, db);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
