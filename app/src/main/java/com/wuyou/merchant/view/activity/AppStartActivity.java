package com.wuyou.merchant.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.R;
import com.wuyou.merchant.mvp.login.LoginActivity;

/**
 * Created by Administrator on 2018\1\29 0029.
 */

public class AppStartActivity extends BaseActivity {
    @Override
    protected void bindView(Bundle savedInstanceState) {
        new Handler().postDelayed(() -> {
            if (CarefreeApplication.getInstance().getUserInfo() != null) {
                startActivity(new Intent(getCtx(), MainActivity.class));
                finish();
            } else {
                Intent view = new Intent(getCtx(), LoginActivity.class);
                startActivity(view);
            }

        }, 1000);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_start;
    }
}
