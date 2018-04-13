package com.wuyou.merchant.mvp.wallet;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gs.buluo.common.utils.ToastUtils;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.view.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Solang on 2018/3/23.
 */

public class CreditDetailActivity extends BaseActivity {
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_content)
    TextView tvContent;
    private String sScore;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_credit_detail;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        sScore = getIntent().getStringExtra(Constant.CREDIT_SCORE);
        initUI();
    }

    private void initUI() {
        tvScore.setText(sScore);
    }


    @OnClick({R.id.ll_credit_manager, R.id.ll_authentication_info, R.id.ll_credit_record, R.id.ll_bad_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_credit_manager:
                ToastUtils.ToastMessage(getCtx(), R.string.not_open);
                break;
            case R.id.ll_authentication_info:
                ToastUtils.ToastMessage(getCtx(), R.string.not_open);
                break;
            case R.id.ll_credit_record:
                ToastUtils.ToastMessage(getCtx(), R.string.not_open);
                break;
            case R.id.ll_bad_record:
                ToastUtils.ToastMessage(getCtx(), R.string.not_open);
                break;
        }
    }
}
