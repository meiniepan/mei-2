package com.wuyou.merchant.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by solang on 2018/5/24.
 */

public class OfficialEntity implements Parcelable{
    public String name;
    public String corporation;
    public String code;
    public String registered_address;
    public String license;
    public int status;

    protected OfficialEntity(Parcel in) {
        name = in.readString();
        corporation = in.readString();
        code = in.readString();
        registered_address = in.readString();
        license = in.readString();
        status = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(corporation);
        dest.writeString(code);
        dest.writeString(registered_address);
        dest.writeString(license);
        dest.writeInt(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OfficialEntity> CREATOR = new Creator<OfficialEntity>() {
        @Override
        public OfficialEntity createFromParcel(Parcel in) {
            return new OfficialEntity(in);
        }

        @Override
        public OfficialEntity[] newArray(int size) {
            return new OfficialEntity[size];
        }
    };
}
