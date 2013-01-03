package com.kristyandkyle.walkabout.providers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.net.Uri;
import android.provider.BaseColumns;

public class WalkAbout {
	
	public static final String AUTHORITY = "com.kristyandkyle.walkabout.WalkAbout";
	
	public static final class WalkAbouts implements BaseColumns {
		
		private WalkAbouts() {}
		
		public static final Uri WALKABOUTS_URI = Uri.parse("content://" + AUTHORITY + "/" + WalkAbouts.WALKABOUT_NAME);
		
		public static final Uri CONTENT_URI = WALKABOUTS_URI;
		
		public static final String WALKABOUT_NAME = "walkabouts";
		
		public static final String WALKABOUTS = "walkabouts";
		
		public static final String WALKABOUT = WalkAbout.WalkAbouts.WALKABOUTS + "/#";
		
		public static final String PATH_ID = "path_id";
		
		public static final String DURATION = "duration";
		
		public static final String TIMESTAMP = "timestamp";
		
		public static final String _DATA = "_data";
		
		public static final String formatDuration(String duration) {
			int seconds = (int) (Integer.parseInt(duration) / 1000);
			int minutes = seconds / 60;
			seconds = seconds % 60;
			
			String secondsText = (seconds < 10) ? "0" + seconds : "" + seconds;
			String minutesText = (minutes < 10) ? "0" + minutes : "" + minutes;
			
			return minutesText + ":" + secondsText;
		}
		
		public static final String formatTimestamp(String timestamp) {
			Date date = new Date(Long.parseLong(timestamp));
			DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy 'at' hh:mm", Locale.US);
			DateFormat amPmDateFormat = new SimpleDateFormat("a", Locale.US);
			return dateFormat.format(date) + " " + amPmDateFormat.format(date).toLowerCase(Locale.US);
		}
	}
	
	public static final class Paths implements BaseColumns {
		
		private Paths() {};
		
		public static final Uri PATHS_URI = Uri.parse("content://" + AUTHORITY + "/" + Paths.PATHS_NAME);
		
		public static final Uri CONTENT_URI = PATHS_URI;
		
		public static final String PATHS_NAME = "paths";
		
		public static final String PATHS = "paths";
		
		public static final String PATH = WalkAbout.Paths.PATHS + "/#";
		
		public static final String ID = "id";
		
		public static final String NAME = "name";
		
		public static final String DISTANCE = "distance";
		
		public static final String _DATA = "_data";
		
	}
	
	public static final class Waypoints implements BaseColumns {

		private Waypoints() {};
		
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + Waypoints.WAYPOINTS_NAME);
		
		public static final String WAYPOINTS_NAME = "waypoints";
		
		public static final String ID = "id";
		
		public static final String NAME = "name";
		
		public static final String PATH_ID = "path_id";
		
		public static final String LATITUDE = "latitude";
		
		public static final String LONGITUDE = "longitude";

	}
	
	public static final class WalkAbouts_Waypoints implements BaseColumns {
		
		private WalkAbouts_Waypoints() {};
		
		public static final String WALKABOUT_ID = "walkabout_id";
		
		public static final String WAYPOINT_ID = "waypoint_id";
		
	}
	
}
