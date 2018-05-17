package com.wuyou.merchant.mvp.store;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.gs.buluo.common.utils.DataCleanManager;
import com.gs.buluo.common.utils.ToastUtils;
import com.gs.buluo.common.widget.CustomAlertDialog;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.R;
import com.wuyou.merchant.mvp.login.LoginActivity;
import com.wuyou.merchant.view.activity.BaseActivity;

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
                ToastUtils.ToastMessage(getCtx(),R.string.not_open);
                break;
            case R.id.setting_feedback:
                ToastUtils.ToastMessage(getCtx(),R.string.not_open);
                break;
            case R.id.setting_clear_cache:
                new CustomAlertDialog.Builder(this).setTitle(R.string.prompt).setMessage("确定清除所有缓存?").setPositiveButton("清除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataCleanManager.clearAllCache(SettingActivity.this);
                        tvCache.setText("0.00K");
                    }
                }).setNegativeButton(getCtx().getString(R.string.cancel), null).create().show();
                break;
            case R.id.setting_update:
//                checkUpdate();
                ToastUtils.ToastMessage(getCtx(), R.string.not_open);
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

    private void logout() {
        CarefreeDaoSession.getInstance().clearUserInfo();
        Intent intent = new Intent(getCtx(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}