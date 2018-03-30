package com.wuyou.merchant.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by solang on 2018/2/8.
 */

public class ContractMerchantEntity implements Parcelable {
    public String shop_id;
    public String shop_name;
    public String contact_address;
    public String mobile;
    public String logo;
    public String license;
    public String other_image;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.shop_id);
        dest.writeString(this.shop_name);
        dest.writeString(this.contact_address);
        dest.writeString(this.mobile);
        dest.writeString(this.license);
        dest.writeString(this.other_image);
        dest.writeString(this.logo);
    }

    public ContractMerchantEntity() {
    }

    protected ContractMerchantEntity(Parcel in) {
        this.shop_id = in.readString();
        this.shop_name = in.readString();
        this.contact_address = in.readString();
        this.mobile = in.readString();
        this.license = in.readString();
        this.other_image = in.readString();
        this.logo = in.readString();
    }

    public static final Parcelable.Creator<ContractMerchantEntity> CREATOR = new Parcelable.Creator<ContractMerchantEntity>() {
        @Override
        public ContractMerchantEntity createFromParcel(Parcel source) {
            return new ContractMerchantEntity(source);
        }

        @Override
        public ContractMerchantEntity[] newArray(int size) {
            return new ContractMerchantEntity[size];
        }
    };
}
