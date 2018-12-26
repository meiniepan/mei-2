package com.wuyou.merchant.bean.entity;

/**
 * Created by DELL on 2018/12/26.
 */

public class CommodityEntity {
    public String logo;
    public String title;
    public String sales;
    public String store;
    public String price;
    public String status;

    public CommodityEntity(String name) {
        title = name;
    }
}
