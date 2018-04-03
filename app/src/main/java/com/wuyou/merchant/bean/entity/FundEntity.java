package com.wuyou.merchant.bean.entity;

import java.util.List;

/**
 * Created by solang on 2018/2/8.
 */

public class FundEntity {
    public String fund_id;
    public String fund_name;
    public String start_at;
    public String desc;
    public String rate;
    public int status;
    public List<String> tags;
    public List<FundRateEntity> rates;
}
