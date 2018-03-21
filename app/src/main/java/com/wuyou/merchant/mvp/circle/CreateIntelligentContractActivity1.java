package com.wuyou.merchant.mvp.circle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gs.buluo.common.utils.ToastUtils;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.ContractEntity;
import com.wuyou.merchant.view.activity.BaseActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Solang on 2018/3/19.
 */

public class CreateIntelligentContractActivity1 extends BaseActivity {
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.et_contract_name)
    EditText etContractName;
    @BindView(R.id.et_end_time)
    EditText etEndTime;
    @BindView(R.id.iv_calendar)
    ImageView ivCalendar;
    @BindView(R.id.et_company_name)
    EditText etCompanyName;
    @BindView(R.id.et_company_address)
    EditText etCompanyAddress;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.iv_add_business_license)
    ImageView ivAddBusinessLicense;
    @BindView(R.id.iv_add_other)
    ImageView ivAddOther;
    private Uri imagePath;
    ContractEntity entity = new ContractEntity();

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_create_intelligent_contract_1;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {

    }

    @OnClick({R.id.tv_next, R.id.iv_calendar, R.id.iv_add_business_license, R.id.iv_add_other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                Intent intent = new Intent(getCtx(), CreateIntelligentContractActivity2.class);
                entity.shop_id = CarefreeApplication.getInstance().getUserInfo().getUid();
                entity.contract_name = etContractName.getText().toString();
                entity.end_at = etEndTime.getText().toString();
                entity.shop_name = etCompanyName.getText().toString();
                entity.contact_address = etCompanyAddress.getText().toString();
                entity.mobile = etPhone.getText().toString().trim();
                if (TextUtils.isEmpty(entity.contract_name)
                        || TextUtils.isEmpty(entity.end_at)
                        || TextUtils.isEmpty(entity.shop_name)
                        || TextUtils.isEmpty(entity.contact_address)
                        || TextUtils.isEmpty(entity.mobile)
                        || (imagePath == null)
                        ){
                    ToastUtils.ToastMessage(getCtx(),"请完善资料");
                    return;
                }
                if (entity.mobile.length()!= 11){
                    ToastUtils.ToastMessage(getCtx(),"手机号码格式不正确");
                    return;
                }
                intent.putExtra(Constant.CONTRACT_ENTITY,entity);
                intent.putExtra(Constant.IMAGE1_URL,imagePath);
                startActivity(intent);
                break;
            case R.id.iv_calendar:
                break;
            case R.id.iv_add_business_license:
                Matisse.from(this)
                        .choose(MimeType.ofImage())
                        .capture(true)
                        .captureStrategy(new CaptureStrategy(true, "com.wuyou.merchant.FileProvider"))
                        .showSingleMediaType(true)
                        .theme(R.style.Matisse_Dracula)
                        .countable(false)
                        .maxSelectable(1)
                        .imageEngine(new PicassoEngine())
                        .forResult(Constant.REQUEST_CODE_CHOOSE_IMAGE);
                break;
            case R.id.iv_add_other:
                break;
        }
    }
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE_CHOOSE_IMAGE && resultCode == RESULT_OK) {
            imagePath = Matisse.obtainResult(data).get(0);
            Glide.with(getCtx()).load(Matisse.obtainResult(data).get(0).toString()).into(ivAddBusinessLicense);
        }
    }
}
