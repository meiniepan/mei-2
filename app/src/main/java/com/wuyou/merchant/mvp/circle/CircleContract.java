package com.wuyou.merchant.mvp.circle;


import com.wuyou.merchant.bean.entity.ContractEntity;
import com.wuyou.merchant.bean.entity.ResponseListEntity;
import com.wuyou.merchant.mvp.BasePresenter;
import com.wuyou.merchant.mvp.IBaseView;

/**
 * Created by Administrator on 2018\1\29 0029.
 */

public interface CircleContract {
    interface View extends IBaseView {
        void getSuccess(ResponseListEntity<ContractEntity> data);

        void getMore(ResponseListEntity<ContractEntity> data);

        void loadMoreError(int code);
    }

    abstract class Presenter extends BasePresenter<View> {
        abstract void getCreatedContract();

        abstract void getContractMarket();

        abstract void loadCreatedContractMore();

        abstract void loadMarketMore();

        abstract void getJoinedContract();

        abstract void loadJoinedContractMore();
    }
}
