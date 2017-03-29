package com.grantsome.criminalintent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.grantsome.criminalintent.CrimeDbSchema.CrimeTable.NAME;
import static com.grantsome.criminalintent.CrimeDbSchema.CrimeTable.Cols;

/**
 * Created by tom on 2017/3/13.
 */

public class CrimeBaseHelper extends SQLiteOpenHelper {

    public static final int VERSION = 2;

    public static final String DATABASE_NAME = "crimeBase.db";

    public CrimeBaseHelper(Context context) {
        super(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + NAME+"("+"_id integer primary key autoincrement, "+ Cols.UUID+", "+Cols.TITLE+", "+ Cols.DATE+", "+Cols.SOLVED + ", " + Cols.SUSPECT+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
