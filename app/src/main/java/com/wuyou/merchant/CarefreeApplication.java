package com.wuyou.merchant;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.gs.buluo.common.BaseApplication;
import com.gs.buluo.common.network.ApiException;
import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.utils.SharePreferenceManager;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tendcloud.tenddata.TCAgent;
import com.wuyou.merchant.bean.DaoMaster;
import com.wuyou.merchant.bean.DaoSession;
import com.wuyou.merchant.bean.UserInfo;
import com.wuyou.merchant.bean.UserInfoDao;
import com.wuyou.merchant.bean.entity.UpdateEntity;
import com.wuyou.merchant.mvp.login.LoginActivity;
import com.wuyou.merchant.mvp.store.SettingActivity;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.UserApis;
import com.wuyou.merchant.view.activity.MainActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
        initUrl();
        initDB();
//        CrashReport.initCrashReport(getApplicationContext(), "505e4fa223", true);
        initBuglyUpgrade();
        initTalkingData();
    }
    private void initTalkingData() {
        TCAgent.LOG_ON=true;
        // App ID: 在TalkingData创建应用后，进入数据报表页中，在“系统设置”-“编辑应用”页面里查看App ID。
        // 渠道 ID: 是渠道标识符，可通过不同渠道单独追踪数据。
        TCAgent.init(this, "8AE868CD5DC24735BA4C2DBF36007577", "android");
        // 如果已经在AndroidManifest.xml配置了App ID和渠道ID，调用TCAgent.init(this)即可；或与AndroidManifest.xml中的对应参数保持一致。
    }

    private void initUrl() {
        String baseUrl = SharePreferenceManager.getInstance(this).getStringValue(Constant.SP_BASE_URL);
        if (!TextUtils.isEmpty(baseUrl)) Constant.BASE_URL = baseUrl;
        if (TextUtils.equals(baseUrl, Constant.ONLINE_BASE_URL)) {
            TCAgent.setReportUncaughtExceptions(true);
        } else {
            TCAgent.setReportUncaughtExceptions(false);
        }
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
    public void ManualCheckOnForceUpdate(){
        CarefreeRetrofit.getInstance().createApi(UserApis.class)
                .checkUpdate(QueryMapBuilder.getIns().put("version",getVersionCode()+"" )
                        .put("platform", "android")
                        .buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<UpdateEntity>>() {
                    @Override
                    public void onSuccess(BaseResponse<UpdateEntity> response) {
                        if (2 == response.data.update){
                            Beta.checkUpgrade(false,true);
                        }
                    }
                    @Override
                    protected void onFail(ApiException e) {
                    }
                });
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
