package com.wuyou.merchant.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.wuyou.merchant.R;

/**
 * Created by Administrator on 2018\1\29 0029.
 */

public class AppStartActivity extends BaseActivity {
    @Override
    protected void bindView(Bundle savedInstanceState) {
        new Handler().postDelayed(() -> {
            startActivity(new Intent(getCtx(), MainActivity.class));
            finish();
        }, 1000);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_start;
    }
}
