package com.wuyou.merchant.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hjn on 2018/2/22.
 */

public class WalletInfoEntity implements Parcelable{
    public String shop_id;
    public String score;
    public String total_amount;
    public String available_amount;
    public String used_amount;
    public String frozen_amount;
    public String payable_amount;

    public WalletInfoEntity(Parcel in) {
        shop_id = in.readString();
        score = in.readString();
        total_amount = in.readString();
        available_amount = in.readString();
        used_amount = in.readString();
        frozen_amount = in.readString();
        payable_amount = in.readString();
    }

    public static final Creator<WalletInfoEntity> CREATOR = new Creator<WalletInfoEntity>() {
        @Override
        public WalletInfoEntity createFromParcel(Parcel in) {
            return new WalletInfoEntity(in);
        }

        @Override
        public WalletInfoEntity[] newArray(int size) {
            return new WalletInfoEntity[size];
        }
    };

    public WalletInfoEntity() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shop_id);
        dest.writeString(score);
        dest.writeString(total_amount);
        dest.writeString(available_amount);
        dest.writeString(used_amount);
        dest.writeString(frozen_amount);
        dest.writeString(payable_amount);
    }
}
