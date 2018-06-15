package com.wuyou.merchant.mvp.store;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.gnway.bangwoba.activity.Leaving_message;
import com.gs.buluo.common.utils.AppManager;
import com.gs.buluo.common.utils.DataCleanManager;
import com.gs.buluo.common.utils.ToastUtils;
import com.gs.buluo.common.widget.CustomAlertDialog;
import com.tencent.bugly.beta.Beta;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.mvp.login.LoginActivity;
import com.wuyou.merchant.util.NetTool;
import com.wuyou.merchant.view.activity.BaseActivity;
import com.wuyou.merchant.view.activity.MainActivity;
import com.wuyou.merchant.view.activity.WebActivity;

import butterknife.BindView;

/**
 * Created by hjn on 2016/11/7.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.mine_switch)
    Switch mSwitch;
    @BindView(R.id.setting_cache_size)
    TextView tvCache;
    private CustomAlertDialog customAlertDialog;

    @Override
    protected void bindView(Bundle savedInstanceState) {
//        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
        findViewById(R.id.exit).setOnClickListener(this);
        findViewById(R.id.setting_clear_cache).setOnClickListener(this);
        findViewById(R.id.setting_feedback).setOnClickListener(this);
        findViewById(R.id.setting_update).setOnClickListener(this);
        findViewById(R.id.setting_about).setOnClickListener(this);
        String cacheSize = null;
        try {
            cacheSize = DataCleanManager.getTotalCacheSize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cacheSize != null)
            tvCache.setText(cacheSize);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_setting;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.setting_about:
                if (!NetTool.isConnected(getCtx())) {
                    ToastUtils.ToastMessage(getCtx(), R.string.no_network);
                    return;
                }
                intent.setClass(getCtx(), WebActivity.class);
                intent.putExtra(Constant.WEB_INTENT, Constant.ABOUT_US_URL);
                startActivity(intent);
                break;
            case R.id.setting_feedback:
                intent.setClass(getCtx(), Leaving_message.class);
                startActivity(intent);
                break;
            case R.id.setting_clear_cache:
                new CustomAlertDialog.Builder(this).setTitle(R.string.prompt).setMessage("确定清除所有缓存?").setPositiveButton("清除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataCleanManager.clearAllCache(SettingActivity.this);
                        tvCache.setText("0K");
                    }
                }).setNegativeButton(getCtx().getString(R.string.cancel), null).create().show();
                break;
            case R.id.setting_update:
                checkUpdate();
                break;
            case R.id.exit:
                customAlertDialog = new CustomAlertDialog.Builder(this).setTitle(R.string.prompt).setMessage("您确定要退出登录吗?")
                        .setPositiveButton(getString(R.string.yes), (dialog, which) -> logout())
                        .setNegativeButton(getString(R.string.cancel), (dialog, which) -> {
                            customAlertDialog.dismiss();
                        }).create();
                customAlertDialog.show();
                break;
        }
    }

    private void checkUpdate() {
        Beta.checkUpgrade(true,false);
    }

    private void logout() {
        CarefreeDaoSession.getInstance().clearUserInfo();
        Intent intent = new Intent(getCtx(), LoginActivity.class);
        startActivity(intent);
        AppManager.getAppManager().finishActivity(MainActivity.class);
        finish();
    }
}
