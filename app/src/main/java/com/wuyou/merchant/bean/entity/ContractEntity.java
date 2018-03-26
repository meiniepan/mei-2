package com.wuyou.merchant.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by solang on 2018/2/8.
 */

public class ContractEntity implements Parcelable{
    public String shop_id;
    public String shop_name;
    public String end_at;
    public String contract_name;
    public String contact_address;
    public String mobile;
    public String total_amount;
    public String divided_amount;
    public ServiceEntity service;
    public String information;
    public String contract_id;
    public String created_at;
    public String status;
    public String member_quantity;
    public String joined_at;
    public String certification;
    public String contract_number;
    public String type;
    public ContractMerchantEntity shop;



    public ContractEntity() {

    }

    protected ContractEntity(Parcel in) {
        shop_id = in.readString();
        shop_name = in.readString();
        end_at = in.readString();
        contract_name = in.readString();
        contact_address = in.readString();
        mobile = in.readString();
        total_amount = in.readString();
        divided_amount = in.readString();
        contract_id = in.readString();
        created_at = in.readString();
        status = in.readString();
        member_quantity = in.readString();
        joined_at = in.readString();
        certification = in.readString();
        contract_number = in.readString();
    }

    public static final Creator<ContractEntity> CREATOR = new Creator<ContractEntity>() {
        @Override
        public ContractEntity createFromParcel(Parcel in) {
            return new ContractEntity(in);
        }

        @Override
        public ContractEntity[] newArray(int size) {
            return new ContractEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shop_id);
        dest.writeString(shop_name);
        dest.writeString(end_at);
        dest.writeString(contract_name);
        dest.writeString(contact_address);
        dest.writeString(mobile);
        dest.writeString(total_amount);
        dest.writeString(divided_amount);
        dest.writeString(contract_id);
        dest.writeString(created_at);
        dest.writeString(status);
        dest.writeString(member_quantity);
        dest.writeString(joined_at);
        dest.writeString(certification);
        dest.writeString(contract_number);
    }
}
