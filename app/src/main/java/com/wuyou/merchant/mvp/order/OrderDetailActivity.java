package com.wuyou.merchant.mvp.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gs.buluo.common.network.ApiException;
import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.utils.ToastUtils;
import com.gs.buluo.common.utils.TribeDateUtils;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.OrderInfoEntity;
import com.wuyou.merchant.bean.entity.OrderInfoListEntity;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.OrderApis;
import com.wuyou.merchant.view.activity.BaseActivity;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by solang on 2018/2/5.
 */

public class OrderDetailActivity extends BaseActivity {
    @BindView(R.id.tv_accept_time)
    TextView tvAcceptTime;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.tv_server_time)
    TextView tvServerTime;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.tv_sum)
    TextView tvSum;
    @BindView(R.id.tv_pay_way)
    TextView tvPayWay;
    @BindView(R.id.tv_is_payed)
    TextView tvIsPayed;
    @BindView(R.id.btn_divide_bill)
    Button btnDivideBill;
    String orderId;
    int fromWhere;

    @Override
    protected int getContentLayout() {
        return R.layout.order_detail;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        orderId = getIntent().getStringExtra(Constant.ORDER_ID);
        fromWhere = getIntent().getIntExtra(Constant.DIVIDE_ORDER_FROM, 0);
        initData();
    }

    private void initData() {
        showLoadingDialog();
        CarefreeRetrofit.getInstance().createApi(OrderApis.class)
                .getOrdersDetail(CarefreeApplication.getInstance().getUserInfo().getUid(), orderId, QueryMapBuilder.getIns().buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<OrderInfoEntity>>() {
                    @Override
                    public void onSuccess(BaseResponse<OrderInfoEntity> response) {
                        initUI(response.data);
                    }

                });
    }

    private void initUI(OrderInfoEntity data) {
        tvAcceptTime.setText(TribeDateUtils.dateFormat(new Date(data.accept_at*1000)));
        tvCategory.setText(data.category);
        tvServerTime.setText(data.service_time);
        tvAddress.setText(data.address);
        tvPhone.setText(data.phone);
        tvCreateTime.setText(TribeDateUtils.dateFormat(new Date(data.created_at*1000)));
        tvId.setText(data.order_num);
        tvSum.setText(data.price+"元");
        tvPayWay.setText(data.pay_type);
        tvIsPayed.setText(data.pay_status);
        if (data.is_dispatch.equals("0")) {

            btnDivideBill.setText("分单");
            btnDivideBill.setOnClickListener(view -> {
                Intent intent = new Intent(OrderDetailActivity.this, ChoseServerActivity.class);
                intent.putExtra(Constant.ORDER_ID,orderId);
                startActivity(intent);
            });
        } else {
            btnDivideBill.setText("发信息");
            btnDivideBill.setOnClickListener(view -> {
                ToastUtils.ToastMessage(OrderDetailActivity.this,"此功能暂未开通！");
            });
        }
    }


    @OnClick(R.id.btn_divide_bill)
    public void onViewClicked() {
    }
}
