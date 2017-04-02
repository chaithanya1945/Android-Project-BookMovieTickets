package movie.com.bookmovietickets;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlAdapter {

	public static final String MYDATABASE_NAME = "MY_DATABASE";
	public static final String MYDATABASE_TABLE = "registration_table";
	public static final String KEY_ID = "_id";
	public static final String KEY_CONTENT1 = "Name";
	public static final String KEY_CONTENT2 = "Password";
	public static final String KEY_CONTENT3 = "Type";


	public static final String MYADMIN_TABLE = "admin_table";
	public static final String KEY_ID1 = "_id";
	public static final String KEY_CONTENT4 = "MovieName";
	public static final String KEY_CONTENT5 = "MovieLocation";
	public static final String KEY_CONTENT6 = "location";
	public static final String KEY_CONTENT7 = "date";
	public static final String KEY_CONTENT8 = "time";
	public static final String KEY_CONTENT9 = "seats";


	public static final String HISTORY_TABLE = "history_table";
	public static final String KEY_ID2 = "_id";
	public static final String KEY_CONTENT10 = "MovieName";
	//public static final String KEY_CONTENT11 = "MovieLocation";
	public static final String KEY_CONTENT11= "location";
	public static final String KEY_CONTENT12 = "date";
	public static final String KEY_CONTENT13 = "time";
	public static final String KEY_CONTENT14= "amount";


	public static final int MYDATABASE_VERSION = 1;

	//create table MY_DATABASE (ID integer primary key, Content text not null);
	private static final String SCRIPT_CREATE_DATABASE =
		"create table " + MYDATABASE_TABLE + " ("
		+ KEY_ID + " integer primary key autoincrement, "
		+ KEY_CONTENT1 + " text not null, "
				+ KEY_CONTENT2 + " text not null, "
		+ KEY_CONTENT3 + " text not null);";



	private static final String SCRIPT_CREATE_DATABASE1 =
			"create table " + MYADMIN_TABLE + " ("
					+ KEY_ID1 + " integer primary key autoincrement, "
					+ KEY_CONTENT4 + " text not null, "
					+ KEY_CONTENT5 + " blob not null, "
					+ KEY_CONTENT6 + " text not null, "
					+ KEY_CONTENT7 + " text not null, "
					+ KEY_CONTENT8 + " text not null, "
					+ KEY_CONTENT9 + " text not null);";

	private static final String SCRIPT_CREATE_DATABASE2 =
			"create table " + HISTORY_TABLE + " ("
					+ KEY_ID2 + " integer primary key autoincrement, "
					+ KEY_CONTENT10 + " text not null, "
					+ KEY_CONTENT11 + " text not null, "
					+ KEY_CONTENT12 + " text not null, "
					+ KEY_CONTENT13 + " text not null, "
					+ KEY_CONTENT14 + " text not null);";


	private SQLiteHelper sqLiteHelper;
	private SQLiteDatabase sqLiteDatabase;
	private Context context;

	public SqlAdapter(Context c){
		context = c;
	}

	public SqlAdapter openToRead() throws android.database.SQLException {
		sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getReadableDatabase();
		return this; 
	}

	public SqlAdapter openToWrite() throws android.database.SQLException {
		sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getWritableDatabase();
		return this; 
	}

	public void close(){
		sqLiteHelper.close();
	}

	public long insert(String content1, String content2, String content3){

		ContentValues contentValues = new ContentValues();
		contentValues.put(KEY_CONTENT1, content1);
		contentValues.put(KEY_CONTENT2, content2);
		contentValues.put(KEY_CONTENT3, content3);

		return sqLiteDatabase.insert(MYDATABASE_TABLE, null, contentValues);
	}

	public int deleteAll(){
		return sqLiteDatabase.delete(MYDATABASE_TABLE, null, null);
	}

	public Cursor queueAll(){
		String[] columns = new String[]{KEY_ID, KEY_CONTENT1, KEY_CONTENT2,KEY_CONTENT3};
		Cursor cursor = sqLiteDatabase.query(MYDATABASE_TABLE, columns,
				null, null, null, null, null);

		return cursor;
	}


	public long insertMovie(String name, byte[] imgDecodableString, String location,String date,String time,String seats){


		ContentValues contentValues = new ContentValues();
		contentValues.put(KEY_CONTENT4, name);
		contentValues.put(KEY_CONTENT5, imgDecodableString);
		contentValues.put(KEY_CONTENT6, location);
		contentValues.put(KEY_CONTENT7, date);
		contentValues.put(KEY_CONTENT8, time);
		contentValues.put(KEY_CONTENT9, seats);

		return sqLiteDatabase.insert(MYADMIN_TABLE, null, contentValues);

	}

	public long insertHistory(History history) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(KEY_CONTENT10, history.getName());
		contentValues.put(KEY_CONTENT11, history.getLocation());
		contentValues.put(KEY_CONTENT12, history.getDate());
		contentValues.put(KEY_CONTENT13, history.getTime());
		contentValues.put(KEY_CONTENT14, history.getNumber());

		return sqLiteDatabase.insert(HISTORY_TABLE, null, contentValues);

	}


	public boolean deleteMovie(long rowId) {
		return sqLiteDatabase.delete(MYADMIN_TABLE, KEY_ID1 + "=" + rowId, null) > 0;
	}

	public boolean updateMovie(long rowId, String name, byte[] imgDecodableString,String location,String date,String time,String seats) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(KEY_CONTENT4, name);
		contentValues.put(KEY_CONTENT5, imgDecodableString);
		contentValues.put(KEY_CONTENT6, location);
		contentValues.put(KEY_CONTENT7, date);
		contentValues.put(KEY_CONTENT8, time);
		contentValues.put(KEY_CONTENT9, seats);

		return sqLiteDatabase.update(MYADMIN_TABLE, contentValues, KEY_ID1 + "=" + rowId, null) > 0;
	}


	public Cursor queueMoviesAll(){
		String[] columns = new String[]{KEY_ID1, KEY_CONTENT4, KEY_CONTENT5,KEY_CONTENT6,KEY_CONTENT7,KEY_CONTENT8,KEY_CONTENT9};
		Cursor cursor = sqLiteDatabase.query(MYADMIN_TABLE, columns,
				null, null, null, null, null);

		return cursor;
	}

	public Cursor queueHistoryAll(){
		String[] columns = new String[]{KEY_ID2, KEY_CONTENT10, KEY_CONTENT11,KEY_CONTENT12,KEY_CONTENT13,KEY_CONTENT14};
		Cursor cursor = sqLiteDatabase.query(HISTORY_TABLE, columns,
				null, null, null, null, null);

		return cursor;
	}


	public class SQLiteHelper extends SQLiteOpenHelper {

		public SQLiteHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(SCRIPT_CREATE_DATABASE);
			db.execSQL(SCRIPT_CREATE_DATABASE1);
			db.execSQL(SCRIPT_CREATE_DATABASE2);


		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
		}
	} 
}
