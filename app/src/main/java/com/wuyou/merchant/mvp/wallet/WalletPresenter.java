package com.wuyou.merchant.mvp.wallet;

import com.gs.buluo.common.network.ApiException;
import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.bean.entity.FundEntity;
import com.wuyou.merchant.bean.entity.RepayRecordEntity;
import com.wuyou.merchant.bean.entity.ResponseListEntity;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.WalletApis;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018\1\29 0029.
 */

public class WalletPresenter extends WalletContract.Presenter {
    String lastId_list;
    String lastId_repay_record;


    @Override
    void getFundList() {
        CarefreeRetrofit.getInstance().createApi(WalletApis.class)
                .getFundList(QueryMapBuilder.getIns()
                        .put("start_id", "0")
                        .put("flag", "1")
                        .put("size", "10")
                        .buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<ResponseListEntity<FundEntity>>>() {
                    @Override
                    public void onSuccess(BaseResponse<ResponseListEntity<FundEntity>> response) {
                        if (response.data.list.size() > 0)
                            lastId_list = response.data.list.get(response.data.list.size() - 1).fund_id;
                        if (isAttach()) mView.getSuccess(response.data);
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        if (isAttach()) mView.showError(e.getDisplayMessage(), e.getCode());
                    }
                });
    }


    @Override
    void loadMore() {
        CarefreeRetrofit.getInstance().createApi(WalletApis.class)
                .getFundList(QueryMapBuilder.getIns()
                        .put("start_id", lastId_list)
                        .put("flag", "2")
                        .put("size", "10")
                        .buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<ResponseListEntity<FundEntity>>>() {
                    @Override
                    public void onSuccess(BaseResponse<ResponseListEntity<FundEntity>> response) {
                        if (response.data.list.size() > 0)
                            lastId_list = response.data.list.get(response.data.list.size() - 1).fund_id;
                        if (isAttach()) mView.getSuccess(response.data);
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        if (isAttach()) mView.showError(e.getDisplayMessage(), e.getCode());
                    }
                });
    }

    @Override
    void getRepayRecordsList() {
        CarefreeRetrofit.getInstance().createApi(WalletApis.class)
                .getRepayRecords(CarefreeApplication.getInstance().getUserInfo().getUid(),
                        QueryMapBuilder.getIns()
                                .put("start_id", "0")
                                .put("flag", "1")
                                .put("size", "10")
                                .buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<ResponseListEntity<RepayRecordEntity>>>() {
                    @Override
                    public void onSuccess(BaseResponse<ResponseListEntity<RepayRecordEntity>> response) {
                        if (response.data.list.size() > 0)
                            lastId_repay_record = response.data.list.get(response.data.list.size() - 1).record_id;
                        if (isAttach()) mView.getSuccess(response.data);
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        if (isAttach()) mView.showError(e.getDisplayMessage(), e.getCode());
                    }
                });
    }

    @Override
    void loadMoreRepayRecords() {
        CarefreeRetrofit.getInstance().createApi(WalletApis.class)
                .getRepayRecords(CarefreeApplication.getInstance().getUserInfo().getUid(),
                        QueryMapBuilder.getIns()
                                .put("start_id", lastId_repay_record)
                                .put("flag", "2")
                                .put("size", "10")
                                .buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<ResponseListEntity<RepayRecordEntity>>>() {
                    @Override
                    public void onSuccess(BaseResponse<ResponseListEntity<RepayRecordEntity>> response) {
                        if (response.data.list.size() > 0)
                            lastId_repay_record = response.data.list.get(response.data.list.size() - 1).record_id;
                        if (isAttach()) mView.getSuccess(response.data);
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        if (isAttach()) mView.showError(e.getDisplayMessage(), e.getCode());
                    }
                });
    }


}
