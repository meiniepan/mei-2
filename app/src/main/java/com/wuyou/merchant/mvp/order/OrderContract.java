package com.wuyou.merchant.mvp.order;


import com.wuyou.merchant.bean.entity.OrderBeanDetail;
import com.wuyou.merchant.bean.entity.OrderInfoListEntity;
import com.wuyou.merchant.mvp.BasePresenter;
import com.wuyou.merchant.mvp.IBaseView;

/**
 * Created by Administrator on 2018\1\29 0029.
 */

public interface OrderContract {
    interface View extends IBaseView {
        void getSuccess(OrderInfoListEntity data);
        void getMore(OrderInfoListEntity data);
        void loadMoreError(int code);
        void getOrderDetailSuccess(OrderBeanDetail bean);
    }

    abstract class Presenter extends BasePresenter<View> {
        abstract void getOrders(String merchant_id, String status);
        abstract void loadMore(String merchant_id, String status);
        abstract void getOrderDetail(String orderId);
    }
}
