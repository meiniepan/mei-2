package com.wuyou.merchant.mvp.store;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.R;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.util.glide.GlideUtils;
import com.wuyou.merchant.view.activity.HelpRobotActivity;
import com.wuyou.merchant.view.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2018\1\29 0029.
 */

public class StoreFragment extends BaseFragment {
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;

    @Override
    protected int getContentLayout() {
        return R.layout.store_home;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void onResume() {
        super.onResume();
        initInfo();
    }

    private void initInfo() {
        GlideUtils.loadImage(getContext(), CarefreeDaoSession.getInstance().getUserInfo().getLogo(), ivAvatar,true);
        tvName.setText(CarefreeDaoSession.getInstance().getUserInfo().getShop_name());
        tvPhone.setText(CommonUtil.getPhoneWithStar(CarefreeDaoSession.getInstance().getUserInfo().getTel()));
    }

    @Override
    public void showError(String message, int res) {

    }


    @OnClick({R.id.ll_work_info, R.id.ll_worker_list, R.id.ll_service, R.id.ll_information, R.id.ll_intro, R.id.ll_mark, R.id.ll_setting})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_work_info:
                intent.setClass(getContext(), StoreInfoEditActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_worker_list:
                intent.setClass(getContext(), WorkerListActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_service:
                intent.setClass(getContext(), HelpRobotActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_information:
                intent.setClass(getContext(), CompanyInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_intro:
                break;
            case R.id.ll_mark:
                break;
            case R.id.ll_setting:
                intent.setClass(getContext(), SettingActivity.class);
                startActivity(intent);
                break;
        }
    }

}
