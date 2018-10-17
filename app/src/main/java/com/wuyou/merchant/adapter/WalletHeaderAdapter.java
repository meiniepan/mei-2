package com.wuyou.merchant.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.gs.buluo.common.utils.DensityUtils;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.WalletInfoEntity;
import com.wuyou.merchant.util.CommonUtil;
import com.gs.buluo.common.widget.recyclerHelper.BaseHolder;
import com.gs.buluo.common.widget.recyclerHelper.BaseQuickAdapter;

import java.util.List;

/**
 * Created by Solang on 2017/10/24.
 */

public class WalletHeaderAdapter extends BaseQuickAdapter<WalletInfoEntity, BaseHolder> {
    public WalletHeaderAdapter(int layoutResId, @Nullable List<WalletInfoEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseHolder helper, final WalletInfoEntity item) {
        View view = helper.getView(R.id.ll);
        if (helper.getAdapterPosition() == 1) {
            view.setBackground(CarefreeApplication.getInstance().getApplicationContext().getResources().getDrawable(R.mipmap.wallet_blue_bac));
            helper.setText(R.id.tv_key, "无忧信用分")
                    .setText(R.id.tv_value, item.score);
        } else if (helper.getAdapterPosition() == 2) {
            view.setBackground(CarefreeApplication.getInstance().getApplicationContext().getResources().getDrawable(R.mipmap.wallet_order_bg));
            helper.setText(R.id.tv_key, "订单营收 (元)")
                    .setText(R.id.tv_value, CommonUtil.formatPrice(item.income));
        } else if (helper.getAdapterPosition() == 3) {
            view.setBackground(CarefreeApplication.getInstance().getApplicationContext().getResources().getDrawable(R.mipmap.wallet_orange_bac));
            helper.setText(R.id.tv_key, "合约营收 (元)")
                    .setText(R.id.tv_value, CommonUtil.formatPrice(item.income));
        }

    }


    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        int width;
        width = CommonUtil.getScreenWidth(mContext) - DensityUtils.dip2px(CarefreeApplication.getInstance().getApplicationContext(), 60);
        View view = mLayoutInflater.inflate(layoutResId, parent, false);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        params.width = width;
        view.setLayoutParams(params);
        return view;
    }
}
