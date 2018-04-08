package com.wuyou.merchant.bean.entity;

import java.util.List;

/**
 * Created by DELL on 2018/3/29.
 */

public class PrepareSignEntity {
    /**
     * government_subsidy : 2
     * rates : [{"stage":1,"rate":"0.5%"},{"stage":3,"rate":"0.8%"},{"stage":6,"rate":"1.1%"},{"stage":12,"rate":"1.5%"}]
     */

    public float government_subsidy;
    public List<RatesBean> rates;

    public static class RatesBean {
        /**
         * stage : 1
         * rate : 0.5%
         */

        public String stage;
        public String rate;
    }
}
