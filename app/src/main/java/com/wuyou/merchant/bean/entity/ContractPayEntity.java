package com.wuyou.merchant.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by DELL on 2018/3/29.
 */

public class ContractPayEntity implements Parcelable {
    public String type_id;
    public String type_name;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type_id);
        dest.writeString(this.type_name);
    }

    public ContractPayEntity() {
    }

    protected ContractPayEntity(Parcel in) {
        this.type_id = in.readString();
        this.type_name = in.readString();
    }

    public static final Parcelable.Creator<ContractPayEntity> CREATOR = new Parcelable.Creator<ContractPayEntity>() {
        @Override
        public ContractPayEntity createFromParcel(Parcel source) {
            return new ContractPayEntity(source);
        }

        @Override
        public ContractPayEntity[] newArray(int size) {
            return new ContractPayEntity[size];
        }
    };
}
