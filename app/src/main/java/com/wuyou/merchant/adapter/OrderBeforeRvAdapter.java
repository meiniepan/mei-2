package com.wuyou.merchant.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.gs.buluo.common.utils.ToastUtils;
import com.gs.buluo.common.utils.TribeDateUtils;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.OrderInfoEntity;
import com.wuyou.merchant.mvp.order.ChoseArtisanActivity;
import com.wuyou.merchant.view.widget.panel.SendMessagePanel;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseHolder;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseQuickAdapter;

import java.util.Date;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * Created by solang on 2018/2/5.
 */

public class OrderBeforeRvAdapter extends BaseQuickAdapter<OrderInfoEntity, BaseHolder> {
    private Activity activity;

    public OrderBeforeRvAdapter(Activity activity, int layoutResId, @Nullable List<OrderInfoEntity> data) {
        super(layoutResId, data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseHolder helper, OrderInfoEntity item) {
        String[] s = {"待分单", "未开始", "进行中", "待评价", "已完成", "已取消"};
        int[] colors = {R.color.custom_orange, R.color.custom_orange, R.color.custom_green, R.color.custom_green, R.color.main_blue, R.color.main_blue};
        int i = Integer.parseInt(item.status) - 1;
        String create_time = TribeDateUtils.dateFormat(new Date(item.created_at * 1000));
        TextView tvOrderCode = helper.getView(R.id.tv_order_code_1);//状态为“进行中”时，该字段可见，其余GONE
        TextView tvCreateTime1 = helper.getView(R.id.tv_create_time);//状态为“进行中”时，该字段显示订单编号（字体黑色），其余显示订单时间（字体灰色）
        TextView tvCreateTime2 = helper.getView(R.id.tv_order_create_time_2);//状态为“进行中”时，该字段显示订单时间，其余gone

        if (item.status.equals("3")) {
            tvOrderCode.setVisibility(View.VISIBLE);
            tvCreateTime1.setTextColor(activity.getResources().getColor(R.color.common_dark));
            tvCreateTime1.setText("");
            tvCreateTime2.setVisibility(View.VISIBLE);
            tvCreateTime2.setText(create_time);
        } else {
            tvOrderCode.setVisibility(View.GONE);
            tvCreateTime1.setTextColor(activity.getResources().getColor(R.color.common_gray));
            tvCreateTime1.setText(create_time);
            tvCreateTime2.setVisibility(View.GONE);
        }
        helper.setText(R.id.tv_status, s[i])
                .setText(R.id.tv_server_time, item.service_date + " " + item.service_time)
                .setTextColor(R.id.tv_status, activity.getResources().getColor(colors[i]))
                .setText(R.id.tv_category, item.service.service_name)
                .setText(R.id.tv_address, item.address.city_name + item.address.district + item.address.area + item.address.address)
                .setText(R.id.tv_sum, item.price);
        View ll_receiver = helper.getView(R.id.ll_receiver);
        Button dispatch = helper.getView(R.id.btn_divide_bill);
        dispatch.setVisibility(View.GONE);
        ll_receiver.setVisibility(View.GONE);
        if (item.status.equals("1")) {
            dispatch.setVisibility(View.VISIBLE);
            ll_receiver.setVisibility(View.GONE);
            dispatch.setText("分单");
            dispatch.setOnClickListener(view -> {
                Intent intent = new Intent(activity, ChoseArtisanActivity.class);
                intent.putExtra(Constant.ORDER_ID, item.order_id);
                activity.startActivity(intent);
            });
        } else if (item.status.equals("2") || item.status.equals("3")) {
            dispatch.setVisibility(View.VISIBLE);
            ll_receiver.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_receiver, item.worker.worker_name);
            dispatch.setText("发信息");
            dispatch.setOnClickListener(view -> {
                connectRongYun(CarefreeApplication.getInstance().getUserInfo().getRc_token());
                SendMessagePanel sendMessagePanel = new SendMessagePanel(mContext);
                sendMessagePanel.setData(item.worker.rc_id);
                sendMessagePanel.show();
            });
        }
    }

    private void connectRongYun(String token) {

        if (activity.getApplicationInfo().packageName.equals(CarefreeApplication.getCurProcessName(activity.getApplicationContext()))) {
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    Log.d("LoginActivity", "--onSuccess" + userid);
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.e("err", errorCode + "");
                }
            });
        }
    }
}
