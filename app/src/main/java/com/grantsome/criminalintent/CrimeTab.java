package com.grantsome.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.grantsome.criminalintent.CrimeDbSchema.CrimeTable.Cols;
import static com.grantsome.criminalintent.CrimeDbSchema.CrimeTable.NAME;
/**
 * Created by tom on 2017/3/5.
 */

public class CrimeTab {

    public static CrimeTab sCrimeTab;

    private Context mContext;

    private SQLiteDatabase mSQLiteDatabase;

    public static CrimeTab get(Context context){
        if(sCrimeTab==null) {
            sCrimeTab = new CrimeTab(context);
        }
        return sCrimeTab;
    }

    private CrimeTab(Context context) {
        mContext = context.getApplicationContext();
        mSQLiteDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();

    }

    public void addCrime(Crime crime){
        ContentValues contentValues = getContentValues(crime);
        mSQLiteDatabase.insert(NAME,null,contentValues);
    }

    public void updateCrime(Crime crime){
        String uuid = crime.getId().toString();
        ContentValues contentValues = getContentValues(crime);
        mSQLiteDatabase.update(NAME,contentValues, Cols.UUID + " = ?",new String[]{uuid});
    }

    public CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs){
        Cursor cursor = mSQLiteDatabase.query(NAME,null,whereClause,whereArgs,null,null,null);
        return new CrimeCursorWrapper(cursor);
    }

    public List<Crime> getCrimes(){
        List<Crime> crimes = new ArrayList<>();
        CrimeCursorWrapper crimeCursorWrapper = queryCrimes(null,null);
        try {
            crimeCursorWrapper.moveToFirst();
            while(!crimeCursorWrapper.isAfterLast()){
                crimes.add(crimeCursorWrapper.getCrime());
                crimeCursorWrapper.moveToNext();
            }
        }finally {
            crimeCursorWrapper.close();
        }
      return crimes;
    }

    public Crime getCrime(UUID id){
        CrimeCursorWrapper crimeCursorWrapper = queryCrimes(Cols.UUID + " = ?",new String[]{id.toString()});
        try {
            if(crimeCursorWrapper.getCount() == 0){
                return null;
            }
            crimeCursorWrapper.moveToFirst();
            return crimeCursorWrapper.getCrime();
        }finally {
            crimeCursorWrapper.close();
        }
    }

    private static ContentValues getContentValues(Crime crime){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Cols.UUID,crime.getId().toString());
        contentValues.put(Cols.TITLE,crime.getTitle());
        contentValues.put(Cols.DATE, crime.getDate().getTime());
        contentValues.put(Cols.SOLVED,crime.isSolved()?1:0);
        contentValues.put(Cols.SUSPECT,crime.getSuspect());
        return contentValues;
    }

    public File getPhotoFile(Crime crime){
        File externalFilesDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if(externalFilesDir == null){
            return null;
        }
        return new File(externalFilesDir,crime.getPhotoFileName());
    }

}
