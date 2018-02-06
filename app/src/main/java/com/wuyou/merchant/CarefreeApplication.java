package com.wuyou.merchant;

import android.os.Environment;

import com.gs.buluo.common.BaseApplication;
import com.wuyou.merchant.bean.DaoMaster;
import com.wuyou.merchant.bean.DaoSession;
import com.wuyou.merchant.bean.UserInfo;
import com.wuyou.merchant.bean.UserInfoDao;

/**
 * Created by hjn on 2016/11/1.
 */
public abstract class CarefreeApplication extends BaseApplication {
    private static CarefreeApplication instance;
    private UserInfo userInfo;
    private UserInfoDao userInfoDao;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initDB();
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
        else
            return userInfoDao.loadByRowId(0);
    }

    public void setUserInfo(UserInfo userInfo) {
        CarefreeApplication.getInstance().getUserInfoDao().insert(userInfo);
        this.userInfo = userInfo;
    }

    public void clearUserInfo(){
        userInfoDao.deleteAll();
        userInfo=null;
    }

    public UserInfoDao getUserInfoDao() {
        return userInfoDao;
    }
}
