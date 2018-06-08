package com.wuyou.merchant.mvp.circle;

import com.gs.buluo.common.network.ApiException;
import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.bean.entity.ContractEntity;
import com.wuyou.merchant.bean.entity.ResponseListEntity;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.CircleApis;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018\1\29 0029.
 */

public class CirclePresenter extends CircleContract.Presenter {
    String lastId_list;
    String lastId_market;


    @Override
    void getCreatedContract( ) {
        CarefreeRetrofit.getInstance().createApi(CircleApis.class)
                .getMyCreatedContractList(CarefreeDaoSession.getInstance().getUserInfo().getShop_id(), QueryMapBuilder.getIns().put("start_id", "0")
                        .put("flag", "1")
                        .put("size", "10")
                        .buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<ResponseListEntity<ContractEntity>>>() {
                    @Override
                    public void onSuccess(BaseResponse<ResponseListEntity<ContractEntity>> response) {
                        if (response.data.list.size() > 0)
                            lastId_list = response.data.list.get(response.data.list.size() - 1).contract_id;
                        if (isAttach()) mView.getSuccess(response.data);
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        if (isAttach()) mView.showError(e.getDisplayMessage(), e.getCode());
                    }
                });
    }

    @Override
    void loadCreatedContractMore() {
        CarefreeRetrofit.getInstance().createApi(CircleApis.class)
                .getMyCreatedContractList(CarefreeDaoSession.getInstance().getUserInfo().getShop_id(),
                        QueryMapBuilder.getIns().put("start_id", lastId_list)
                                .put("flag", "2")
                                .put("size", "10")
                                .buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<ResponseListEntity<ContractEntity>>>() {
                    @Override
                    public void onSuccess(BaseResponse<ResponseListEntity<ContractEntity>> response) {
                        if (response.data.list.size() > 0)
                            lastId_list = response.data.list.get(response.data.list.size() - 1).contract_id;
                        if (isAttach()) mView.getSuccess(response.data);
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        if (isAttach()) mView.showError(e.getDisplayMessage(), e.getCode());
                    }
                });
    }

    @Override
    void getJoinedContract() {
        CarefreeRetrofit.getInstance().createApi(CircleApis.class).getMyJoinedContract(CarefreeDaoSession.getInstance().getUserInfo().getShop_id(),
                QueryMapBuilder.getIns().put("start_id", "0")
                        .put("flag", "1")
                        .put("size", "10")
                        .buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<ResponseListEntity<ContractEntity>>>() {
                    @Override
                    public void onSuccess(BaseResponse<ResponseListEntity<ContractEntity>> responseListEntityBaseResponse) {
                        List<ContractEntity> list = responseListEntityBaseResponse.data.list;
                        if (list.size() > 0)
                            last_joined = list.get(list.size() - 1).contract_id;
                        mView.getSuccess(responseListEntityBaseResponse.data);
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        mView.showError(e.getDisplayMessage(), e.getCode());
                    }
                });
    }

    public String last_joined;

    @Override
    void loadJoinedContractMore() {
        CarefreeRetrofit.getInstance().createApi(CircleApis.class)
                .getContractMarket(QueryMapBuilder.getIns()
                        .put("start_id", last_joined)
                        .put("flag", "2")
                        .put("size", "10")
                        .buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<ResponseListEntity<ContractEntity>>>() {
                    @Override
                    public void onSuccess(BaseResponse<ResponseListEntity<ContractEntity>> responseListEntityBaseResponse) {
                        List<ContractEntity> list = responseListEntityBaseResponse.data.list;
                        if (list.size() > 0)
                            last_joined = list.get(list.size() - 1).contract_id;
                        if (isAttach()) mView.getSuccess(responseListEntityBaseResponse.data);
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        if (isAttach()) mView.showError(e.getDisplayMessage(), e.getCode());
                    }
                });
    }

    @Override
    void getContractMarket() {
        CarefreeRetrofit.getInstance().createApi(CircleApis.class)
                .getContractMarket(QueryMapBuilder.getIns()
                        .put("start_id", "0")
                        .put("flag", "1")
                        .put("size", "10")
                        .put("shop_id", CarefreeDaoSession.getInstance().getUserInfo().getShop_id())
                        .buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<ResponseListEntity<ContractEntity>>>() {
                    @Override
                    public void onSuccess(BaseResponse<ResponseListEntity<ContractEntity>> response) {
                        if (response.data.list.size() > 0)
                            lastId_market = response.data.list.get(response.data.list.size() - 1).contract_id;
                        if (isAttach()) mView.getSuccess(response.data);
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        if (isAttach()) mView.showError(e.getDisplayMessage(), e.getCode());
                    }
                });
    }


    @Override
    void loadMarketMore() {
        CarefreeRetrofit.getInstance().createApi(CircleApis.class)
                .getContractMarket(QueryMapBuilder.getIns()
                        .put("start_id", lastId_market)
                        .put("flag", "2")
                        .put("size", "10")
                        .put("shop_id", CarefreeDaoSession.getInstance().getUserInfo().getShop_id())
                        .buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<ResponseListEntity<ContractEntity>>>() {
                    @Override
                    public void onSuccess(BaseResponse<ResponseListEntity<ContractEntity>> response) {
                        if (response.data.list.size() > 0)
                            lastId_market = response.data.list.get(response.data.list.size() - 1).contract_id;
                        if (isAttach()) mView.getSuccess(response.data);
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        if (isAttach()) mView.showError(e.getDisplayMessage(), e.getCode());
                    }
                });
    }
}
