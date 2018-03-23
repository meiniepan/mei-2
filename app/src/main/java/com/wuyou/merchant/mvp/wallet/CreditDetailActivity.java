package com.wuyou.merchant.mvp.wallet;

import android.os.Bundle;
import android.widget.TextView;

import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.view.activity.BaseActivity;

import butterknife.BindView;

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

}
