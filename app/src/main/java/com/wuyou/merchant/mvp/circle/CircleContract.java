package com.wuyou.merchant.mvp.circle;


import com.wuyou.merchant.bean.entity.OrderInfoListEntity;
import com.wuyou.merchant.bean.entity.WorkerListEntity;
import com.wuyou.merchant.mvp.BasePresenter;
import com.wuyou.merchant.mvp.IBaseView;

/**
 * Created by Administrator on 2018\1\29 0029.
 */

public interface CircleContract {
    interface View extends IBaseView {
        void getSuccess(WorkerListEntity data);
        void getMore(WorkerListEntity data);
        void loadMoreError(int code);
    }

    abstract class Presenter extends BasePresenter<View> {
        abstract void getSponsorAlliance();
        abstract void getJoinedAlliance();
        abstract void getPrepareMerchants();
        abstract void loadMore( String startId);
    }
}
