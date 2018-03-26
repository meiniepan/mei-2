package com.wuyou.merchant.mvp.wallet;

import android.os.Bundle;
import android.widget.TextView;

import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.utils.TribeDateUtils;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.TradeEntity;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.WalletApis;
import com.wuyou.merchant.view.activity.BaseActivity;

import java.util.Date;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by solang on 2018/2/6.
 */

public class TradeDetailActivity extends BaseActivity {


    @BindView(R.id.tv_trade_hash)
    TextView tvTradeHash;
    @BindView(R.id.tv_trade_time)
    TextView tvTradeTime;
    @BindView(R.id.tv_confirm_time)
    TextView tvConfirmTime;
    @BindView(R.id.tv_confirm_node)
    TextView tvConfirmNode;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_trade_big)
    TextView tvTradeBig;
    @BindView(R.id.tv_trade_sum)
    TextView tvTradeSum;
    @BindView(R.id.tv_trade_fee)
    TextView tvTradeFee;
    private String id;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_trade_detail;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        id = getIntent().getStringExtra(Constant.TRANSACTION_ID);
        initData();
    }

    private void initData() {
        showLoadingDialog();
        CarefreeRetrofit.getInstance().createApi(WalletApis.class)
                .getTradeDetail(QueryMapBuilder.getIns()
                        .put("shop_id",CarefreeApplication.getInstance().getUserInfo().getShop_id())
                        .put("transaction_id",id)
                        .buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<TradeEntity>>() {
                    @Override
                    public void onSuccess(BaseResponse<TradeEntity> response) {
                        String isConfirm;
                        if (Integer.parseInt(response.data.confirmations)>0) {
                            isConfirm = "已确认";
                        }else {
                            isConfirm = "未确认";
                        }
                        String time = TribeDateUtils.SDF7.format(new Date(Long.parseLong(response.data.time) * 1000));
                        String confirmTime = TribeDateUtils.SDF7.format(new Date(Long.parseLong(response.data.timereceived) * 1000));
                        tvTradeHash.setText(response.data.blockhash);
                        tvTradeTime.setText(time);
                        tvConfirmTime.setText(confirmTime);
                        tvConfirmNode.setText(response.data.confirmations);
                        tvStatus.setText(isConfirm);
                        tvTradeBig.setText(response.data.blockindex);
                        tvTradeSum.setText(response.data.amount);
                        tvTradeFee.setText(response.data.fee);
                    }
                });
    }

}
