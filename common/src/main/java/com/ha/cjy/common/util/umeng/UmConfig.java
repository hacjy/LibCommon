package com.ha.cjy.common.util.umeng;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.UMShareAPI;

/**
 * 友盟配置信息
 */
public class UmConfig {

    public void config(Context context) {
        UMShareAPI.get(context);
        Config.DEBUG = false;
        Config.IsToastTip = false;//关闭toast
        Config.dialogSwitch = false;//关闭dialog
//        PlatformConfig.setWeixin("wx6fb15236e5b48598", "520508150ab20dc87625fef69e90611e");
//        PlatformConfig.setQQZone("101366543","7910ccd7ad2f7be368b861da0810aa87");
//        PlatformConfig.setSinaWeibo("575302933", "2f2af11eb22fb98c943854d5fbab1d00");
        MobclickAgent.setScenarioType( context, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }
}
