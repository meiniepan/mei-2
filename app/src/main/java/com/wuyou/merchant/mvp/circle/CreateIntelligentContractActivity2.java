package com.wuyou.merchant.mvp.circle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gs.buluo.common.network.ApiException;
import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.ContractEntity;
import com.wuyou.merchant.bean.entity.WorkerListEntity;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.CircleApis;
import com.wuyou.merchant.view.activity.BaseActivity;

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

public class CreateIntelligentContractActivity2 extends BaseActivity {

    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.et_sum)
    EditText etSum;
    @BindView(R.id.et_scale)
    EditText etScale;
    @BindView(R.id.et_create_time)
    EditText etCreateTime;
    @BindView(R.id.ll_service_category)
    LinearLayout llServiceCategory;
    @BindView(R.id.btn_new_address)
    Button btnNewAddress;
    @BindView(R.id.ll_time_service)
    LinearLayout llTimeService;
    @BindView(R.id.ll_deduct_scale)
    LinearLayout llDeductScale;
    ContractEntity  entity;
    String imagePath;

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_create_intelligent_contract_2;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        entity = getIntent().getParcelableExtra(Constant.CONTRACT_ENTITY);
        imagePath = getIntent().getStringExtra(Constant.IMAGE1_URL);
    }

    @OnClick({R.id.tv_commit, R.id.ll_time_service, R.id.ll_deduct_scale})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_commit:
                entity.total_amount = etSum.getText().toString();
                entity.divided_amount = etScale.getText().toString();
                entity.service = "1";
                entity.information = "通用";

                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), new File(imagePath));

//                CarefreeRetrofit.getInstance().createApi(CircleApis.class)
//                .createContract(QueryMapBuilder.getIns().put("shop_id", entity.shop_id)
//                        .put("shop_name", entity.shop_name)
//                        .put("end_at", entity.end_at)
//                        .put("contract_name", entity.contract_name)
//                        .put("contact_address", entity.contact_address)
//                        .put("mobile", entity.mobile)
//                        .put("total_amount", entity.total_amount)
//                        .put("divided_amount", entity.divided_amount)
//                        .put("service", entity.service)
//                        .put("information", entity.information)
//                        .buildPost(),requestBody)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseSubscriber<BaseResponse>() {
//                    @Override
//                    public void onSuccess(BaseResponse response) {
//
//                    }
//
//                    @Override
//                    protected void onFail(ApiException e) {
//
//                    }
//                });

                break;
            case R.id.ll_time_service:
                break;
            case R.id.ll_deduct_scale:
                break;
        }
    }
}
