package com.wuyou.merchant.mvp.order;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gs.buluo.common.utils.TribeDateUtils;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.OrderBeanDetail;
import com.wuyou.merchant.bean.entity.OrderInfoListEntity;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.util.glide.GlideUtils;
import com.wuyou.merchant.view.activity.BaseActivity;
import com.wuyou.merchant.view.widget.panel.SendMessagePanel;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hjn on 2018/2/6.
 */

public class OrderDetailActivity extends BaseActivity<OrderContract.View, OrderContract.Presenter> implements OrderContract.View {
    @BindView(R.id.order_detail_status)
    TextView orderDetailStatus;
    @BindView(R.id.order_detail_dispatch_warn)
    LinearLayout orderDetailWarn;
    @BindView(R.id.tv_worker)
    TextView tvWorker;
    @BindView(R.id.order_detail_address)
    TextView orderDetailAddress;
    @BindView(R.id.order_detail_name)
    TextView orderDetailName;
    @BindView(R.id.order_detail_phone)
    TextView orderDetailPhone;
    @BindView(R.id.order_detail_create_time)
    TextView orderDetailCreateTime;
    @BindView(R.id.order_detail_number)
    TextView orderDetailNumber;
    @BindView(R.id.order_detail_pay_method)
    TextView orderDetailPayMethod;
    @BindView(R.id.order_detail_pay_serial)
    TextView orderDetailBillSerial;
    @BindView(R.id.order_detail_action)
    TextView orderDetailAction;
    @BindView(R.id.order_detail_serve_way)
    TextView orderDetailServeWay;
    @BindView(R.id.order_detail_serve_time)
    TextView orderDetailServeTime;
    @BindView(R.id.order_detail_remark)
    TextView orderDetailRemark;
    @BindView(R.id.order_detail_pay_time)
    TextView orderDetailPayTime;
    @BindView(R.id.order_detail_contact_store)
    TextView orderDetailContactStore;
    @BindView(R.id.order_detail_second_payment)
    TextView orderDetailSecondPayment;
    @BindView(R.id.order_detail_cancel)
    TextView orderDetailCancel;
    @BindView(R.id.order_detail_store_name)
    TextView orderDetailStoreName;
    @BindView(R.id.order_detail_picture)
    ImageView orderDetailPicture;
    @BindView(R.id.order_detail_serve_name)
    TextView orderDetailServeName;
    @BindView(R.id.order_detail_goods_number)
    TextView orderDetailGoodsNumber;
    @BindView(R.id.order_detail_fee)
    TextView orderDetailFee;
    @BindView(R.id.order_detail_other_fee)
    TextView orderDetailOtherFee;
    @BindView(R.id.order_detail_amount)
    TextView orderDetailAmount;
    @BindView(R.id.order_detail_specification)
    TextView orderDetailSpec;
    @BindView(R.id.order_detail_pay_area)
    LinearLayout orderDetailPayArea;
    @BindView(R.id.order_detail_bottom)
    LinearLayout orderDetailBottom;
    private String orderId;
    private OrderBeanDetail beanDetail;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        setTitleText(R.string.order_detail);
        baseStatusLayout.setErrorAction(
                v -> mPresenter.getOrderDetail(orderId)
        );
        orderId = getIntent().getStringExtra(Constant.ORDER_ID);
    }

    @Override
    protected void onResume() {
        super.onResume();
        baseStatusLayout.showProgressView();
        mPresenter.getOrderDetail(orderId);
    }

    @Override
    protected OrderContract.Presenter getPresenter() {
        return new OrderPresenter();
    }


    @Override
    public void getOrderDetailSuccess(OrderBeanDetail bean) {
        baseStatusLayout.showContentView();
        setData(bean);
    }

    public void setData(OrderBeanDetail data) {
        beanDetail = data;
        if (beanDetail.status == 2 || beanDetail.status == 3) {
            orderDetailWarn.setVisibility(View.VISIBLE);
            tvWorker.setText(beanDetail.worker.worker_name);
        }
        if (beanDetail.status != 2 && beanDetail.second_payment != 0) {
            findViewById(R.id.order_detail_second_payment_area).setVisibility(View.VISIBLE);
        }
        GlideUtils.loadImage(this, data.service.photo, orderDetailPicture);
        orderDetailStatus.setText(CommonUtil.getOrderStatusString(data.status));
        orderDetailStoreName.setText(data.shop.shop_name);
        orderDetailServeName.setText(data.service.title);
        orderDetailSecondPayment.setText(CommonUtil.formatPrice(data.second_payment));
        orderDetailSecondPayment.setText(CommonUtil.formatPrice(data.second_payment));
        orderDetailGoodsNumber.setText(data.number + "");
        orderDetailOtherFee.setText(CommonUtil.formatPrice(data.service.visiting_fee));
        float price;
        if (data.specification != null && data.specification.id != null) {
            price = data.specification.price * data.number;
            orderDetailSpec.setText(data.specification.name);
        } else {
            price = data.service.price * data.number;
        }
        orderDetailFee.setText(CommonUtil.formatPrice(price));
        orderDetailAmount.setText(CommonUtil.formatPrice(data.amount));
        orderDetailName.setText(data.address.name);
        orderDetailAddress.setText(String.format("%s%s%s%s", data.address.city, data.address.district, data.address.area, data.address.address));
        orderDetailPhone.setText(data.address.mobile);

        orderDetailCreateTime.setText(TribeDateUtils.dateFormat(new Date(data.created_at * 1000)));
        orderDetailNumber.setText(data.order_no);
        orderDetailServeWay.setText(data.service_mode);
        orderDetailServeTime.setText(data.service_date + "  " + data.service_time);
        orderDetailRemark.setText(data.remark);
        if (!TextUtils.isEmpty(data.serial)) orderDetailBillSerial.setText(data.serial);
        orderDetailPayMethod.setText(data.pay_type);
        orderDetailPayTime.setText(TribeDateUtils.dateFormat(new Date(data.pay_time * 1000)));

        setActionStatus();
        switch (beanDetail.status) {
            case 1:
                dispatch();
                break;
            case 2:
                sendMessage();
                break;
            case 3:
                sendMessage();
                break;
            case 5:
                if (beanDetail.has_voucher.equals("0"))
                    uploadVoucher();
                else
                    uploadVoucherDone();
                break;
        }
    }

    private void uploadVoucherDone() {
        orderDetailBottom.setVisibility(View.VISIBLE);
        orderDetailContactStore.setText("凭证已上传");
        orderDetailContactStore.setEnabled(false);
    }

    private void uploadVoucher() {
        orderDetailBottom.setVisibility(View.VISIBLE);
        orderDetailContactStore.setText("上传凭证");
        orderDetailContactStore.setOnClickListener(view -> {
            Intent intent = new Intent(getCtx(), VoucherUploadActivity.class);
            intent.putExtra(Constant.ORDER_ID, beanDetail.order_id);
            startActivity(intent);
        });
    }

    private void dispatch() {
        orderDetailBottom.setVisibility(View.VISIBLE);
        orderDetailContactStore.setText("去分单");
        orderDetailContactStore.setOnClickListener(view -> {
            Intent intent = new Intent(getCtx(), ChoseArtisanActivity.class);
            intent.putExtra(Constant.ORDER_ID, beanDetail.order_id);
            startActivity(intent);
        });
    }

    private void sendMessage() {
        orderDetailBottom.setVisibility(View.VISIBLE);
        orderDetailContactStore.setText("发信息");
        orderDetailContactStore.setOnClickListener(view -> {
            SendMessagePanel sendMessagePanel = new SendMessagePanel(getCtx());
            sendMessagePanel.setData(beanDetail.worker.rc_id);
            sendMessagePanel.show();
        });
    }


    @OnClick({R.id.order_detail_action, R.id.order_detail_contact_store, R.id.order_detail_cancel})
    public void onViewClicked(View view) {
        if (TextUtils.isEmpty(orderId)) return;
        switch (view.getId()) {

            case R.id.order_detail_action:
        }
    }

    public void setActionStatus() {
        int[] colors = {R.color.custom_orange, R.color.custom_orange, R.color.custom_orange, R.color.custom_orange, R.color.custom_green, R.color.common_gray};
        orderDetailStatus.setTextColor(getResources().getColor(colors[beanDetail.status]));
    }

    @Override
    public void getSuccess(OrderInfoListEntity data) {

    }

    @Override
    public void getMore(OrderInfoListEntity data) {

    }

    @Override
    public void loadMoreError(int code) {

    }

//    @Override
//    public void onBackPressed() {
//        back();
//    }
//
//    private void back() {
//        Intent intent = new Intent();
//        intent.setClass(getCtx(), MainActivity.class);
//        intent.putExtra(Constant.MAIN_FLAG, 1);
//        startActivity(intent);
//        finish();
//    }
}