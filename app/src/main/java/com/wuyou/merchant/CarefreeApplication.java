package com.wuyou.merchant;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDex;

import com.gs.buluo.common.BaseApplication;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.wuyou.merchant.bean.DaoMaster;
import com.wuyou.merchant.bean.DaoSession;
import com.wuyou.merchant.bean.UserInfo;
import com.wuyou.merchant.bean.UserInfoDao;
import com.wuyou.merchant.mvp.login.LoginActivity;
import com.wuyou.merchant.mvp.store.SettingActivity;
import com.wuyou.merchant.view.activity.MainActivity;

/**
 * Created by hjn on 2016/11/1.
 */
public class CarefreeApplication extends BaseApplication {
    private static CarefreeApplication instance;
    private UserInfo userInfo;
    private UserInfoDao userInfoDao;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initDB();
//        CrashReport.initCrashReport(getApplicationContext(), "505e4fa223", true);
        initBuglyUpgrade();
    }

    private void initBuglyUpgrade() {
        Beta.upgradeDialogLayoutId = R.layout.upgrade_dialog;
        Beta.canShowUpgradeActs.add(MainActivity.class);
        Beta.canShowUpgradeActs.add(LoginActivity.class);
        Beta.canShowUpgradeActs.add(SettingActivity.class);
        Bugly.init(getApplicationContext(), "5c7ecfcc54", false);
    }


    private void initDB() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "carefree.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        userInfoDao = daoSession.getUserInfoDao();
    }

    public static synchronized CarefreeApplication getInstance() {
        return instance;
    }

    @Override
    public void onInitialize() {

    }


    @Override
    public String getFilePath() {
        return Environment.getExternalStorageDirectory().toString() + "/carefree/";
    }

    public UserInfo getUserInfo() {
        if (userInfo != null)
            return userInfo;
        else {
            UserInfo userInfo = userInfoDao.loadByRowId(0);
            return userInfo;
        }
    }

    public void setUserInfo(UserInfo userInfo) {
        CarefreeApplication.getInstance().getUserInfoDao().insert(userInfo);
        this.userInfo = userInfo;
    }

    public void clearUserInfo() {
        userInfoDao.deleteAll();
        userInfo = null;

    }

    public UserInfoDao getUserInfoDao() {
        return userInfoDao;
    }

    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

}
