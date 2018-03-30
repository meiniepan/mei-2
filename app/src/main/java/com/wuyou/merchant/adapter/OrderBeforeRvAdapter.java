package com.wuyou.merchant.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.gs.buluo.common.utils.ToastUtils;
import com.gs.buluo.common.utils.TribeDateUtils;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.OrderInfoEntity;
import com.wuyou.merchant.mvp.order.ChoseArtisanFragment;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseHolder;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseQuickAdapter;

import java.util.Date;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

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
        helper.setText(R.id.tv_create_time, create_time)
                .setText(R.id.tv_status, s[i])
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
                Intent intent = new Intent(activity, ChoseArtisanFragment.class);
                intent.putExtra(Constant.ORDER_ID, item.order_id);
                activity.startActivity(intent);
            });
        } else if (item.status.equals("2") || item.status.equals("3")) {
            dispatch.setVisibility(View.VISIBLE);
            ll_receiver.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_receiver, item.worker.worker_name);
            dispatch.setText("发信息");
            dispatch.setOnClickListener(view -> {
                TextMessage myTextMessage = TextMessage.obtain("请准备服务！");

                Message myMessage = Message.obtain(item.worker.rc_id, Conversation.ConversationType.PRIVATE, myTextMessage);

                RongIM.getInstance().sendMessage(myMessage, null, null, new IRongCallback.ISendMessageCallback() {
                    @Override
                    public void onAttached(Message message) {
                        //消息本地数据库存储成功的回调
                    }

                    @Override
                    public void onSuccess(Message message) {
                        //消息通过网络发送成功的回调
                        ToastUtils.ToastMessage(activity, "消息发送成功！");
                    }

                    @Override
                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                        //消息发送失败的回调
                        ToastUtils.ToastMessage(activity, "消息发送失败！");
                    }
                });
            });
        }
    }
}
