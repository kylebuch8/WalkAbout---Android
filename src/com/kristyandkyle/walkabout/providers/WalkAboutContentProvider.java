package com.kristyandkyle.walkabout.providers;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;

import com.kristyandkyle.walkabout.utils.FileHandlerFactory;
import com.kristyandkyle.walkabout.utils.RESTfulContentProvider;
import com.kristyandkyle.walkabout.utils.ResponseHandler;

public class WalkAboutContentProvider extends RESTfulContentProvider {
	public static final String WALKABOUT = "walkabout";
    public static final String DATABASE_NAME = WALKABOUT + ".db";
    static int DATABASE_VERSION = 3;

    public static final String WALKABOUTS_TABLE_NAME = "walkabout";
    public static final String PATHS_TABLE_NAME = "paths";
	
	private static final String FILE_CACHE_DIR = "/data/data/com.kristyandkyle.walkabout/file_cache";
	
	private static final int WALKABOUTS = 1;
	private static final int PATHS = 2;
	
	private static UriMatcher sUriMatcher;
	
	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(WalkAbout.AUTHORITY, WalkAbout.WalkAbouts.WALKABOUT, WALKABOUTS);
		sUriMatcher.addURI(WalkAbout.AUTHORITY, WalkAbout.Paths.PATHS, PATHS);
	}
	
	private DatabaseHelper mOpenHelper;
    private SQLiteDatabase mDb; 
    
    private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context, String name, CursorFactory factory) {
			super(context, name, factory, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			createTable(db);
		}

		private void createTable(SQLiteDatabase db) {
			String createWalkAboutTable = "CREATE TABLE " + WALKABOUTS_TABLE_NAME + "(" + 
					BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					WalkAbout.WalkAbouts.DURATION + " TEXT, " +
					WalkAbout.WalkAbouts.TIMESTAMP + " TEXT, " +
					WalkAbout.WalkAbouts._DATA + " TEXT UNIQUE" + 
					");";
			
			db.execSQL(createWalkAboutTable);
			
			String createPathsTable = "CREATE TABLE " + PATHS_TABLE_NAME + "(" +
					BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					WalkAbout.Paths.ID + " TEXT, " +
					WalkAbout.Paths.NAME + " TEXT, " +
					WalkAbout.Paths.DISTANCE + " TEXT, " +
					WalkAbout.Paths._DATA + " TEXT UNIQUE" + 
					");";
			
			db.execSQL(createPathsTable);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + WALKABOUTS_TABLE_NAME + ";");
			db.execSQL("DROP TABLE IF EXISTS " + PATHS_TABLE_NAME + ";");
			createTable(db);
		}
    	
    }

	public WalkAboutContentProvider() {
		super(new FileHandlerFactory(FILE_CACHE_DIR));
	}
	
	public WalkAboutContentProvider(Context context) {
        super(new FileHandlerFactory(FILE_CACHE_DIR));
        init();
    }
	
	@Override
	public boolean onCreate() {
		init();
		return true;
	}

	private void init() {
		mOpenHelper = new DatabaseHelper(getContext(), DATABASE_NAME, null);
		mDb = mOpenHelper.getWritableDatabase();
		mFileHandlerFactory = new FileHandlerFactory(FILE_CACHE_DIR);
	}
	
	@Override
	public SQLiteDatabase getDatabase() {
		return mDb;
	}
	
	@Override
	protected ResponseHandler newResponseHandler(String requestTag) {
		return new WalkAboutHandler(this, requestTag);
	}
	
	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Cursor queryCursor;
		
		int match = sUriMatcher.match(uri);
		switch (match) {
			case WALKABOUTS:
				queryCursor = mDb.query(WALKABOUTS_TABLE_NAME,
						projection,
						selection,
						selectionArgs,
						null,
						null,
						sortOrder);
				
				queryCursor.setNotificationUri(getContext().getContentResolver(), uri);
				asyncQueryRequest("walkabout", "http://kristyandkyle.com/walkabout/assets/scripts/paths.json");
				break;
			
			case PATHS:
				queryCursor = mDb.query(PATHS_TABLE_NAME,
						projection,
						selection,
						selectionArgs,
						null,
						null,
						sortOrder);
				
				queryCursor.setNotificationUri(getContext().getContentResolver(), uri);
				asyncQueryRequest("paths", "http://kristyandkyle.com/walkabout/assets/scripts/paths.json");
				break;
	
			default:
				throw new IllegalArgumentException("unsupported uri");
		}
		
		return queryCursor;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values, SQLiteDatabase db) {
		String itemId = (String) values.get(WalkAbout.Paths.ID);
		Long rowID = itemExists(db, WalkAbout.Paths.ID, itemId, PATHS_TABLE_NAME);
		
		if (rowID == null) {
			long rowId = db.insert(PATHS_TABLE_NAME, WalkAbout.Paths.PATHS, values);
			
			if (rowId >= 0) {
				Uri insertUri = ContentUris.withAppendedId(WalkAbout.Paths.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(insertUri, null);
				return insertUri;
			}
		}
		
		return ContentUris.withAppendedId(WalkAbout.Paths.CONTENT_URI, rowID);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private Long itemExists(SQLiteDatabase db, String columnName, String itemId, String tableName) {
		Cursor cursor = null;
		Long rowId = null;
		
		try {
			cursor = db.query(tableName,
					null,
					columnName + "= '" + itemId + "'",
					null,
					null,
					null,
					null);
			
			if (cursor.moveToFirst()) {
				rowId = cursor.getLong(0);
			} 
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		
		return rowId;
	}

}
