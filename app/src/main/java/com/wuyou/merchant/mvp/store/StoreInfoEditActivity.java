package com.wuyou.merchant.mvp.store;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gs.buluo.common.network.ApiException;
import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.utils.ToastUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.UserInfo;
import com.wuyou.merchant.bean.entity.LogoEntity;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.UserApis;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.util.ImageUtil;
import com.wuyou.merchant.util.glide.GlideUtils;
import com.wuyou.merchant.view.activity.BaseActivity;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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
        CommonUtil.GlideCircleLoad(getCtx(), CarefreeDaoSession.getInstance().getUserInfo().getLogo(), ivAvatar);
        GlideUtils.loadImage(getCtx(), CarefreeApplication.getInstance().getUserInfo().getLogo(), ivAvatar,true);
        tvName.setText(CarefreeApplication.getInstance().getUserInfo().getShop_name());
        tvPhone.setText(CommonUtil.getPhoneWithStar(CarefreeApplication.getInstance().getUserInfo().getTel()));
    }

    @OnClick({R.id.iv_avatar, R.id.ll_phone, R.id.ll_store_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_avatar:
                chosePhoto();
                break;
            case R.id.ll_store_name:
                startActivityForResult(new Intent(getCtx(), ModifyNickActivity.class),Constant.REQUEST_NICK);
                break;
            case R.id.ll_phone:
                startActivityForResult(new Intent(getCtx(), ModifyPhoneActivity.class),Constant.REQUEST_PHONE);
                break;
        }
    }

    private void chosePhoto() {
        CommonUtil.chooseCirclePhoto(this, PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                String path = "";
                if (selectList != null && selectList.size() > 0) {
                    LocalMedia localMedia = selectList.get(0);
                    if (localMedia.isCompressed()) {
                        path = localMedia.getCompressPath();
                    } else if (localMedia.isCut()) {
                        path = localMedia.getCutPath();
                    } else {
                        path = localMedia.getPath();
                    }
                }
                uploadAvatar(path);
            } else if (requestCode == Constant.REQUEST_NICK) {
                tvName.setText(data.getStringExtra("info"));
            } else if (requestCode == Constant.REQUEST_PHONE) {
                tvPhone.setText(CommonUtil.getPhoneWithStar(data.getStringExtra("info")));
            }
        }
    }

    private static final long MAX_NUM_PIXELS_THUMBNAIL = 64 * 64;

    private void uploadAvatar(final String path) {
        showLoadingDialog();
        Observable.just(path)
                .flatMap(imagePath -> {
                    CommonUtil.compressAndSaveImgToLocal(imagePath,1);
                    File file = new File(Constant.AUTH_IMG_PATH_1);
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("logo", file.getName(), requestFile);
                    return CarefreeRetrofit.getInstance().createApi(UserApis.class).updateAvatar(CarefreeDaoSession.getInstance().getUserInfo().getShop_id(), body, QueryMapBuilder.getIns().buildPost());
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<LogoEntity>>() {
                    @Override
                    public void onSuccess(BaseResponse<LogoEntity> baseResponse) {
                        ToastUtils.ToastMessage(getCtx(), "上传成功！");
                        GlideUtils.loadImage(getCtx(), baseResponse.data.logo, ivAvatar, true);
                        UserInfo userInfo = CarefreeDaoSession.getInstance().getUserInfo();
                        userInfo.setLogo(baseResponse.data.logo);
                        CarefreeDaoSession.getInstance().updateUserInfo(userInfo);
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        ToastUtils.ToastMessage(getCtx(), R.string.connect_fail);
                    }
                });
    }
}