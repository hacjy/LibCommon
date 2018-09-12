package com.ha.cjy.common.ui.update.download;

import android.content.Context;

import com.ha.cjy.common.util.download.db.OrmLiteOpenHelper;

/**
 * Created by willy on 2016/12/17.
 */

public class XyLiveOrmLiteOpenHelper extends OrmLiteOpenHelper {
    public XyLiveOrmLiteOpenHelper(Context context) {
        super(context);
    }

    static {
        DAO_CLASS_LIST.add(ApkDownloadInfo.class);
    }
}
