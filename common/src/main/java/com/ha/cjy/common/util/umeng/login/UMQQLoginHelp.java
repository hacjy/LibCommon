package com.ha.cjy.common.util.umeng.login;

import android.app.Activity;
import android.content.Context;

import com.ha.cjy.common.ui.constants.ELogin;
import com.ha.cjy.common.ui.constants.EventCfg;
import com.ha.cjy.common.util.StringUtil;
import com.ha.cjy.common.util.bean.LoginThirdInfo;
import com.ha.cjy.common.util.bean.QQUnionidInfo;
import com.ha.cjy.common.util.http.BaseHttpUtil;
import com.ha.cjy.common.util.umeng.BaseAuth;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import rx.Subscriber;

/**
 * 友盟QQ登录调用类
 */
public class UMQQLoginHelp extends BaseAuth {

    String openid ;
    String uid ;
    String name ;
    String pic ;
    String gender;
    String token ;
      int sex;
    public UMQQLoginHelp(Context mContext) {
        super(mContext);
    }

    @Override
    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
        try {
            super.onComplete(share_media,i,map);
             openid =   map.get("openid");
             uid =   map.get("uid");
             name =  map.get("screen_name");
             pic =    map.get("profile_image_url");
             gender =   map.get("gender");
            token  =  map.get("access_token");
            if(StringUtil.isEmpty(name)){
                name  = "未命名";
            }
             sex = 0;
            if(gender.equals("男")){
                sex = 1;
            }else  if(gender.equals("女")){
                sex = 2;
            }
            //获取qq unionid
            BaseHttpUtil.loadQQUnionid(new QQSubscriber(),token,1);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void auth() {
       super.auth();
        SHARE_MEDIA platform = SHARE_MEDIA.QQ;//QQ授权
        UMShareAPI.get(mContext).getPlatformInfo((Activity) mContext, platform, this);
    }

  private class QQSubscriber extends Subscriber {

      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {
        e.printStackTrace();
      }

      @Override
      public void onNext(Object o) {
          QQUnionidInfo info = (QQUnionidInfo) o;
          LoginThirdInfo thirdInfo = new LoginThirdInfo(openid,info.unionid,name,pic,gender,token,sex);
          //第三方登录
          EventBus.getDefault().post(new EventCfg.LoginThirdEvent(ELogin.E_QQ,thirdInfo));
      }
  }
}
