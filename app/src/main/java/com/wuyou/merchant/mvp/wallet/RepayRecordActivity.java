package com.wuyou.merchant.mvp.wallet;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.RepayRecordListRvAdapter;
import com.wuyou.merchant.bean.entity.ContractEntity;
import com.wuyou.merchant.view.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Solang on 2018/3/21.
 */

public class RepayRecordActivity extends BaseActivity {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    List<ContractEntity> data = new ArrayList();
    RepayRecordListRvAdapter adapter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_repay_record;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        adapter = new RepayRecordListRvAdapter(R.layout.item_repay_record, data);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(adapter);
    }

}
