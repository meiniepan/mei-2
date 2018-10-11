package com.wuyou.merchant.mvp.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.view.activity.BaseActivity;

import butterknife.OnClick;

/**
 * Created by Solang on 2018/9/10.
 */

public class CreateAccountSuccessActivity extends BaseActivity {
    @Override
    protected void bindView(Bundle savedInstanceState) {
        setTitleText(getString(R.string.create_account));
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_create_account_success;
    }


    @OnClick({R.id.btn_backup, R.id.btn_check})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_backup:
                intent = new Intent(getCtx(), BackupPKeyActivity.class);
                intent.putExtra(Constant.BACKUP_FROM_CREATE, true);
                startActivity(intent);
                break;
            case R.id.btn_check:
                intent = new Intent(getCtx(), ScoreAccountActivity.class);
                startActivity(intent);
                break;
        }
        finish();
    }
}
