package com.wuyou.merchant.mvp.circle;

import android.os.Bundle;

import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.utils.TribeDateUtils;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.ContractContentRvAdapter;
import com.wuyou.merchant.bean.entity.ContractContentEntity;
import com.wuyou.merchant.bean.entity.ContractDetailEntity;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.OrderApis;
import com.wuyou.merchant.view.activity.BaseActivity;

import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by solang on 2018/3/20.
 */

public class CreatedContractDetailActivity extends BaseActivity {

    String id;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_created_contract_detail;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        id = getIntent().getStringExtra(Constant.CONTRACT_ID);
        initData();
    }

    private void initData() {
        showLoadingDialog();
        CarefreeRetrofit.getInstance().createApi(OrderApis.class)
                .getContractDetail(id, QueryMapBuilder.getIns().buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<ContractDetailEntity>>() {
                    @Override
                    public void onSuccess(BaseResponse<ContractDetailEntity> response) {
                        initUI(response.data);
                    }

                });
    }

    private void initUI(ContractDetailEntity data) {
        String b_time = TribeDateUtils.dateFormat5(new Date(data.start_at * 1000));
        String e_time = TribeDateUtils.dateFormat5(new Date(data.end_at * 1000));

        if (data.content != null) {
            initContentList(data.content);
        }
    }

    private void initContentList(List<ContractContentEntity> content) {
        ContractContentRvAdapter adapter = new ContractContentRvAdapter(R.layout.item_contract_content,content);
//        rvContent.setLayoutManager(new LinearLayoutManager(this));
//        rvContent.setAdapter(adapter);
    }


}
