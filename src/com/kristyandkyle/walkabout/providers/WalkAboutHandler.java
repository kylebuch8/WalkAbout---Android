package com.kristyandkyle.walkabout.providers;

import java.io.IOException;
import java.util.ArrayList;

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
			
			if (mQueryText.equals("walkabouts")) {
				parseWalkAbout(jsonResponse);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void parsePaths(JSONObject jsonResponse) {
		try {
			JSONArray pathsArray = jsonResponse.getJSONArray("paths");
			ContentValues[] pathsContentValues = new ContentValues[pathsArray.length()];
			ArrayList<ContentValues> waypointsContentValuesArray = new ArrayList<ContentValues>();
			
			if (pathsArray.length() > 0) {
				for (int i = 0; i < pathsArray.length(); i++) {
					JSONObject path = pathsArray.getJSONObject(i);
					
					ContentValues pathEntry = new ContentValues();
					pathEntry.put(WalkAbout.Paths.ID, path.getString("id"));
					pathEntry.put(WalkAbout.Paths.NAME, path.getString("name"));
					pathEntry.put(WalkAbout.Paths.DISTANCE, path.getString("distance"));
					
					pathsContentValues[i] = pathEntry;
										
					// loop through all of the waypoints
					JSONArray waypoints = path.getJSONArray("waypoints");
							
					for (int j = 0; j < waypoints.length(); j++) {
						JSONObject waypoint = waypoints.getJSONObject(j);
						
						ContentValues waypointEntry = new ContentValues();
						waypointEntry.put(WalkAbout.Waypoints.ID, waypoint.getString("id"));
						waypointEntry.put(WalkAbout.Waypoints.PATH_ID, waypoint.getString("pathId"));
						waypointEntry.put(WalkAbout.Waypoints.NAME, waypoint.getString("name"));
						waypointEntry.put(WalkAbout.Waypoints.LATITUDE, waypoint.getString("latitude"));
						waypointEntry.put(WalkAbout.Waypoints.LONGITUDE, waypoint.getString("longitude"));
						
						waypointsContentValuesArray.add(waypointEntry);
					}
				}
				
				mWalkAboutContentProvider.bulkInsert(WalkAbout.Paths.PATHS_URI, pathsContentValues);
				
				ContentValues[] waypointContentValues = new ContentValues[waypointsContentValuesArray.size()]; 
				for (int k = 0; k < waypointsContentValuesArray.size(); k++) {
					waypointContentValues[k] = waypointsContentValuesArray.get(k);
				}
				
				mWalkAboutContentProvider.bulkInsert(WalkAbout.Waypoints.CONTENT_URI, waypointContentValues);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void parseWalkAbout(JSONObject jsonResponse) {
		
	}

}
