package com.ha.cjy.common.ui.constants;

import com.ha.cjy.common.util.bean.LoginThirdInfo;

/**
 * EventBus事件集合
 * Created by cjy on 2018/7/24.
 */

public class EventCfg {

    /**
     * 点击推送消息的处理事件
     */
    public static class ClickPushNoticationEvent{
        /**
         * 推送的数据
         */
        public String pushData;

        public ClickPushNoticationEvent(String pushData) {
            this.pushData = pushData;
        }
    }

    /**
     * 第三方登录
     */
    public static class LoginThirdEvent {
        /**
         * 第三方登录平台type
         */
        public ELogin loginThirdType;
        public LoginThirdInfo info;

        public LoginThirdEvent( ELogin loginThirdType) {
            this.loginThirdType = loginThirdType;
        }

        public LoginThirdEvent( ELogin loginThirdType,LoginThirdInfo info) {
            this.loginThirdType = loginThirdType;
            this.info = info;
        }
    }

    /**
     * 登入回调信息
     */
    public static class LoginResultEvent {
        public ELoginStaute loginStaute;
        public ELoginType loginType;

        public LoginResultEvent(ELoginStaute resultType) {
            this.loginStaute = resultType;
        }

        public LoginResultEvent(ELoginStaute resultType, ELoginType loginType) {
            this.loginStaute = resultType;
            this.loginType = loginType;
        }
    }

    /**
     * 分享结果
     */
    public static class ShareResultEvent {
        public static final int success = 1;
        public static final int error = 2;
        public static final int cancel = 3;
        //搜索的关键词
        public int ShareType;
        public EShare eShare;

        public ShareResultEvent(int ShareType) {
            this.ShareType = ShareType;
        }

        public ShareResultEvent(int shareType, EShare eShare) {
            ShareType = shareType;
            this.eShare = eShare;
        }
    }

}
