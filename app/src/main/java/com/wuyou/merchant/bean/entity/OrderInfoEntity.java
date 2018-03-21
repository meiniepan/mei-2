package com.wuyou.merchant.bean.entity;

/**
 * Created by solang on 2018/2/8.
 */

public class OrderInfoEntity {
    public String order_id;
    public long created_at;
    public String price;
    public String service_time;
    public String service_data;
    public String status;
    public ServiceEntity service;
    public WorkerEntity worker;
    public AddressEntity address;
}
