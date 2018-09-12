package com.ha.cjy.common.util.download.db;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * 下载数据的数据库操作dao
 * @param <T>
 * @param <ID>
 */

public class DaoHelpImp<T, ID> implements IDaoHelp<T, ID> {

    protected Dao<T, ID> dao;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public DaoHelpImp(Context context, Class openHelperClass,
                      Class<T> typeInstance) {
        try {

            dao = OpenHelperManager.getHelper(context, openHelperClass).getDao(
                    typeInstance);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int insert(T t) {
        try {
            return dao.create(t);
        } catch (SQLException e) {
            return 0;
        }

    }

    @Override
    public int update(T t) {
        try {
            return dao.update(t);
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public int delete(T t) {
        try {
            return dao.delete(t);
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public int deleteById(ID id) {
        try {
            return dao.deleteById(id);
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public int deleteAll() {

        try {
            DeleteBuilder<T, ID> db = dao.deleteBuilder();
            return dao.delete(db.prepare());
        } catch (SQLException e) {
            return 0;
        }

    }

    @Override
    public T query(ID id) {
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<T> queryAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public long getCount() {
        try {
            return dao.countOf();
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    public boolean isOpen(String tableName) {
        return dao.getConnectionSource().isOpen(tableName);
    }

    @Override
    public void insertOrUpdate(T t) {
        try {
            dao.createOrUpdate(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void batchInsertOrUpdate(final List<T> data) {
        try {
            dao.callBatchTasks(new Callable<Void>() {

                @Override
                public Void call() throws Exception {
                    try {
                        for (T t : data) {
                            dao.createOrUpdate(t);
                        }
                    } catch (Exception e) {
                        return null;
                    }
                    return null;

                }
            });
        } catch (Exception e) {

        }
    }

    @Override
    public void batchInsert(final List<T> data) {
        if (data == null || data.isEmpty())
            return;
        try {
            dao.callBatchTasks(new Callable<Void>() {

                @Override
                public Void call() throws Exception {
                    for (T t : data) {
                        dao.create(t);
                    }
                    return null;

                }
            });
        } catch (Exception e) {

        }
    }

    @Override
    public void batchUpdate(final List<T> data) {
        if (data == null || data.isEmpty())
            return;
        try {
            dao.callBatchTasks(new Callable<Void>() {

                @Override
                public Void call() throws Exception {
                    for (T t : data) {
                        dao.update(t);
                    }
                    return null;

                }
            });
        } catch (Exception e) {

        }
    }

    @Override
    public int batchDelete(List<ID> ids) {
        try {
            return dao.deleteIds(ids);
        } catch (SQLException e) {
            return 0;
        }
    }

}
