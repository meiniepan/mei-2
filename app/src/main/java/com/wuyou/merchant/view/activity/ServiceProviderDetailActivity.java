package com.wuyou.merchant.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.ServiceProDetailRvAdapter;
import com.wuyou.merchant.bean.entity.MerchantDetailEntity;
import com.wuyou.merchant.bean.entity.OrderInfoEntity;
import com.wuyou.merchant.bean.entity.WorkerEntity;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.OrderApis;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by solang on 2018/2/6.
 */

public class ServiceProviderDetailActivity extends BaseActivity {
    @BindView(R.id.rv_contract)
    RecyclerView recyclerView;
    List<Integer> data = new ArrayList<>();
    String id;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_service_provider_detail;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        id = getIntent().getStringExtra(Constant.MERCHANT_ID);
        initData();
        recyclerView.setLayoutManager(new LinearLayoutManager(getCtx()));
        ServiceProDetailRvAdapter adapter = new ServiceProDetailRvAdapter(R.layout.item_service_pro_detail, data);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getCtx(),ContractDetailActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        CarefreeRetrofit.getInstance().createApi(OrderApis.class)
                .getMerchantDetail(CarefreeApplication.getInstance().getUserInfo().getUid(), id, QueryMapBuilder.getIns().buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<MerchantDetailEntity>>() {
                    @Override
                    public void onSuccess(BaseResponse<MerchantDetailEntity> response) {
                        initUI(response.data);
                    }

                });
    }

    private void initUI(MerchantDetailEntity data) {
    }
}
