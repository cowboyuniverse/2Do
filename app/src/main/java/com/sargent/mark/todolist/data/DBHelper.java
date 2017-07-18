package com.sargent.mark.todolist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mark on 7/4/17.
 */

public class DBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "items.db";
    private static final String TAG = "dbhelper";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //CREATING COLUMNS FOR CATEGORY AND DONE
    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryString = "CREATE TABLE " + Contract.TABLE_TODO.TABLE_NAME + " ("+
                Contract.TABLE_TODO._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contract.TABLE_TODO.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL, " +
                Contract.TABLE_TODO.COLUMN_NAME_DUE_DATE + " DATE, " +
                Contract.TABLE_TODO.COLUMN_NAME_CATEGORY + " TEXT NOT NULL " +
                "); ";

        Log.d(TAG, "Create table SQL: " + queryString);
        db.execSQL(queryString);

//        db.execSQL("INSERT into todoitems(description, duedate) " +
//                "values('Eat Ramen with Jessica','2017-6-21')");

//                db.execSQL("INSERT into todoitems(description, duedate) " +
//                "values('Eat Ramen with Jessica','2017-6-21', 'description2')");

        db.execSQL("INSERT into todoitems(description, duedate, category) " +
                "values('Finish Android HW3','2017-6-16', 'School')");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("drop table " + Contract.TABLE_TODO.TABLE_NAME + " if exists;");
    }
}



//    public static final String TABLE_NAME = "todoitems";
//    public static final String COLUMN_NAME_DESCRIPTION = "description";
//    public static final String COLUMN_NAME_DUE_DATE = "duedate";
//    public static final String COLUMN_NAME_DONE = "done" ;
//    public static final String COLUMN_NAME_CATEGORY = "category" ;
