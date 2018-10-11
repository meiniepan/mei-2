package com.wuyou.merchant.mvp.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.gs.buluo.common.network.BaseSubscriber;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.data.EoscDataManager;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.util.RxUtil;
import com.wuyou.merchant.view.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Solang on 2018/9/11.
 */

public class ScoreAccountActivity extends BaseActivity {
    @BindView(R.id.iv_account_avatar)
    ImageView ivAccountAvatar;
    @BindView(R.id.tv_account_name)
    TextView tvAccountName;
    @BindView(R.id.tv_account_score)
    TextView tvAccountScore;
    @BindView(R.id.ll_backup_pk)
    LinearLayout llBackupPk;
    @BindView(R.id.tv_obtain)
    TextView tvObtain;
    @BindView(R.id.tv_exchange)
    TextView tvExchange;
    @BindView(R.id.drawerL)
    DrawerLayout drawerLayout;
    @BindView(R.id.ll_above)
    LinearLayout layout;
    @BindView(R.id.score_account_value)
    TextView scoreAccountValue;

    @Override
    protected void bindView(Bundle savedInstanceState) {
        initDrawerLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String account = CarefreeDaoSession.getInstance().getMainAccount().getName();
        tvAccountName.setText(account);
        getAccountScore(account);
    }

    public void getAccountScore(String name) {
        showLoadingDialog();
        EoscDataManager.getIns().getCurrencyBalance(Constant.EOSIO_TOKEN_CONTRACT, name, "EOS").compose(RxUtil.switchSchedulers())
                .subscribe(new BaseSubscriber<JsonArray>() {
                    @Override
                    public void onSuccess(JsonArray eosAccountInfo) {
                        if (eosAccountInfo.size() > 0) {
                            String scoreAmount = eosAccountInfo.get(0).toString().replace("EOS", "").replaceAll("\"", "");
                            tvAccountScore.setText(scoreAmount);
                            scoreAccountValue.setText(CommonUtil.formatPrice(Float.parseFloat(scoreAmount) / 100.0f));
                        }
                    }
                });
    }

    private void initDrawerLayout() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        WindowManager wm = this.getWindowManager();//获取屏幕宽高
        int width1 = wm.getDefaultDisplay().getWidth();
        int height1 = wm.getDefaultDisplay().getHeight();
        ViewGroup.LayoutParams para = layout.getLayoutParams();//获取drawerlayout的布局
        para.width = width1 * 4 / 7;//修改宽度
        para.height = height1;//修改高度
        layout.setLayoutParams(para); //设置修改后的布局。
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_score_account;
    }

    @OnClick({R.id.iv_more, R.id.ll_backup_pk, R.id.back_1, R.id.back_2, R.id.ll_import, R.id.ll_manager, R.id.ll_score, R.id.score_obtain_layout, R.id.score_exchange_layout})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.iv_more:
                drawerLayout.openDrawer(Gravity.START);
                break;
            case R.id.ll_backup_pk:
                intent.setClass(getCtx(), BackupPKeyActivity.class);
                startActivity(intent);
                break;
            case R.id.back_1:
                finish();
                break;
            case R.id.back_2:
                drawerLayout.closeDrawer(Gravity.START);
                break;
            case R.id.ll_import:
                intent.setClass(getCtx(), ImportAccountActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_manager:
                intent.setClass(getCtx(), ManagerAccountActivity.class);
                startActivity(intent);
                break;
        }
    }

}
