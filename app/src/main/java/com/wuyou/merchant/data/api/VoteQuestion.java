package com.wuyou.merchant.data.api;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by DELL on 2018/10/10.
 */

public class VoteQuestion implements Parcelable {
    public String question;
    public List<VoteOptionContent> option;
    public int single = 0;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.question);
        dest.writeTypedList(this.option);
        dest.writeInt(this.single);
    }

    public VoteQuestion() {
    }

    protected VoteQuestion(Parcel in) {
        this.question = in.readString();
        this.option = in.createTypedArrayList(VoteOptionContent.CREATOR);
        this.single = in.readInt();
    }

    public static final Parcelable.Creator<VoteQuestion> CREATOR = new Parcelable.Creator<VoteQuestion>() {
        @Override
        public VoteQuestion createFromParcel(Parcel source) {
            return new VoteQuestion(source);
        }

        @Override
        public VoteQuestion[] newArray(int size) {
            return new VoteQuestion[size];
        }
    };
}
