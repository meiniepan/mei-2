package com.wuyou.merchant.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by solang on 2018/2/8.
 */

public class ContractEntity implements Parcelable {
    public String shop_id;
    public String shop_name;
    public String end_at;
    public String contract_name;
    public String contact_address;
    public String mobile;
    public int pay_type;
    public float price;
    public ServiceEntity service;
    public String information;
    public String contract_id;
    public String created_at;
    public int status;
    public String member_quantity;
    public long joined_at;
    public String certification;
    public String contract_number;
    public String type;
    public ContractMerchantEntity shop;


    public ContractEntity() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.shop_id);
        dest.writeString(this.shop_name);
        dest.writeString(this.end_at);
        dest.writeString(this.contract_name);
        dest.writeString(this.contact_address);
        dest.writeString(this.mobile);
        dest.writeInt(this.pay_type);
        dest.writeFloat(this.price);
        dest.writeParcelable(this.service, flags);
        dest.writeString(this.information);
        dest.writeString(this.contract_id);
        dest.writeString(this.created_at);
        dest.writeInt(this.status);
        dest.writeString(this.member_quantity);
        dest.writeLong(this.joined_at);
        dest.writeString(this.certification);
        dest.writeString(this.contract_number);
        dest.writeString(this.type);
        dest.writeParcelable(this.shop, flags);
    }

    protected ContractEntity(Parcel in) {
        this.shop_id = in.readString();
        this.shop_name = in.readString();
        this.end_at = in.readString();
        this.contract_name = in.readString();
        this.contact_address = in.readString();
        this.mobile = in.readString();
        this.pay_type = in.readInt();
        this.price = in.readFloat();
        this.service = in.readParcelable(ServiceEntity.class.getClassLoader());
        this.information = in.readString();
        this.contract_id = in.readString();
        this.created_at = in.readString();
        this.status = in.readInt();
        this.member_quantity = in.readString();
        this.joined_at = in.readLong();
        this.certification = in.readString();
        this.contract_number = in.readString();
        this.type = in.readString();
        this.shop = in.readParcelable(ContractMerchantEntity.class.getClassLoader());
    }

    public static final Creator<ContractEntity> CREATOR = new Creator<ContractEntity>() {
        @Override
        public ContractEntity createFromParcel(Parcel source) {
            return new ContractEntity(source);
        }

        @Override
        public ContractEntity[] newArray(int size) {
            return new ContractEntity[size];
        }
    };
}
