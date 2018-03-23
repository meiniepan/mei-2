package com.wuyou.merchant.mvp.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.WalletInfoEntity;
import com.wuyou.merchant.view.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by solang on 2018/3/21.
 */

public class Loan2Activity extends BaseActivity {
    @BindView(R.id.wallet_limit)
    TextView walletUsed;
    @BindView(R.id.wallet_borrow)
    TextView walletFrozen;
    @BindView(R.id.wallet_pay_back)
    TextView walletTotal;
    @BindView(R.id.tv_residue)
    TextView tvResidue;
    WalletInfoEntity entity;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_loan_2;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        entity = getIntent().getParcelableExtra(Constant.WALLET_INFO_ENTITY);
        walletUsed.setText(entity.used_amount);
        walletFrozen.setText(entity.frozen_amount);
        walletTotal.setText(entity.total_amount);
        tvResidue.setText(entity.available_amount);
    }

    @OnClick({R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                loan();
                break;
        }
    }

    private void loan() {
        startActivity(new Intent(getCtx(), LoanActivity.class));
    }

}
