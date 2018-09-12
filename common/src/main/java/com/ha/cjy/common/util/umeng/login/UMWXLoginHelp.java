package com.ha.cjy.common.util.umeng.login;

import android.app.Activity;
import android.content.Context;

import com.ha.cjy.common.ui.constants.ELogin;
import com.ha.cjy.common.ui.constants.EventCfg;
import com.ha.cjy.common.util.StringUtil;
import com.ha.cjy.common.util.bean.LoginThirdInfo;
import com.ha.cjy.common.util.umeng.BaseAuth;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

/**
 * 友盟微信调用类
 */
public class UMWXLoginHelp extends BaseAuth {

    public UMWXLoginHelp(Context mContext) {
        super(mContext);
    }

    @Override
    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
        try {
            super.onComplete(share_media,i,map);
            String openid = map.get("openid");
            String uid = map.get("unionid");
            String name = map.get("screen_name");
            String pic = map.get("profile_image_url");
            String gender = map.get("gender");
            if (StringUtil.isEmpty(name)) {
                name = "未命名";
            }
            int sex = 0;
            if (gender.equals("1")) {
                sex = 1;
            } else if (gender.equals("2")) {
                sex = 2;
            }
            //第三方登录
            LoginThirdInfo thirdInfo = new LoginThirdInfo(openid,"",name,pic,gender,"",sex);
            EventBus.getDefault().post(new EventCfg.LoginThirdEvent(ELogin.E_WEIXI,thirdInfo));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void auth() {
        super.auth();
        SHARE_MEDIA platform = SHARE_MEDIA.WEIXIN;
        UMShareAPI.get(mContext).getPlatformInfo((Activity) mContext, platform, this);
    }
}
