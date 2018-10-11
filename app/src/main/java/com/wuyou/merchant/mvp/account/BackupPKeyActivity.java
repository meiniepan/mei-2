package com.wuyou.merchant.mvp.account;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.TextView;

import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.utils.ToastUtils;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.util.RxUtil;
import com.wuyou.merchant.view.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by DELL on 2018/9/10.
 */

public class BackupPKeyActivity extends BaseActivity {
    @BindView(R.id.backup_pk_text)
    TextView backupPkText;
    @BindView(R.id.backup_pk)
    TextView backupPk;
    @BindView(R.id.save_pk)
    TextView savePk;
    @BindView(R.id.back_main)
    TextView backMain;
    private String privateKey;
    private boolean needUnlock;

    @Override
    protected void bindView(Bundle savedInstanceState) {
        setTitleText(getString(R.string.backup_pk));
        if (!getIntent().getBooleanExtra(Constant.BACKUP_FROM_CREATE, false)) {
            backupPkText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            savePk.setVisibility(View.GONE);
            backMain.setVisibility(View.GONE);
            backupPk.setText(R.string.unlock);
            needUnlock = true;
        }
        privateKey = CarefreeDaoSession.getInstance().getMainAccount().getPrivateKey();
        backupPkText.setText(privateKey);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_backup_pk;
    }

    @OnClick({R.id.backup_pk, R.id.save_pk, R.id.back_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backup_pk:
                if (needUnlock) {
                    unlock();
                } else {
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    if (cm != null) cm.setPrimaryClip(ClipData.newPlainText(null, privateKey));
                    ToastUtils.ToastMessage(getCtx(), getString(R.string.copy_success));
                }
                break;
            case R.id.save_pk:
                showLoadingDialog();
                Observable.fromCallable(() -> {
                    Bitmap screenshot = CommonUtil.getScreenshot(getCtx(), getRootView());
                    String s = MediaStore.Images.Media.insertImage(getCtx().getContentResolver(), screenshot, "备份", "钱包备份图片");
                    return Uri.parse(s);
                }).compose(RxUtil.switchSchedulers()).subscribeWith(new BaseSubscriber<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        CarefreeApplication.getInstance().getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                        ToastUtils.ToastMessage(getCtx(), R.string.save_success);
                    }
                });
                break;
            case R.id.back_main:
                Intent intent = new Intent(getCtx(), ScoreAccountActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void unlock() {
//        new CaptchaPanel(this, () -> {
//            backupPkText.setTransformationMethod(SingleLineTransformationMethod.getInstance());
//            savePk.setVisibility(View.VISIBLE);
//            backMain.setVisibility(View.VISIBLE);
//            backupPk.setText(R.string.copy_pk);
//            needUnlock = false;
//        }).show();
    }
}
