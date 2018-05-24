package com.wuyou.merchant.mvp.store;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gs.buluo.common.utils.ToastUtils;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.R;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.util.glide.GlideUtils;
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
//        initInfo();
    }

    private void initInfo() {
        GlideUtils.loadImage(getContext(), CarefreeDaoSession.getInstance().getUserInfo().getLogo(), ivAvatar,true);
        tvName.setText(CarefreeDaoSession.getInstance().getUserInfo().getShop_name());
        tvPhone.setText(CommonUtil.getPhoneWithStar(CarefreeDaoSession.getInstance().getUserInfo().getTel()));
    }

    @Override
    public void showError(String message, int res) {

    }


    @OnClick({R.id.iv_store_info,  R.id.ll_worker_list, R.id.ll_service, R.id.ll_information, R.id.ll_intro, R.id.ll_mark, R.id.ll_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_store_info:
                startActivity(new Intent(getContext(), StoreInfoEditActivity.class));
                break;

            case R.id.ll_worker_list:
//                ToastUtils.ToastMessage(getContext(), R.string.not_open);
                startActivity(new Intent(getContext(), WorkerListActivity.class));
                break;
            case R.id.ll_service:
                ToastUtils.ToastMessage(getContext(), R.string.not_open);
                break;
            case R.id.ll_information:
//                ToastUtils.ToastMessage(getContext(), R.string.not_open);
                startActivity(new Intent(getContext(), CompanyInfoActivity.class));
                break;
            case R.id.ll_intro:
                ToastUtils.ToastMessage(getContext(), R.string.not_open);
                break;
            case R.id.ll_mark:
                ToastUtils.ToastMessage(getContext(), R.string.not_open);
                break;
            case R.id.ll_setting:
//                ToastUtils.ToastMessage(getContext(), R.string.not_open);
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initInfo();
    }
}
