package com.ha.cjy.common.util.download.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建数据库
 * 
 */
public class OrmLiteOpenHelper extends OrmLiteSqliteOpenHelper {

    /**
     * 数据库名称
     */
    public static final String DATABASE_NAME = "download.sqlite";
    /**
     * 版本号
     */
    public static final int DATABASE_VERSION = 3;

    public static List<Class> DAO_CLASS_LIST = new ArrayList<Class>();
    public static List<Class> DAO_CLASS_LIST2 = new ArrayList<Class>();

    public OrmLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
        for (Class clzz : DAO_CLASS_LIST) {
            try {
                TableUtils.createTable(arg1, clzz);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2, int arg3) {
        for (Class clzz : DAO_CLASS_LIST2) {
            try {
                TableUtils.createTable(arg1, clzz);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
