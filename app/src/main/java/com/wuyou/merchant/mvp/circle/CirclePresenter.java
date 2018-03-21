package com.wuyou.merchant.mvp.circle;

import com.gs.buluo.common.network.ApiException;
import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.bean.entity.ContractEntity;
import com.wuyou.merchant.bean.entity.OrderInfoListEntity;
import com.wuyou.merchant.bean.entity.PartnerListEntity;
import com.wuyou.merchant.bean.entity.WorkerListEntity;
import com.wuyou.merchant.mvp.order.OrderContract;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.CircleApis;
import com.wuyou.merchant.network.apis.OrderApis;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018\1\29 0029.
 */

public class CirclePresenter extends CircleContract.Presenter {
    String lastId;


    @Override
    void getSponsorAlliance() {
//        CarefreeRetrofit.getInstance().createApi(OrderApis.class)
//                .getDispatchMerchantInfo(CarefreeApplication.getInstance().getUserInfo().getUid(), "launch", QueryMapBuilder.getIns().buildGet())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseSubscriber<BaseResponse<WorkerListEntity>>() {
//                    @Override
//                    public void onSuccess(BaseResponse<WorkerListEntity> response) {
//                        if (isAttach()) mView.getSuccess(response.data);
//                    }
//
//                    @Override
//                    protected void onFail(ApiException e) {
//                        if (isAttach()) mView.showError(e.getDisplayMessage(),e.getCode());
//                    }
//                });
    }

    @Override
    void getJoinedAlliance() {
//        CarefreeRetrofit.getInstance().createApi(OrderApis.class)
//                .getDispatchMerchantInfo(CarefreeApplication.getInstance().getUserInfo().getUid(), "join", QueryMapBuilder.getIns().buildGet())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseSubscriber<BaseResponse<WorkerListEntity>>() {
//                    @Override
//                    public void onSuccess(BaseResponse<WorkerListEntity> response) {
//                        if (isAttach()) mView.getSuccess(response.data);
//                    }
//
//                    @Override
//                    protected void onFail(ApiException e) {
//                        if (isAttach()) mView.showError(e.getDisplayMessage(),e.getCode());
//                    }
//                });
    }

    @Override
    void getPrepareMerchants() {
//        CarefreeRetrofit.getInstance().createApi(OrderApis.class)
//                .getPrepareMerchantInfo(CarefreeApplication.getInstance().getUserInfo().getUid(), "0","1", QueryMapBuilder.getIns().buildGet())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseSubscriber<BaseResponse<WorkerListEntity>>() {
//                    @Override
//                    public void onSuccess(BaseResponse<WorkerListEntity> response) {
//                        if (response.data.list.size() > 0)
//                            lastId = response.data.list.get(response.data.list.size() - 1).id;
//                        if (isAttach()) mView.getSuccess(response.data);
//                    }
//
//                    @Override
//                    protected void onFail(ApiException e) {
//                        if (isAttach()) mView.showError(e.getDisplayMessage(),e.getCode());
//                    }
//                });
    }

    @Override
    void loadMore() {
//        CarefreeRetrofit.getInstance().createApi(OrderApis.class)
//                .getPrepareMerchantInfo(CarefreeApplication.getInstance().getUserInfo().getUid(), lastId,"2", QueryMapBuilder.getIns().buildGet())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseSubscriber<BaseResponse<WorkerListEntity>>() {
//                    @Override
//                    public void onSuccess(BaseResponse<WorkerListEntity> response) {
//                        if (response.data.list.size() > 0)
//                            lastId = response.data.list.get(response.data.list.size() - 1).id;
//                        if (isAttach()) mView.getMore(response.data);
//                    }
//
//                    @Override
//                    protected void onFail(ApiException e) {
//                        if (isAttach()) mView.loadMoreError(e.getCode());
//                    }
//                });
    }

    @Override
    void createContract(ContractEntity entity) {
//        CarefreeRetrofit.getInstance().createApi(CircleApis.class)
//                .createContract(QueryMapBuilder.getIns().put("shop_id", entity.shop_id)
//                        .put("shop_name", entity.shop_name)
//                        .put("end_at", entity.end_at)
//                        .put("contract_id", entity.id)
//                        .put("contract_id", entity.id)
//                        .put("contract_id", entity.id)
//                        .put("contract_id", entity.id)
//                        .put("contract_id", entity.id)
//                        .put("contract_id", entity.id)
//                        .put("contract_id", entity.id)
//                        .buildPost())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseSubscriber<BaseResponse<WorkerListEntity>>() {
//                    @Override
//                    public void onSuccess(BaseResponse<WorkerListEntity> response) {
//                        if (isAttach()) mView.getSuccess(response.data);
//                    }
//
//                    @Override
//                    protected void onFail(ApiException e) {
//                        if (isAttach()) mView.showError(e.getDisplayMessage(),e.getCode());
//                    }
//                });
    }
}
