package com.wuyou.merchant.mvp.companies;

import android.support.annotation.Nullable;

import com.wuyou.merchant.bean.UserInfo;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseHolder;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseQuickAdapter;

import java.util.List;

/**
 * Created by hjn on 2018\1\26 0026.
 */

public class CompaniesAdapter extends BaseQuickAdapter<UserInfo, BaseHolder> {

    public CompaniesAdapter(int layoutResId, @Nullable List<UserInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseHolder helper, UserInfo item) {

    }
}