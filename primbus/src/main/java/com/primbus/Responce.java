package com.primbus;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019/1/12 - 3:27 PM
 */
public class Responce implements Parcelable {
    protected Responce(Parcel in) {
    }

    public static final Creator<Responce> CREATOR = new Creator<Responce>() {
        @Override
        public Responce createFromParcel(Parcel in) {
            return new Responce(in);
        }

        @Override
        public Responce[] newArray(int size) {
            return new Responce[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
