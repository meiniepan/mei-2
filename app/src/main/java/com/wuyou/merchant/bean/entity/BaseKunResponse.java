package com.wuyou.merchant.bean.entity;

/**
 * Created by Solang on 2019/1/9.
 */

public class BaseKunResponse<T> {
    public int code;
    public T data;
    public String message;
}
