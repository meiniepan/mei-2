package com.wuyou.merchant.mvp.store;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wuyou.merchant.R;
import com.wuyou.merchant.view.activity.BaseActivity;
import com.wuyou.merchant.view.widget.panel.BankPickPanel;
import com.wuyou.merchant.view.widget.panel.ProvincePickPanel;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Solang on 2019/1/16.
 */

public class CompanyInfoUpdateNextActivity extends BaseActivity {
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.tv_company_info_update_2_name)
    EditText tvCompanyInfoUpdate2Name;
    @BindView(R.id.tv_company_open_province)
    TextView tvCompanyOpenProvince;
    @BindView(R.id.tv_company_open_bank)
    TextView tvCompanyOpenBank;
    @BindView(R.id.tv_company_info_update_code)
    EditText tvCompanyInfoUpdateCode;
    @BindView(R.id.tv_company_info_update_address)
    EditText tvCompanyInfoUpdateAddress;
    @BindView(R.id.tv_company_info_captcha)
    EditText tvCompanyInfoCaptcha;
    @BindView(R.id.iv_company_update_add)
    ImageView ivCompanyUpdateAdd;
    @BindView(R.id.btn_company_update_commit)
    Button btnCompanyUpdateCommit;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_company_info_update_next;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {

    }


    @OnClick({R.id.iv_company_province, R.id.iv_company_bank, R.id.iv_company_update_add, R.id.btn_company_update_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_company_province:
                initProvincePicker();
                break;
            case R.id.iv_company_bank:
                initBankPicker();
                break;
            case R.id.iv_company_update_add:
                break;
            case R.id.btn_company_update_commit:
                break;
        }
    }
    private void initProvincePicker() {
        ProvincePickPanel pickPanel = new ProvincePickPanel(this, new ProvincePickPanel.OnSelectedFinished() {
            @Override
            public void onSelected(String res) {
                tvCompanyOpenProvince.setText(res);
            }
        });
        pickPanel.show();
    }
    private void initBankPicker() {
        BankPickPanel pickPanel = new BankPickPanel(this, new BankPickPanel.OnSelectedFinished() {
            @Override
            public void onSelected(String res) {
                tvCompanyOpenBank.setText(res);
            }
        });
        pickPanel.show();
    }
}
