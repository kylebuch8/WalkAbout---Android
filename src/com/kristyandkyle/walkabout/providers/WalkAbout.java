package com.kristyandkyle.walkabout.providers;

import android.net.Uri;
import android.provider.BaseColumns;

public class WalkAbout {
	
	public static final String AUTHORITY = "com.kristyandkyle.walkabout.WalkAbout";
	
	public static final class WalkAbouts implements BaseColumns {
		
		private WalkAbouts() {}
		
		public static final Uri WALKABOUTS_URI = Uri.parse("content://" + AUTHORITY + "/" + WalkAbouts.WALKABOUT_NAME);
		
		public static final String WALKABOUT_NAME = "walkabout";
		
		public static final String WALKABOUT = "walkabout";
		
		public static final String DURATION = "duration";
		
		public static final String TIMESTAMP = "timestamp";
		
		public static final String _DATA = "_data";
	}
	
	public static final class Paths implements BaseColumns {
		
		private Paths() {};
		
		public static final Uri PATHS_URI = Uri.parse("content://" + AUTHORITY + "/" + Paths.PATHS_NAME);
		
		public static final Uri CONTENT_URI = PATHS_URI;
		
		public static final String PATHS_NAME = "paths";
		
		public static final String PATHS = "paths";
		
		public static final String ID = "id";
		
		public static final String NAME = "name";
		
		public static final String DISTANCE = "distance";
		
		public static final String _DATA = "_data";
		
	}
	
}
