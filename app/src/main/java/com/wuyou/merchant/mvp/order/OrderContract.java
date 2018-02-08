package com.wuyou.merchant.mvp.order;


import com.wuyou.merchant.mvp.BasePresenter;
import com.wuyou.merchant.mvp.IBaseView;

/**
 * Created by Administrator on 2018\1\29 0029.
 */

public interface OrderContract {
    interface View extends IBaseView {
        void getSuccess();
    }

    abstract class Presenter extends BasePresenter<View> {
        abstract void getOrders(int merchant_id, int status,int start_id, int flag);

    }
}
