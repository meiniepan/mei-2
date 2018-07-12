package com.wuyou.merchant.view.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.gs.buluo.common.utils.SharePreferenceManager;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.GuidePagerAdapter;
import com.wuyou.merchant.mvp.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\1\29 0029.
 */

public class AppStartActivity extends BaseActivity {
    ViewPager viewPager;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_start;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        disableFitSystemWindow();
        setBarColor(R.color.transparent);
        inits();
    }

    private void initViewPager() {
        List imageList = new ArrayList();
        imageList.add(R.mipmap.guild_1);
        imageList.add(R.mipmap.guild_2);
        viewPager = findViewById(R.id.vp_guild);
        viewPager.setVisibility(View.VISIBLE);
        viewPager.setAdapter(new GuidePagerAdapter(this, imageList));
    }

    public void inits() {
        new Handler().postDelayed(() -> {
            if (SharePreferenceManager.getInstance(this).getBooeanValue(Constant.FIRST_OPEN + getVersionCode(), true)) {
                SharePreferenceManager.getInstance(this).setValue(Constant.FIRST_OPEN + getVersionCode(), false);
                initViewPager();
                return;
            }
            doJump();

        }, 1000);
    }

    private void doJump() {
        if (CarefreeDaoSession.getInstance().getUserInfo() != null) {
            startActivity(new Intent(AppStartActivity.this, MainActivity.class));
        } else {
            Intent view = new Intent(AppStartActivity.this, LoginActivity.class);
            startActivity(view);
        }
        finish();
    }

    public int getVersionCode() {
        PackageManager manager;
        PackageInfo info = null;
        manager = this.getPackageManager();
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
