package com.wuyou.merchant.bean.entity;

import java.util.List;

/**
 * Created by solang on 2018/2/8.
 */

public class KunListEntity<T> {
    public int status;
    public List<T> serviceList;
    public List<T> mechanicSet;
    public List<T> rvalList;
}
