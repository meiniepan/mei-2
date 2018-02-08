package com.wuyou.merchant.mvp.order;

import com.gs.buluo.common.network.ApiException;
import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.widget.LoadingDialog;
import com.wuyou.merchant.bean.entity.OrderInfoListEntity;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.OrderApis;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018\1\29 0029.
 */

public class OrderPresenter extends OrderContract.Presenter {
    @Override
    void getOrders(int merchant_id, int status,int start_id, int flag) {
        CarefreeRetrofit.getInstance().createApi(OrderApis.class)
                .getOrders(merchant_id,status,start_id,flag,QueryMapBuilder.getIns().buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<OrderInfoListEntity>>() {
                    @Override
                    public void onSuccess(BaseResponse<OrderInfoListEntity> userInfoBaseResponse) {
                        mView.getSuccess();
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        mView.showError(e.getDisplayMessage(), e.getCode());
                    }
                });
    }


}
