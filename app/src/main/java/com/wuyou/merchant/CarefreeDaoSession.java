package com.wuyou.merchant;

import android.text.TextUtils;
import android.util.Log;

import com.gs.buluo.common.utils.SharePreferenceManager;
import com.wuyou.merchant.bean.DaoMaster;
import com.wuyou.merchant.bean.DaoSession;
import com.wuyou.merchant.bean.UserInfo;
import com.wuyou.merchant.bean.UserInfoDao;
import com.wuyou.merchant.data.local.db.CarefreeOpenHelper;
import com.wuyou.merchant.data.local.db.EosAccount;
import com.wuyou.merchant.data.local.db.EosAccountDao;

import java.util.List;

/**
 * Created by hjn on 2018/3/8.
 */

public class CarefreeDaoSession {
    private static DaoSession daoSession;
    private static CarefreeDaoSession instance;
    private String currentFormName;

    private CarefreeDaoSession(String form) {
        DaoMaster.OpenHelper devOpenHelper = new CarefreeOpenHelper(CarefreeApplication.getInstance().getApplicationContext(), form, null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        daoSession = daoMaster.newSession();
        currentFormName = devOpenHelper.getDatabaseName();
    }

    public static boolean isLogin() {
        return !TextUtils.isEmpty(SharePreferenceManager.getInstance(CarefreeApplication.getInstance().getApplicationContext()).getStringValue(Constant.DB_FORM_NAME))
                && getInstance().getUserInfo() != null;
    }

    /**
     * 必须在getInstance 之前调用 ，在loginPresenter里初始化
     *
     * @param currentForm
     */
    public static void setCurrentForm(String currentForm) {
        currentForm = currentForm + ".db";
        instance = new CarefreeDaoSession(currentForm);
        SharePreferenceManager.getInstance(CarefreeApplication.getInstance().getApplicationContext()).setValue(Constant.DB_FORM_NAME, currentForm);
    }

    public String getDatabaseFormName() {
        return currentFormName;
    }

    public static synchronized CarefreeDaoSession getInstance() {
        if (null == instance) {
            instance = new CarefreeDaoSession(SharePreferenceManager.getInstance(CarefreeApplication.getInstance().getApplicationContext()).getStringValue(Constant.DB_FORM_NAME));
        }
        return instance;
    }

    private static UserInfoDao getUserInfoDao() {
        return daoSession.getUserInfoDao();
    }

    public UserInfo setUserInfo(UserInfo userInfo) {
        getUserInfoDao().insert(userInfo);
        return userInfo;
    }

    public void updateUserInfo(UserInfo userInfo) {
        getUserInfoDao().update(userInfo);
    }

    public void clearUserInfo() {
        getUserInfoDao().deleteAll();
    }

    public UserInfo getUserInfo() {
        List<UserInfo> userInfos = getUserInfoDao().loadAll();
        if (userInfos == null || userInfos.size() == 0) return null;
        return userInfos.get(0);
    }

    private String uid;

    public String getUserId() {
        if (TextUtils.isEmpty(uid)) {
            List<UserInfo> userInfos = getUserInfoDao().loadAll();
            if (userInfos == null || userInfos.size() == 0) return null;
            uid = userInfos.get(0).getUid();
            return uid;
        } else {
            return uid;
        }
    }


    /*    --------------------------------------------------------------------------------------------    */
    //eos database operate


    public EosAccountDao getEosDao() {
        return daoSession.getEosAccountDao();
    }

    public void deleteAll() {
        CarefreeDaoSession.getInstance().getEosDao().deleteAll();
    }

    public void delete(String accountName) {
        CarefreeDaoSession.getInstance().getEosDao().deleteByKey(accountName);
    }

    public List<EosAccount> getAllEosAccount() {
        return CarefreeDaoSession.getInstance().getEosDao().loadAll();
    }

    public EosAccount searchName(String nameStarts) {
        return CarefreeDaoSession.getInstance().getEosDao().queryBuilder().where(EosAccountDao.Properties.Name.like("%" + nameStarts + "%")).build().unique();
    }

    public EosAccount getMainAccount() {
        return CarefreeDaoSession.getInstance().getEosDao().queryBuilder().where(EosAccountDao.Properties.Main.like("TRUE")).build().unique();
    }

    public EosAccount setMainAccount(String account) throws IllegalStateException { //remember to try/catch
        EosAccount eosAccount = searchName(account);
        if (eosAccount == null) {
            Log.e("Carefree", "Account not found in database:" + account);
            return null;
        }
        if (eosAccount.getMain()) {
            return eosAccount;
        }
        EosAccount mainAccount = getMainAccount();
        mainAccount.setMain(false);
        getEosDao().update(mainAccount);

        eosAccount.setMain(true);
        getEosDao().update(eosAccount);
        return eosAccount;
    }

    public EosAccount setMainAccount(EosAccount account) {
        EosAccount mainAccount = getMainAccount();
        if (mainAccount != null) {
            mainAccount.setMain(false);
            getEosDao().update(mainAccount);
        }
        account.setMain(true);
        getEosDao().update(account);
        return account;
    }
}
