package com.wuyou.merchant.mvp.store;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.widget.StatusLayout;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.StaffRvAdapter;
import com.wuyou.merchant.bean.entity.BaseKunResponse;
import com.wuyou.merchant.bean.entity.KunListEntity;
import com.wuyou.merchant.bean.entity.StaffEntity;
import com.wuyou.merchant.bean.entity.StaffReq;
import com.wuyou.merchant.network.CarefreeRetrofitNew;
import com.wuyou.merchant.network.apis.OrderApis;
import com.wuyou.merchant.view.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by DELL on 2018/12/26.
 */

public class ManagerListActivity extends BaseActivity {
    @BindView(R.id.sl_list_layout)
    StatusLayout statusLayout;
    @BindView(R.id.rv_orders)
    RecyclerView recyclerView;
    List<StaffEntity> data = new ArrayList();
    StaffRvAdapter adapter;
    String orderId;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_worker_list;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        setTitleText("管理员列表");
        statusLayout.setErrorAction(v -> getData());
        orderId = getIntent().getStringExtra(Constant.ORDER_ID);
        adapter = new StaffRvAdapter(R.layout.item_chose_artisan, data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getCtx()));
        recyclerView.setAdapter(adapter);
        getData();
    }

    private void getData() {
        StaffReq req = new StaffReq();
        req.shopId = "5c27753e8ffaedc2a6bc4b71";
        req.page = 2;
        statusLayout.showProgressView();
        CarefreeRetrofitNew.getInstance().createApi(OrderApis.class)
                .getStaffList(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseKunResponse<KunListEntity<StaffEntity>>>() {
                    @Override
                    public void onSuccess(BaseKunResponse<KunListEntity<StaffEntity>> response) {
                        statusLayout.showContentView();
                        data = response.data.rvalList;
                        adapter.setNewData(data);
                    }

                });
    }
}
