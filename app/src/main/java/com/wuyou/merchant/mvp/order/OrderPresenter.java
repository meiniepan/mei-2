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
    String lastId;

    @Override
    void getOrders(String merchant_id, String status) {
        CarefreeRetrofit.getInstance().createApi(OrderApis.class)
                .getOrders(QueryMapBuilder.getIns().put("shop_id",merchant_id).put("start_id","0").put("size","10").put("flag","1").put("status",status).buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<OrderInfoListEntity>>() {
                    @Override
                    public void onSuccess(BaseResponse<OrderInfoListEntity> userInfoBaseResponse) {
                        if (userInfoBaseResponse.data.list.size() > 0)
                            lastId = userInfoBaseResponse.data.list.get(userInfoBaseResponse.data.list.size() - 1).order_id;
                        if (isAttach()) mView.getSuccess(userInfoBaseResponse.data);
                    }


                    @Override
                    protected void onFail(ApiException e) {
                        if (isAttach()) mView.showError(e.getDisplayMessage(), e.getCode());
                    }
                });
    }

    @Override
    void getAllianceOrders(String merchant_id, String status) {
        CarefreeRetrofit.getInstance().createApi(OrderApis.class)
                .getAllianceOrders(merchant_id, status, "0", "1", QueryMapBuilder.getIns().buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<OrderInfoListEntity>>() {
                    @Override
                    public void onSuccess(BaseResponse<OrderInfoListEntity> userInfoBaseResponse) {
                        if (userInfoBaseResponse.data.list.size() > 0)
                            lastId = userInfoBaseResponse.data.list.get(userInfoBaseResponse.data.list.size() - 1).order_id;
                        if (isAttach()) mView.getSuccess(userInfoBaseResponse.data);
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        if (isAttach()) mView.showError(e.getDisplayMessage(), e.getCode());
                    }
                });
    }

    @Override
    void loadMore(String merchant_id, String status) {
        CarefreeRetrofit.getInstance().createApi(OrderApis.class)
                .getOrders(QueryMapBuilder.getIns().put("shop_id",merchant_id).put("start_id",lastId).put("size","10").put("flag","2").put("status",status).buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<OrderInfoListEntity>>() {
                    @Override
                    public void onSuccess(BaseResponse<OrderInfoListEntity> userInfoBaseResponse) {
                        if (userInfoBaseResponse.data.list.size() > 0)
                            lastId = userInfoBaseResponse.data.list.get(userInfoBaseResponse.data.list.size() - 1).order_id;
                        if (isAttach()) mView.getMore(userInfoBaseResponse.data);
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        if (isAttach())mView.loadMoreError(e.getCode());
                    }
                });
    }

    @Override
    void loadAllianceMore(String merchant_id, String status) {
        CarefreeRetrofit.getInstance().createApi(OrderApis.class)
                .getAllianceOrders(merchant_id, status, lastId, "2", QueryMapBuilder.getIns().buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<OrderInfoListEntity>>() {
                    @Override
                    public void onSuccess(BaseResponse<OrderInfoListEntity> userInfoBaseResponse) {
                        if (userInfoBaseResponse.data.list.size() > 0)
                            lastId = userInfoBaseResponse.data.list.get(userInfoBaseResponse.data.list.size() - 1).order_id;
                        if (isAttach()) mView.getMore(userInfoBaseResponse.data);
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        if (isAttach())mView.loadMoreError(e.getCode());
                    }
                });
    }


}
