package com.wuyou.merchant.mvp.store;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gs.buluo.common.utils.ToastUtils;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.R;
import com.wuyou.merchant.mvp.commodity.CommodityManagerActivity;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.util.NetTool;
import com.wuyou.merchant.util.glide.GlideUtils;
import com.wuyou.merchant.view.activity.HelpRobotActivity;
import com.wuyou.merchant.view.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;


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
    @BindView(R.id.iv_store_info)
    ImageView ivStoreInfo;
    @BindView(R.id.ll_work_info)
    LinearLayout llWorkInfo;
    @BindView(R.id.ll_worker_list)
    LinearLayout llWorkerList;
    @BindView(R.id.ll_information)
    LinearLayout llInformation;
    @BindView(R.id.ll_service)
    LinearLayout llService;

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
        GlideUtils.loadImage(getContext(), CarefreeDaoSession.getInstance().getUserInfo().getLogo(), ivAvatar, true);
        tvName.setText(CarefreeDaoSession.getInstance().getUserInfo().getShop_name());
        tvPhone.setText(CommonUtil.getPhoneWithStar(CarefreeDaoSession.getInstance().getUserInfo().getTel()));
    }


    @Override
    public void showError(String message, int res) {
    }


    @OnClick({R.id.ll_work_info, R.id.ll_worker_list, R.id.ll_service, R.id.ll_setting,
            R.id.ll_information, R.id.ll_goods_manager, R.id.ll_message, R.id.ll_manager})
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
                if (!NetTool.isConnected(mCtx)) {
                    ToastUtils.ToastMessage(mCtx, R.string.no_network);
                    return;
                }
                intent.setClass(getContext(), HelpRobotActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_information:
                if (TextUtils.isEmpty(CarefreeDaoSession.getInstance().getUserInfo().getOfficial().name)) {
                    intent.setClass(getContext(), CompanyInfoUpdateActivity.class);
                } else {
                    intent.setClass(getContext(), CompanyInfoActivity.class);
                }

                startActivity(intent);
                break;
            case R.id.ll_setting:
                intent.setClass(getContext(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_goods_manager:
                intent.setClass(getContext(), CommodityManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_message:
                intent.setClass(getContext(), MessageActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_manager:
                intent.setClass(getContext(), ManagerListActivity.class);
                startActivity(intent);
                break;
        }

    }

}
