package com.wuyou.merchant.data.local.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.wuyou.merchant.bean.DaoSession;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "EOS_ACCOUNT".
*/
public class EosAccountDao extends AbstractDao<EosAccount, String> {

    public static final String TABLENAME = "EOS_ACCOUNT";

    /**
     * Properties of entity EosAccount.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Name = new Property(0, String.class, "name", true, "name");
        public final static Property Type = new Property(1, Integer.class, "type", false, "type");
        public final static Property PrivateKey = new Property(2, String.class, "privateKey", false, "private");
        public final static Property PublicKey = new Property(3, String.class, "publicKey", false, "public");
        public final static Property Main = new Property(4, Boolean.class, "main", false, "main");
    }


    public EosAccountDao(DaoConfig config) {
        super(config);
    }
    
    public EosAccountDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"EOS_ACCOUNT\" (" + //
                "\"name\" TEXT PRIMARY KEY NOT NULL ," + // 0: name
                "\"type\" INTEGER," + // 1: type
                "\"private\" TEXT," + // 2: privateKey
                "\"public\" TEXT," + // 3: publicKey
                "\"main\" INTEGER);"); // 4: main
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"EOS_ACCOUNT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, EosAccount entity) {
        stmt.clearBindings();
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(1, name);
        }
 
        Integer type = entity.getType();
        if (type != null) {
            stmt.bindLong(2, type);
        }
 
        String privateKey = entity.getPrivateKey();
        if (privateKey != null) {
            stmt.bindString(3, privateKey);
        }
 
        String publicKey = entity.getPublicKey();
        if (publicKey != null) {
            stmt.bindString(4, publicKey);
        }
 
        Boolean main = entity.getMain();
        if (main != null) {
            stmt.bindLong(5, main ? 1L: 0L);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, EosAccount entity) {
        stmt.clearBindings();
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(1, name);
        }
 
        Integer type = entity.getType();
        if (type != null) {
            stmt.bindLong(2, type);
        }
 
        String privateKey = entity.getPrivateKey();
        if (privateKey != null) {
            stmt.bindString(3, privateKey);
        }
 
        String publicKey = entity.getPublicKey();
        if (publicKey != null) {
            stmt.bindString(4, publicKey);
        }
 
        Boolean main = entity.getMain();
        if (main != null) {
            stmt.bindLong(5, main ? 1L: 0L);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public EosAccount readEntity(Cursor cursor, int offset) {
        EosAccount entity = new EosAccount( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // name
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // type
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // privateKey
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // publicKey
            cursor.isNull(offset + 4) ? null : cursor.getShort(offset + 4) != 0 // main
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, EosAccount entity, int offset) {
        entity.setName(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setType(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setPrivateKey(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPublicKey(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setMain(cursor.isNull(offset + 4) ? null : cursor.getShort(offset + 4) != 0);
     }
    
    @Override
    protected final String updateKeyAfterInsert(EosAccount entity, long rowId) {
        return entity.getName();
    }
    
    @Override
    public String getKey(EosAccount entity) {
        if(entity != null) {
            return entity.getName();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(EosAccount entity) {
        return entity.getName() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
