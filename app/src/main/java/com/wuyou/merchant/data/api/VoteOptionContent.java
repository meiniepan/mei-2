package com.wuyou.merchant.data.api;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by DELL on 2018/10/11.
 */

public class VoteOptionContent implements Parcelable {
   public String optioncontent;
    public float number;
    public int id;
    public boolean isChecked;
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
        dest.writeFloat(this.number);
        dest.writeInt(this.id);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }

    protected VoteOptionContent(Parcel in) {
        this.optioncontent = in.readString();
        this.number = in.readFloat();
        this.id = in.readInt();
        this.isChecked = in.readByte() != 0;
    }

    public static final Creator<VoteOptionContent> CREATOR = new Creator<VoteOptionContent>() {
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
