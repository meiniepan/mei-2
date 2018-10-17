package com.wuyou.merchant.data.api;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by DELL on 2018/10/11.
 */

public class VoteOptionContent implements Parcelable {
   public String optioncontent;
    public int number;
    public int id;
    public boolean isChecked;
    public VoteOptionContent(String content) {
        optioncontent = content;
    }


    protected VoteOptionContent(Parcel in) {
        optioncontent = in.readString();
        number = in.readInt();
        id = in.readInt();
        isChecked = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(optioncontent);
        dest.writeInt(number);
        dest.writeInt(id);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VoteOptionContent> CREATOR = new Creator<VoteOptionContent>() {
        @Override
        public VoteOptionContent createFromParcel(Parcel in) {
            return new VoteOptionContent(in);
        }

        @Override
        public VoteOptionContent[] newArray(int size) {
            return new VoteOptionContent[size];
        }
    };
}
