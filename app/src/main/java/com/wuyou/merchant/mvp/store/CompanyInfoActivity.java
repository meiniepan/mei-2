package com.wuyou.merchant.mvp.store;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.gs.buluo.common.utils.ToastUtils;
import com.wuyou.merchant.R;
import com.wuyou.merchant.view.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Solang on 2018/5/15.
 */

public class CompanyInfoActivity extends BaseActivity {


    @BindView(R.id.tv_company_info_name)
    TextView tvCompanyInfoName;
    @BindView(R.id.tv_company_info_legal_person)
    TextView tvCompanyInfoLegalPerson;
    @BindView(R.id.tv_company_info_code)
    TextView tvCompanyInfoCode;
    @BindView(R.id.tv_company_info_address)
    TextView tvCompanyInfoAddress;
    @BindView(R.id.rv_company_info_affix)
    RecyclerView rvCompanyInfoAffix;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_company_info;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {

    }

    @OnClick(R.id.iv_company_edit)
    public void onViewClicked() {
        ToastUtils.ToastMessage(getCtx(), R.string.not_open);
    }
}
