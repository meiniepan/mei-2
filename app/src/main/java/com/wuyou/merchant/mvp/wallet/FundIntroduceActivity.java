package com.wuyou.merchant.mvp.wallet;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.utils.ToastUtils;
import com.gs.buluo.common.utils.TribeDateUtils;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.FundRateListRvAdapter;
import com.wuyou.merchant.bean.entity.FundEntity;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.WalletApis;
import com.wuyou.merchant.view.activity.BaseActivity;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by solang on 2018/2/9.
 */

public class FundIntroduceActivity extends BaseActivity {
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    FundRateListRvAdapter adapter;
    String id;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_fund_introduce;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        id = getIntent().getStringExtra(Constant.FUND_ID);
        if (!TextUtils.isEmpty(id))
        getData(id);
    }

    private void getData(String id) {
        showLoadingDialog();
        CarefreeRetrofit.getInstance().createApi(WalletApis.class)
                .getFundDetail(id,
                        QueryMapBuilder.getIns()
                                .buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<FundEntity>>() {
                    @Override
                    public void onSuccess(BaseResponse<FundEntity> response) {
                        initUI(response.data);
                    }
                });
    }

    private void initUI(FundEntity data) {
        adapter = new FundRateListRvAdapter(R.layout.item_fund_rate, data.rates);
        recyclerView.setLayoutManager(new LinearLayoutManager(getCtx()));
        recyclerView.setAdapter(adapter);
        String s = TribeDateUtils.SDF5.format(new Date(Long.parseLong(data.start_at)*1000));
        name.setText(data.fund_name);
        tvTime.setText(s);
        description.setText(data.description);
    }

    @OnClick(R.id.btn_apply)
    public void onViewClicked() {
        ToastUtils.ToastMessage(getCtx(),"暂未开通！");
    }
}
