package com.wuyou.merchant.mvp.circle;

import android.content.Intent;
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
import com.wuyou.merchant.util.glide.Glide4Engine;
import com.wuyou.merchant.view.activity.BaseActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.util.ConvertUtils;

/**
 * Created by Solang on 2018/3/19.
 */

public class CreateIntelligentContractActivity1 extends BaseActivity {
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.et_contract_name)
    EditText etContractName;
    @BindView(R.id.et_end_time)
    TextView tvEndTime;
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
    private String imagePath2;
    ContractEntity entity = new ContractEntity();
    private long endTime;

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
                doNext();
                break;
            case R.id.iv_calendar:
                Calendar calendar = Calendar.getInstance();
                final DatePicker picker = new DatePicker(this);
                picker.setCanceledOnTouchOutside(true);
                picker.setUseWeight(true);
                picker.setTopPadding(ConvertUtils.toPx(this, 10));
                picker.setRangeEnd(2111, 1, 11);
                calendar.setTime(new Date(System.currentTimeMillis() + 24 * 3600 * 1000));
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                picker.setRangeStart(year, month, day);
                picker.setResetWhileWheel(false);
                picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));
                        endTime = calendar.getTimeInMillis() / 1000;
                        tvEndTime.setText(year + "-" + month + "-" + day);
                    }
                });
                picker.setOnWheelListener(new DatePicker.OnWheelListener() {
                    @Override
                    public void onYearWheeled(int index, String year) {
                        picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
                    }

                    @Override
                    public void onMonthWheeled(int index, String month) {
                        picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
                    }

                    @Override
                    public void onDayWheeled(int index, String day) {
                        picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
                    }
                });
                picker.show();
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
                        .imageEngine(new Glide4Engine())
                        .forResult(Constant.IntentRequestCode.REQUEST_CODE_CHOOSE_IMAGE);
                break;
            case R.id.iv_add_other:
                Matisse.from(this)
                        .choose(MimeType.ofImage())
                        .capture(true)
                        .captureStrategy(new CaptureStrategy(true, "com.wuyou.merchant.FileProvider"))
                        .showSingleMediaType(true)
                        .theme(R.style.Matisse_Dracula)
                        .countable(false)
                        .maxSelectable(1)
                        .imageEngine(new Glide4Engine())
                        .forResult(Constant.IntentRequestCode.REQUEST_CODE_CHOOSE_IMAGE_2);
                break;
        }
    }

    private void doNext() {
        Intent intent = new Intent(getCtx(), CreateIntelligentContractActivity2.class);
        entity.shop_id = CarefreeApplication.getInstance().getUserInfo().getUid();
        entity.contract_name = etContractName.getText().toString();
        entity.end_at = endTime;
        entity.shop_name = etCompanyName.getText().toString();
        entity.contact_address = etCompanyAddress.getText().toString();
        entity.mobile = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(entity.contract_name)
                || entity.end_at == 0
                || TextUtils.isEmpty(entity.shop_name)
                || TextUtils.isEmpty(entity.contact_address)
                || TextUtils.isEmpty(entity.mobile)
                || (imagePath == null)) {
            ToastUtils.ToastMessage(getCtx(), "请完善资料");
            return;
        }
        if (entity.mobile.length() != 11) {
            ToastUtils.ToastMessage(getCtx(), "手机号码格式不正确");
            return;
        }
        intent.putExtra(Constant.CONTRACT_ENTITY, entity);
        intent.putExtra(Constant.IMAGE1_URL, imagePath);
        intent.putExtra(Constant.IMAGE1_URL_2, imagePath2);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.IntentRequestCode.REQUEST_CODE_CHOOSE_IMAGE && resultCode == RESULT_OK) {
            imagePath = Matisse.obtainPathResult(data).get(0);
            Glide.with(getCtx()).load(Matisse.obtainResult(data).get(0).toString()).into(ivAddBusinessLicense);
        }
        if (requestCode == Constant.IntentRequestCode.REQUEST_CODE_CHOOSE_IMAGE_2 && resultCode == RESULT_OK) {
            imagePath2 = Matisse.obtainPathResult(data).get(0);
            Glide.with(getCtx()).load(Matisse.obtainResult(data).get(0).toString()).into(ivAddOther);
        }
    }
}
