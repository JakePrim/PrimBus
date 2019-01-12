package com.primbus;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019/1/12 - 3:23 PM
 */
public class Request implements Parcelable {
    protected Request(Parcel in) {
    }

    public static final Creator<Request> CREATOR = new Creator<Request>() {
        @Override
        public Request createFromParcel(Parcel in) {
            return new Request(in);
        }

        @Override
        public Request[] newArray(int size) {
            return new Request[size];
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
