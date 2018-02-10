package com.wuyou.merchant.mvp.order;


import com.wuyou.merchant.bean.entity.OrderInfoEntity;
import com.wuyou.merchant.bean.entity.OrderInfoListEntity;
import com.wuyou.merchant.mvp.BasePresenter;
import com.wuyou.merchant.mvp.IBaseView;

import java.util.List;

/**
 * Created by Administrator on 2018\1\29 0029.
 */

public interface OrderContract {
    interface View extends IBaseView {
        void getSuccess(OrderInfoListEntity data);
        void getMore(OrderInfoListEntity data);
        void loadMoreError(int code);
    }

    abstract class Presenter extends BasePresenter<View> {
        abstract void getOrders(String merchant_id, String status);
        abstract void getAllianceOrders(String merchant_id, String status);

        abstract void loadMore(String merchant_id, String status);
        abstract void loadAllianceMore(String merchant_id, String status);
    }
}
