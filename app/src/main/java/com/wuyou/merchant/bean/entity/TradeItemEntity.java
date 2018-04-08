package com.wuyou.merchant.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by DELL on 2018/4/4.
 */

public class TradeItemEntity implements Parcelable {

    /**
     * order_id : 26
     * order_number : 20180330090824451310
     * shop_name : 碧桂园控股集团
     * merchant_name : 智能家政服务
     * total_amount : 6
     * pay_time : 0
     * type : 1
     */
    public String contract_name;
    public String contract_number;
    public String service_name;
    public float price;
    public int number;
    public float amount;
    public String order_id;
    public String order_number;
    public String shop_name;
    public String merchant_name;
    public int total_amount;
    public long pay_time;
    public int type;
    public String buyer;
    public List<TradeEntity> transactions;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.contract_name);
        dest.writeString(this.contract_number);
        dest.writeString(this.service_name);
        dest.writeFloat(this.price);
        dest.writeInt(this.number);
        dest.writeFloat(this.amount);
        dest.writeString(this.order_id);
        dest.writeString(this.order_number);
        dest.writeString(this.shop_name);
        dest.writeString(this.merchant_name);
        dest.writeInt(this.total_amount);
        dest.writeLong(this.pay_time);
        dest.writeInt(this.type);
        dest.writeString(this.buyer);
        dest.writeTypedList(this.transactions);
    }

    public TradeItemEntity() {
    }

    protected TradeItemEntity(Parcel in) {
        this.contract_name = in.readString();
        this.contract_number = in.readString();
        this.service_name = in.readString();
        this.price = in.readFloat();
        this.number = in.readInt();
        this.amount = in.readFloat();
        this.order_id = in.readString();
        this.order_number = in.readString();
        this.shop_name = in.readString();
        this.merchant_name = in.readString();
        this.total_amount = in.readInt();
        this.pay_time = in.readLong();
        this.type = in.readInt();
        this.buyer = in.readString();
        this.transactions = in.createTypedArrayList(TradeEntity.CREATOR);
    }

    public static final Parcelable.Creator<TradeItemEntity> CREATOR = new Parcelable.Creator<TradeItemEntity>() {
        @Override
        public TradeItemEntity createFromParcel(Parcel source) {
            return new TradeItemEntity(source);
        }

        @Override
        public TradeItemEntity[] newArray(int size) {
            return new TradeItemEntity[size];
        }
    };
}
