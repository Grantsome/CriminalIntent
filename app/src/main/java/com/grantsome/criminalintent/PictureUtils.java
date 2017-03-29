package com.grantsome.criminalintent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by tom on 2017/3/29.
 */

public class PictureUtils {

    public static Bitmap getScaledBitmap(String path,int destWitdth,int destHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path);
        float scrWidth = options.outWidth;
        float scrHeight = options.outHeight;
        int inSmapleSize = 1;
        if(scrHeight>destHeight||scrWidth>destWitdth){
            if(scrHeight>scrHeight){
                inSmapleSize = Math.round(scrHeight/destHeight);
            }else {
                inSmapleSize = Math.round(scrWidth/destWitdth);
            }
        }
        options = new BitmapFactory.Options();
        options.inSampleSize = inSmapleSize;
        return BitmapFactory.decodeFile(path,options);
    }

    public static Bitmap getScaledBitmap(String path,Activity activity){
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return getScaledBitmap(path,size.x,size.y);
    }
}
