package com.zzq.netlib.view.dialog;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @auther tangedegushi
 * @creat 2018/9/12
 * @Decribe
 */
public abstract class ViewListener implements Parcelable {

    public abstract void convert(ViewHolder helper,EasyDialog dialog);

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public ViewListener() {
    }

    protected ViewListener(Parcel in) {
    }

    public static final Creator<ViewListener> CREATOR = new Creator<ViewListener>() {
        @Override
        public ViewListener createFromParcel(Parcel source) {
            return new ViewListener(source) {
                @Override
                public void convert(ViewHolder helper, EasyDialog dialog) {

                }
            };
        }

        @Override
        public ViewListener[] newArray(int size) {
            return new ViewListener[size];
        }
    };
}
