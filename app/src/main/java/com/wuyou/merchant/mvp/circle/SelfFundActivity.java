package com.wuyou.merchant.mvp.circle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.gs.buluo.common.utils.ToastUtils;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.view.activity.BaseActivity;

import butterknife.BindView;

/**
 * Created by DELL on 2018/3/29.
 */

public class SelfFundActivity extends BaseActivity {
    @BindView(R.id.self_fund_edit)
    EditText fundEditText;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_self_fund;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {

    }

    public void setSelfFund(View view) {
        if (fundEditText.length() == 0) {
            ToastUtils.ToastMessage(getCtx(), "请输入自有资金数额");
            return;
        }
        String fund = fundEditText.getText().toString().trim();
        Intent intent = new Intent();
        intent.putExtra(Constant.FUND, fund);
        setResult(RESULT_OK, intent);
        finish();
    }
}
