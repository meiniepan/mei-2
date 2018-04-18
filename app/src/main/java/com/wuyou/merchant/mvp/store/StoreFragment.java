package com.wuyou.merchant.mvp.store;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gs.buluo.common.utils.ToastUtils;
import com.gs.buluo.common.widget.CustomAlertDialog;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.R;
import com.wuyou.merchant.mvp.login.LoginActivity;
import com.wuyou.merchant.util.CommonUtil;
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
        CommonUtil.GlideCircleLoad(getContext(), CarefreeApplication.getInstance().getUserInfo().getLogo(), ivAvatar);
        tvName.setText(CarefreeApplication.getInstance().getUserInfo().getShop_name());
        tvPhone.setText(CarefreeApplication.getInstance().getUserInfo().getTel());
    }

    @Override
    public void showError(String message, int res) {

    }


    @OnClick({R.id.iv_store_info, R.id.ll_log_out, R.id.ll_worker_list, R.id.ll_service, R.id.ll_information, R.id.ll_intro, R.id.ll_mark, R.id.ll_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_store_info:
                startActivity(new Intent(getContext(), StoreInfoEditActivity.class));
                break;
            case R.id.ll_log_out:
                CustomAlertDialog.Builder builder = new CustomAlertDialog.Builder(getContext());
                builder.setMessage("确认退出登录？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CarefreeApplication.getInstance().clearUserInfo();
                        startActivity(new Intent(getContext(), LoginActivity.class));
                        getActivity().finish();

//                        CarefreeRetrofit.getInstance().createApi(UserApis.class)
//                                .loginOut(CarefreeApplication.getInstance().getUserInfo().getShop_id(), QueryMapBuilder.getIns().buildPost())
//                                .subscribeOn(Schedulers.io())
//                                .observeOn(AndroidSchedulers.mainThread())
//                                .subscribe(new BaseSubscriber<BaseResponse>() {
//                                    @Override
//                                    public void onSuccess(BaseResponse userInfoBaseResponse) {
//
//                                    }
//                                });
                    }
                }
                );
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                builder.create().show();

                break;
            case R.id.ll_worker_list:
                ToastUtils.ToastMessage(getContext(), R.string.not_open);
                break;
            case R.id.ll_service:
                ToastUtils.ToastMessage(getContext(), R.string.not_open);
                break;
            case R.id.ll_information:
                ToastUtils.ToastMessage(getContext(), R.string.not_open);
                break;
            case R.id.ll_intro:
                ToastUtils.ToastMessage(getContext(), R.string.not_open);
                break;
            case R.id.ll_mark:
                ToastUtils.ToastMessage(getContext(), R.string.not_open);
                break;
            case R.id.ll_setting:
                ToastUtils.ToastMessage(getContext(), R.string.not_open);
                break;
        }
    }

}
