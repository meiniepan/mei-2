package com.wuyou.merchant.data.local.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.wuyou.merchant.bean.DaoMaster;
import com.wuyou.merchant.bean.UserInfoDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by DELL on 2018/9/18.
 */

public class CarefreeOpenHelper extends DaoMaster.OpenHelper {
    public CarefreeOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        }, EosAccountDao.class, UserInfoDao.class);
    }
}
