package com.wuyou.merchant.data.api;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by DELL on 2018/10/11.
 */

public class VoteOptionContent implements Parcelable {
    String optioncontent;
    public int number;

    public VoteOptionContent(String content) {
        optioncontent = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.optioncontent);
        dest.writeInt(this.number);
    }

    protected VoteOptionContent(Parcel in) {
        this.optioncontent = in.readString();
        this.number = in.readInt();
    }

    public static final Parcelable.Creator<VoteOptionContent> CREATOR = new Parcelable.Creator<VoteOptionContent>() {
        @Override
        public VoteOptionContent createFromParcel(Parcel source) {
            return new VoteOptionContent(source);
        }

        @Override
        public VoteOptionContent[] newArray(int size) {
            return new VoteOptionContent[size];
        }
    };
}
