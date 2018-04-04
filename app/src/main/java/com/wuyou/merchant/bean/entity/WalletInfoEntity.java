package com.wuyou.merchant.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hjn on 2018/2/22.
 */

public class WalletInfoEntity implements Parcelable {
    public String shop_id;
    public String score;
    public String total_amount;
    public String available_amount;
    public String used_amount;
    public String frozen_amount;
    public String payable_amount;
    public float income;

    public WalletInfoEntity() {

    }

    public WalletInfoEntity(float income) {
        this.income = income;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.shop_id);
        dest.writeString(this.score);
        dest.writeString(this.total_amount);
        dest.writeString(this.available_amount);
        dest.writeString(this.used_amount);
        dest.writeString(this.frozen_amount);
        dest.writeString(this.payable_amount);
        dest.writeFloat(this.income);
    }

    protected WalletInfoEntity(Parcel in) {
        this.shop_id = in.readString();
        this.score = in.readString();
        this.total_amount = in.readString();
        this.available_amount = in.readString();
        this.used_amount = in.readString();
        this.frozen_amount = in.readString();
        this.payable_amount = in.readString();
        this.income = in.readFloat();
    }

    public static final Creator<WalletInfoEntity> CREATOR = new Creator<WalletInfoEntity>() {
        @Override
        public WalletInfoEntity createFromParcel(Parcel source) {
            return new WalletInfoEntity(source);
        }

        @Override
        public WalletInfoEntity[] newArray(int size) {
            return new WalletInfoEntity[size];
        }
    };
}
