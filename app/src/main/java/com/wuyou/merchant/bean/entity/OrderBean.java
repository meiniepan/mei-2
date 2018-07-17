package com.wuyou.merchant.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by hjn on 2018/2/6.
 */

public class OrderBean implements Parcelable {

    /**
     * order_id : 5
     * order_number : 201803190710581086238049
     * status : 1. 待付款 2.进行中 3.待评价 4已完成 5已取消
     * amount : 980
     * services : {"service_id":1,"service_name":"空调清洗","photo":"http://images4.5maiche.cn/2016-07-11_57833334133a7.jpg"}
     * shop : {"shop_id":2,"shop_name":"重庆鸡公煲"}
     */

    public String order_id;
    public String order_no;
    public int status;
    public String serial;
    public float amount;
    public ShopBean shop;
    public int can_finish;
    public float second_payment;

    public OrderBean() {
    }

    protected OrderBean(Parcel in) {
        order_id = in.readString();
        order_no = in.readString();
        status = in.readInt();
        serial = in.readString();
        amount = in.readFloat();
        shop = in.readParcelable(ShopBean.class.getClassLoader());
        can_finish = in.readInt();
        second_payment = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(order_id);
        dest.writeString(order_no);
        dest.writeInt(status);
        dest.writeString(serial);
        dest.writeFloat(amount);
        dest.writeParcelable(shop, flags);
        dest.writeInt(can_finish);
        dest.writeFloat(second_payment);
    }

    public static final Creator<OrderBean> CREATOR = new Creator<OrderBean>() {
        @Override
        public OrderBean createFromParcel(Parcel in) {
            return new OrderBean(in);
        }

        @Override
        public OrderBean[] newArray(int size) {
            return new OrderBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


}
