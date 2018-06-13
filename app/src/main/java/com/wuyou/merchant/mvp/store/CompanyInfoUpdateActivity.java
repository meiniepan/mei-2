package com.wuyou.merchant.mvp.store;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.utils.SharePreferenceManager;
import com.gs.buluo.common.utils.ToastUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.UserInfo;
import com.wuyou.merchant.bean.entity.OfficialEntity;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.UserApis;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.util.glide.GlideUtils;
import com.wuyou.merchant.view.activity.BaseActivity;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Solang on 2018/5/17.
 */

public class CompanyInfoUpdateActivity extends BaseActivity {
    @BindView(R.id.tv_company_info_update_name)
    EditText tvCompanyInfoUpdateName;
    @BindView(R.id.tv_company_info_update_legal_person)
    EditText tvCompanyInfoUpdateLegalPerson;
    @BindView(R.id.tv_company_info_update_code)
    EditText tvCompanyInfoUpdateCode;
    @BindView(R.id.tv_company_info_update_address)
    EditText tvCompanyInfoUpdateAddress;
    @BindView(R.id.iv_company_update_add)
    ImageView ivCompanyUpdateAdd;
    OfficialEntity companyInfo;
    private String imagePath;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_company_info_update;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        companyInfo = CarefreeDaoSession.getInstance().getUserInfo().getOfficial();
        if (companyInfo != null) {
            initData();
        }
    }

    private void initData() {
        tvCompanyInfoUpdateName.setText(companyInfo.name);
        tvCompanyInfoUpdateLegalPerson.setText(companyInfo.corporation);
        tvCompanyInfoUpdateCode.setText(companyInfo.code);
        tvCompanyInfoUpdateAddress.setText(companyInfo.registered_address);
        GlideUtils.loadImage(getCtx(), companyInfo.license, ivCompanyUpdateAdd);
        imagePath = companyInfo.license;
    }

    @OnClick({R.id.iv_company_update_add, R.id.btn_company_update_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_company_update_add:
                CommonUtil.choosePhoto(this, PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.btn_company_update_commit:
                if (TextUtils.isEmpty(tvCompanyInfoUpdateName.getText().toString().trim())
                        || TextUtils.isEmpty(tvCompanyInfoUpdateLegalPerson.getText().toString().trim())
                        || TextUtils.isEmpty(tvCompanyInfoUpdateCode.getText().toString().trim())
                        || TextUtils.isEmpty(tvCompanyInfoUpdateAddress.getText().toString().trim())
                        || TextUtils.isEmpty(imagePath)
                        ) {
                    ToastUtils.ToastMessage(getCtx(), R.string.not_all);
                    return;
                }
                updataInfo();
                break;
        }
    }

    private void updataInfo() {
        File file = new File(Constant.AUTH_IMG_PATH_1);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("license", file.getName(), requestFile);
        showLoadingDialog();
        CarefreeRetrofit.getInstance().createApi(UserApis.class)
                .updateCompanyInfo(CarefreeDaoSession.getInstance().getUserInfo().getShop_id(), body, QueryMapBuilder.getIns()
                        .put("name", tvCompanyInfoUpdateName.getText().toString().trim())
                        .put("corporation", tvCompanyInfoUpdateLegalPerson.getText().toString().trim())
                        .put("code", tvCompanyInfoUpdateCode.getText().toString().trim())
                        .put("registered_address", tvCompanyInfoUpdateAddress.getText().toString().trim())
                        .buildGet())
                .subscribeOn(Schedulers.io())
                .flatMap(baseResponse -> CarefreeRetrofit.getInstance().createApi(UserApis.class)
                        .getUserInfo(CarefreeDaoSession.getInstance().getUserInfo().getShop_id(), QueryMapBuilder.getIns().buildGet()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<UserInfo>>() {
                    @Override
                    public void onSuccess(BaseResponse<UserInfo> response) {
                        UserInfo userInfo = CarefreeDaoSession.getInstance().getUserInfo();
                        userInfo.setOfficial(response.data.getOfficial());
                        CarefreeDaoSession.getInstance().updateUserInfo(userInfo);
                        ToastUtils.ToastMessage(getCtx(), R.string.commit_success);
                        setResult(RESULT_OK);
                        finish();
                    }


                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);

                if (selectList != null && selectList.size() > 0) {
                    LocalMedia localMedia = selectList.get(0);
                    if (localMedia.isCompressed()) {
                        imagePath = localMedia.getCompressPath();
                    } else if (localMedia.isCut()) {
                        imagePath = localMedia.getCutPath();
                    } else {
                        imagePath = localMedia.getPath();
                    }
                }
                CommonUtil.compressAndSaveImgToLocal(imagePath,1);
                GlideUtils.loadImage(getCtx(), imagePath, ivCompanyUpdateAdd);
            }
        }
    }
}
