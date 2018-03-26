package com.wuyou.merchant.mvp.store;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.R;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.view.activity.BaseActivity;

import butterknife.BindView;

/**
 * Created by DELL on 2018/3/20.
 */

public class StoreInfoEditActivity extends BaseActivity {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_phone)
    TextView tvPhone;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_store_info;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        CommonUtil.GlideCircleLoad(getCtx(), CarefreeApplication.getInstance().getUserInfo().getLogo(),ivAvatar);
        tvName.setText(CarefreeApplication.getInstance().getUserInfo().getShop_name());
        tvPhone.setText(CarefreeApplication.getInstance().getUserInfo().getTel());
    }


}
