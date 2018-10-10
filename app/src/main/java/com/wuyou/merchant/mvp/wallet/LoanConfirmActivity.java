package com.wuyou.merchant.mvp.wallet;

import android.os.Bundle;
import android.view.View;

import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.utils.ToastUtils;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.R;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.WalletApis;
import com.wuyou.merchant.view.activity.BaseActivity;

import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by solang on 2018/3/22.
 */

public class LoanConfirmActivity extends BaseActivity {
    @Override
    protected int getContentLayout() {
        return R.layout.activity_loan_confirm;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {

    }

    @OnClick({ R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                loan();
                break;
        }
    }

    private void loan() {
        showLoadingDialog();
        CarefreeRetrofit.getInstance().createApi(WalletApis.class)
                .loan(QueryMapBuilder.getIns()
                        .put("shop_id", CarefreeDaoSession.getInstance().getUserInfo().getUid())
                        .put("amount", CarefreeDaoSession.getInstance().getUserInfo().getUid())
                        .put("stage_number", CarefreeDaoSession.getInstance().getUserInfo().getUid())
                        .put("information", CarefreeDaoSession.getInstance().getUserInfo().getUid())
                        .buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        ToastUtils.ToastMessage(getCtx(),"借款成功！");
                    }
                });
    }
}
