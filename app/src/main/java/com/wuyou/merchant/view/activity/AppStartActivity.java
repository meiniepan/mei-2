package com.wuyou.merchant.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.R;
import com.wuyou.merchant.mvp.login.LoginActivity;

/**
 * Created by Administrator on 2018\1\29 0029.
 */

public class AppStartActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        init();
    }

    public void init() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(() -> {
            if (CarefreeApplication.getInstance().getUserInfo() != null) {
                startActivity(new Intent(AppStartActivity.this, MainActivity.class));
                finish();
            } else {
                Intent view = new Intent(AppStartActivity.this, LoginActivity.class);
                startActivity(view);
                finish();
            }

        }, 1000);
    }

}
