package com.wuyou.merchant.mvp.circle;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.ContractEntity;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.CircleApis;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.view.activity.BaseActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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
    private String imagePath;
    ContractEntity entity = new ContractEntity();

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_create_intelligent_contract_1;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/SamSung/empty_order.png");
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("license", file.getName(), requestFile);
        CarefreeRetrofit.getInstance().createApi(CircleApis.class)
                .createContract(body,QueryMapBuilder.getIns()
                        .put("contact_address","北京市")
                        .put("contract_name","abc")
                        .put("divided_amount","1000")
                        .put("end_at","1521615639425")
                        .put("information","北京市")
                        .put("mobile","13013073213")
                        .put("shop_id","114")
                        .put("shop_name","name")
                        .put("total_amount","500000")
                        .put("service","1")
                        .put("other_image","")
                        .buildPost())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {

                    }
                });
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
                entity.mobile = etPhone.getText().toString();
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
            imagePath = Matisse.obtainResult(data).get(0).toString();
            Glide.with(getCtx()).load(imagePath).into(ivAddBusinessLicense);
        }
    }
}
