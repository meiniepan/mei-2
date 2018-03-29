package com.wuyou.merchant.mvp.circle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.utils.ToastUtils;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.ServeChooseAdapter;
import com.wuyou.merchant.bean.entity.ResponseListEntity;
import com.wuyou.merchant.bean.entity.ServiceEntity;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.CircleApis;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.view.activity.BaseActivity;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by DELL on 2018/3/29.
 */

public class ServeChooseActivity extends BaseActivity {
    @BindView(R.id.serve_choose_list)
    RecyclerView recyclerView;
    private List<ServiceEntity> list;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_serve_choose;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        showLoadingDialog();
        CarefreeRetrofit.getInstance().createApi(CircleApis.class)
                .getServiceList(getIntent().getStringExtra(Constant.SHOP_ID), QueryMapBuilder.getIns().buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<ResponseListEntity<ServiceEntity>>>() {
                    @Override
                    public void onSuccess(BaseResponse<ResponseListEntity<ServiceEntity>> responseListEntityBaseResponse) {
                        list = responseListEntityBaseResponse.data.list;
                        setData();
                    }
                });
        findViewById(R.id.serve_choose_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPos == -1) {
                    ToastUtils.ToastMessage(getCtx(), "请选择服务");
                    return;
                }

                Intent intent = new Intent();
                intent.putExtra(Constant.SERVE_BEAN, list.get(selectPos));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public void setData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(CommonUtil.getRecyclerDivider(this));
        ServeChooseAdapter adapter = new ServeChooseAdapter(R.layout.item_serve_choose, list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((a, view, position) -> {
            adapter.setSelectedPos(position);
            selectPos = position;
        });
    }

    private int selectPos = -1;
}
