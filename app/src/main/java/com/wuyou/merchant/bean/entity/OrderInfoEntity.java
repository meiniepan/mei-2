package com.wuyou.merchant.bean.entity;

/**
 * Created by solang on 2018/2/8.
 */

public class OrderInfoEntity {
    public String order_id;
    public long created_at;
    public String price;
    public String service_time;
    public String service_date;
    public String status;
    public String order_no;
    public String voucher;
    public ServiceEntity service;
    public WorkerEntity worker;
    public AddressEntity address;
}
