package com.wuyou.merchant.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by solang on 2018/2/8.
 */

public class TradeEntity implements Parcelable {

    /**
     * from : 智能家政服务
     * to : 碧桂园控股集团
     * transaction_id : 6cbe7bc9d81b389beac4451d4f4bd5adf8cfd40cebd233f18fcc126c1be4bd96
     * amount : 100
     * confirmations : 779
     * time : 1522400907
     * timereceived : 1522400907
     * fee : 0
     */

    public String from;
    public String to;
    public String transaction_id;
    public float amount;
    public int confirmations;
    public long time;
    public long timereceived;
    public float fee;
    public String txid;
    public String pow;

    public TradeEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.from);
        dest.writeString(this.to);
        dest.writeString(this.transaction_id);
        dest.writeFloat(this.amount);
        dest.writeInt(this.confirmations);
        dest.writeLong(this.time);
        dest.writeLong(this.timereceived);
        dest.writeFloat(this.fee);
        dest.writeString(this.txid);
        dest.writeString(this.pow);
    }

    protected TradeEntity(Parcel in) {
        this.from = in.readString();
        this.to = in.readString();
        this.transaction_id = in.readString();
        this.amount = in.readFloat();
        this.confirmations = in.readInt();
        this.time = in.readLong();
        this.timereceived = in.readLong();
        this.fee = in.readFloat();
        this.txid = in.readString();
        this.pow = in.readString();
    }

    public static final Creator<TradeEntity> CREATOR = new Creator<TradeEntity>() {
        @Override
        public TradeEntity createFromParcel(Parcel source) {
            return new TradeEntity(source);
        }

        @Override
        public TradeEntity[] newArray(int size) {
            return new TradeEntity[size];
        }
    };
}
