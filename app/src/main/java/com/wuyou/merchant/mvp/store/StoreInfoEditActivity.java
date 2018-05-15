package com.wuyou.merchant.mvp.store;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gs.buluo.common.utils.ToastUtils;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.R;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.view.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

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
        CommonUtil.GlideCircleLoad(getCtx(), CarefreeApplication.getInstance().getUserInfo().getLogo(), ivAvatar);
        tvName.setText(CarefreeApplication.getInstance().getUserInfo().getShop_name());
        tvPhone.setText(CarefreeApplication.getInstance().getUserInfo().getTel());
    }

    @OnClick({R.id.iv_avatar, R.id.ll_phone, R.id.ll_store_name, R.id.ll_mail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_avatar:
                ToastUtils.ToastMessage(getCtx(), "功能还在开发！");
                break;
            case R.id.ll_store_name:
                startActivity(new Intent(getCtx(), ModifyStoreNameActivity.class));
                break;
            case R.id.ll_phone:
                startActivity(new Intent(getCtx(), ModifyPhoneActivity.class));
                break;
            case R.id.ll_mail:
                startActivity(new Intent(getCtx(), ModifyEmailActivity.class));
                break;
        }
    }
}
