package com.wuyou.merchant.mvp.store;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.utils.ToastUtils;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.R;
import com.wuyou.merchant.mvp.login.LoginActivity;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.UserApis;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.view.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


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
        CommonUtil.GlideCircleLoad(getContext(), CarefreeApplication.getInstance().getUserInfo().getLogo(), ivAvatar);
        tvName.setText(CarefreeApplication.getInstance().getUserInfo().getShop_name());
        tvPhone.setText(CarefreeApplication.getInstance().getUserInfo().getTel());
    }

    @Override
    public void showError(String message, int res) {

    }


    @OnClick({R.id.iv_store_info, R.id.ll_log_out,R.id.ll_worker_list, R.id.ll_service, R.id.ll_information, R.id.ll_intro, R.id.ll_mark, R.id.ll_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_store_info:
                startActivity(new Intent(getContext(), StoreInfoEditActivity.class));
                break;
            case R.id.ll_log_out:
                showLoadingDialog();
                CarefreeRetrofit.getInstance().createApi(UserApis.class)
                        .loginOut(CarefreeApplication.getInstance().getUserInfo().getShop_id(), QueryMapBuilder.getIns().buildPost())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<BaseResponse>() {
                            @Override
                            public void onSuccess(BaseResponse userInfoBaseResponse) {
                                CarefreeApplication.getInstance().clearUserInfo();
                                startActivity(new Intent(getContext(), LoginActivity.class));
                                getActivity().finish();
                            }
                        });

                break;
            case R.id.ll_worker_list:
                ToastUtils.ToastMessage(getContext(),"功能还在开发！");
                break;
            case R.id.ll_service:
                ToastUtils.ToastMessage(getContext(),"功能还在开发！");
                break;
            case R.id.ll_information:
                ToastUtils.ToastMessage(getContext(),"功能还在开发！");
                break;
            case R.id.ll_intro:
                ToastUtils.ToastMessage(getContext(),"功能还在开发！");
                break;
            case R.id.ll_mark:
                ToastUtils.ToastMessage(getContext(),"功能还在开发！");
                break;
            case R.id.ll_setting:
                ToastUtils.ToastMessage(getContext(),"功能还在开发！");
                break;
        }
    }

}
