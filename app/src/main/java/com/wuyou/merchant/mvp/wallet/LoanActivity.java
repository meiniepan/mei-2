package com.wuyou.merchant.mvp.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wuyou.merchant.R;
import com.wuyou.merchant.view.activity.BaseActivity;

import butterknife.OnClick;

/**
 * Created by solang on 2018/3/21.
 */

public class LoanActivity extends BaseActivity {
    @Override
    protected int getContentLayout() {
        return R.layout.activity_loan;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {

    }

    @OnClick({ R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                loan();
                break;
        }
    }

    private void loan() {
        startActivity(new Intent(getCtx(), LoanConfirmActivity.class));
    }
}
