package com.wuyou.merchant.adapter;

import android.support.annotation.Nullable;

import com.gs.buluo.common.utils.TribeDateUtils;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.ContractEntity;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseHolder;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseQuickAdapter;

import java.util.Date;
import java.util.List;

/**
 * Created by solang on 2018/2/5.
 */

public class JoinedContractListRvAdapter extends BaseQuickAdapter<ContractEntity, BaseHolder> {

    public JoinedContractListRvAdapter(int layoutResId, @Nullable List<ContractEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseHolder helper, ContractEntity item) {
        String[] s = {"已过期","进行中"};
        int i = Integer.parseInt(item.status);
//        String create_time = TribeDateUtils.dateFormat(new Date(Long.parseLong(item.created_at)*1000));
//        String end_time = TribeDateUtils.dateFormat(new Date(Long.parseLong(item.end_at)*1000));
//        String joined_time = TribeDateUtils.dateFormat(new Date(Long.parseLong(item.joined_at)*1000));
//        helper.setText(R.id.tv_name, item.contract_name)
//                .setText(R.id.tv_code, item.contract_id)
//                .setText(R.id.tv_start_time, create_time)
//                .setText(R.id.tv_end_time, end_time)
//                .setText(R.id.tv_joined_time, joined_time)
//                .setText(R.id.tv_status, s[i]);
    }
}
