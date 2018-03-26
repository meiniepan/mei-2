package com.wuyou.merchant.mvp.circle;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.utils.ToastUtils;
import com.gs.buluo.common.widget.LoadingDialog;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.ContractEntity;
import com.wuyou.merchant.bean.entity.ContractInfoEntity;
import com.wuyou.merchant.bean.entity.ResponseListEntity;
import com.wuyou.merchant.bean.entity.ServiceEntity;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.CircleApis;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.view.activity.BaseActivity;
import com.wuyou.merchant.view.activity.MainActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;
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
    @BindView(R.id.tv_service_category)
    TextView tvServiceCategory;
    @BindView(R.id.btn_new_address)
    Button btnNewAddress;
    @BindView(R.id.ll_time_service)
    LinearLayout llTimeService;
    @BindView(R.id.ll_deduct_scale)
    LinearLayout llDeductScale;
    @BindView(R.id.tv_address_input_1)
    EditText etInput1;
    @BindView(R.id.tv_address_input_2)
    EditText etInput2;
    @BindView(R.id.tv_address_input_3)
    EditText etInput3;
    @BindView(R.id.et_time)
    EditText etTime;
    @BindView(R.id.et_time2)
    EditText etTime2;
    ContractEntity entity;
    Uri imagePath;
    private int n = 1;
    private String serviceIndex = "1";

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_create_intelligent_contract_2;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        entity = getIntent().getParcelableExtra(Constant.CONTRACT_ENTITY);
        imagePath = getIntent().getParcelableExtra(Constant.IMAGE1_URL);
    }

    @OnClick({R.id.tv_commit, R.id.ll_time_service, R.id.ll_deduct_scale
            , R.id.ll_service_category, R.id.btn_new_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_commit:
                doCommit();
                break;
            case R.id.ll_time_service:
                break;
            case R.id.ll_deduct_scale:
                break;
            case R.id.ll_service_category:
                choseServiceCategory();
                break;
            case R.id.btn_new_address:
                if (n < 3) {
                    if (n == 1) {
                        etInput2.setVisibility(View.VISIBLE);

                    } else if (n == 2) {
                        etInput3.setVisibility(View.VISIBLE);
                    }
                    n = n + 1;
                }
                break;
        }
    }

    private void choseServiceCategory() {
        showLoadingDialog();
        List<String> s = new ArrayList<>();
        CarefreeRetrofit.getInstance().createApi(CircleApis.class)
                .getContractService(QueryMapBuilder.getIns()
                        .buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<ResponseListEntity<ServiceEntity>>>() {
                    @Override
                    public void onSuccess(BaseResponse<ResponseListEntity<ServiceEntity>> response) {
                        for (ServiceEntity ss : response.data.list
                                ) {
                            s.add(ss.service_name);
                        }
                        OptionPicker picker = new OptionPicker(CreateIntelligentContractActivity2.this, s);
                        picker.setCanceledOnTouchOutside(false);
                        picker.setDividerRatio(WheelView.DividerConfig.FILL);
                        picker.setShadowColor(Color.RED, 40);
                        picker.setSelectedIndex(1);
                        picker.setCycleDisable(true);
                        picker.setTextSize(22);
                        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                            @Override
                            public void onOptionPicked(int index, String item) {
                                tvServiceCategory.setText(item);
                                serviceIndex = (index +1)+"";
                            }
                        });
                        picker.show();
                    }

                });
    }

    private void doCommit() {
        entity.total_amount = etSum.getText().toString();
        entity.divided_amount = etScale.getText().toString();
        ServiceEntity serviceEntity = new ServiceEntity();
        entity.service = serviceEntity;
        entity.service.service_id = serviceIndex;
        List addressList = new ArrayList();
        addressList.add(etInput1.getText().toString());
        if (n == 2) addressList.add(etInput2.getText().toString());
        if (n == 3) addressList.add(etInput3.getText().toString());
        ContractInfoEntity cEntity = new ContractInfoEntity();
        cEntity.communities = addressList;
        cEntity.time_limit = etTime.getText().toString();
        cEntity.penalized_proportion = etTime2.getText().toString();

        Gson gson = new Gson();
        String sInfomation = gson.toJson(cEntity);
        entity.information = sInfomation;
        if (TextUtils.isEmpty(entity.total_amount)
                || TextUtils.isEmpty(entity.divided_amount)
                || TextUtils.isEmpty(entity.service.service_id)
                || TextUtils.isEmpty(etInput1.getText())
                || TextUtils.isEmpty(etTime.getText().toString())
                || TextUtils.isEmpty(etTime2.getText().toString())
                ) {
            ToastUtils.ToastMessage(getCtx(), "请完善资料");
            return;
        }
        File file = CommonUtil.getFileByUri(imagePath, getCtx());
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("license", file.getName(), requestFile);
        LoadingDialog.getInstance().show(getCtx(), "创建中...", false);
        CarefreeRetrofit.getInstance().createApi(CircleApis.class)
                .createContract(body, QueryMapBuilder.getIns()
                        .put("contact_address", entity.contact_address)
                        .put("contract_name", entity.contract_name)
                        .put("divided_amount", entity.divided_amount)
                        .put("end_at", entity.end_at)
                        .put("information", entity.information.toString())
                        .put("mobile", entity.mobile)
                        .put("shop_id", entity.shop_id)
                        .put("shop_name", entity.shop_name)
                        .put("total_amount", entity.total_amount)
                        .put("service", entity.service.service_id)
                        .put("other_image", "")
                        .buildPost())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        ToastUtils.ToastMessage(getCtx(), "创建成功！");
                        startActivity(new Intent(getCtx(), MainActivity.class));
                    }

                });
    }
}
