package com.ha.cjy.common.ui.update.download;


import com.ha.cjy.common.ui.base.BaseApplication;
import com.ha.cjy.common.util.download.db.DaoHelpImp;

/**
 * Created by willy on 2016/12/17.
 */

public class ApkDownloadDao extends DaoHelpImp<ApkDownloadInfo, String> {
    private static ApkDownloadDao instance;

    public static ApkDownloadDao getInstance(){
        if (instance == null){
            instance = new ApkDownloadDao();
        }
        return instance;
    }

    public ApkDownloadDao() {
        super(BaseApplication.getInstance(), XyLiveOrmLiteOpenHelper.class, ApkDownloadInfo.class);
    }
}
