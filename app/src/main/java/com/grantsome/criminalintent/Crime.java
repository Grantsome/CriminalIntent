package com.grantsome.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by tom on 2017/3/5.
 */

public class Crime {

    private UUID mId;

    private String mTitle;

    private Date mDate;

    private boolean mSolved;

    private String mSuspect;

    public Crime(){
        this(UUID.randomUUID());
    }

    public Crime(UUID id){
        mId = id;
        mDate = new Date();
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public UUID getId() {
        return mId;
    }

    public Date getDate() {
        return mDate;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }

    public String getSuspect() {
        return mSuspect;
    }

    public String getPhotoFileName(){
        return "IMG_" + getId().toString() + ".jpg";
    }

}
