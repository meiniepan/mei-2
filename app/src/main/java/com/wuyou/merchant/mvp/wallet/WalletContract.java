package com.wuyou.merchant.mvp.wallet;


import com.wuyou.merchant.bean.entity.ResponseListEntity;
import com.wuyou.merchant.mvp.BasePresenter;
import com.wuyou.merchant.mvp.IBaseView;

/**
 * Created by Administrator on 2018\1\29 0029.
 */

public interface WalletContract {
    interface View extends IBaseView {
        void getSuccess(ResponseListEntity data);
        void getMore(ResponseListEntity data);
        void loadMoreError(int code);
    }

    abstract class Presenter extends BasePresenter<View> {
        abstract void getFundList( );
        abstract void getTradeList( );
        abstract void getRepayRecordsList( );
        abstract void loadMore();
        abstract void loadMoreRepayRecords();
    }
}
