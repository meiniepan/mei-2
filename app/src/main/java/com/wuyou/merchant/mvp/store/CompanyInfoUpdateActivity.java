package com.wuyou.merchant.mvp.store;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.utils.ToastUtils;
import com.gs.buluo.common.widget.LoadingDialog;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.OfficialEntity;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.OrderApis;
import com.wuyou.merchant.network.apis.UserApis;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.util.glide.Glide4Engine;
import com.wuyou.merchant.util.glide.GlideUtils;
import com.wuyou.merchant.view.activity.BaseActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
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
        companyInfo = getIntent().getParcelableExtra(Constant.COMPANY_INFO);
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
    }

    @OnClick({R.id.iv_company_update_add, R.id.btn_company_update_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_company_update_add:
                Matisse.from(this)
                        .choose(MimeType.ofImage())
                        .capture(true)
                        .captureStrategy(new CaptureStrategy(true, "com.wuyou.merchant.FileProvider"))
                        .showSingleMediaType(true)
                        .theme(R.style.Matisse_Dracula)
                        .countable(false)
                        .maxSelectable(1)
                        .imageEngine(new Glide4Engine())
                        .forResult(Constant.IntentRequestCode.REQUEST_CODE_CHOOSE_IMAGE);
                break;
            case R.id.btn_company_update_commit:
                if (TextUtils.isEmpty(tvCompanyInfoUpdateName.getText().toString().trim())
                        ||TextUtils.isEmpty(tvCompanyInfoUpdateLegalPerson.getText().toString().trim())
                        ||TextUtils.isEmpty(tvCompanyInfoUpdateCode.getText().toString().trim())
                        ||TextUtils.isEmpty(tvCompanyInfoUpdateAddress.getText().toString().trim())
                        ||TextUtils.isEmpty(imagePath)
                ){
                    ToastUtils.ToastMessage(getCtx(),R.string.not_all);
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
                .updateCompanyInfo(CarefreeDaoSession.getInstance().getUserInfo().getShop_id(),body, QueryMapBuilder.getIns()
                        .put("name", tvCompanyInfoUpdateName.getText().toString().trim())
                        .put("corporation", tvCompanyInfoUpdateLegalPerson.getText().toString().trim())
                        .put("code", tvCompanyInfoUpdateCode.getText().toString().trim())
                        .put("registered_address", tvCompanyInfoUpdateAddress.getText().toString().trim())
                        .buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse orderResponse) {
                        ToastUtils.ToastMessage(getCtx(),"更新成功！");
                        setResult(RESULT_OK);
                        finish();
                    }


                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.IntentRequestCode.REQUEST_CODE_CHOOSE_IMAGE && resultCode == RESULT_OK) {
            imagePath = Matisse.obtainPathResult(data).get(0);
            CommonUtil.compressAndSaveImgToLocal(imagePath, 1);
            Glide.with(getCtx()).load(Matisse.obtainResult(data).get(0).toString()).into(ivCompanyUpdateAdd);
        }
    }
}
