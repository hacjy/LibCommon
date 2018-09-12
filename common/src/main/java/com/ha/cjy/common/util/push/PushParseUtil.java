package com.ha.cjy.common.util.push;

import android.content.Context;
import android.text.TextUtils;

import com.ha.cjy.common.ui.constants.EventCfg;

import org.greenrobot.eventbus.EventBus;


/**
 * 推送的解析类
 */

public class PushParseUtil {

    //解析推送内容 {"MsgContent":"推送测试内容"}
    public static void parsePushData(final Context context, final String data) {
        if (TextUtils.isEmpty(data)) {
            return;
        }
//        String content = "";
//        try {
//            JSONObject jsonObject = new JSONObject(data);
//            content = jsonObject.optString("MsgContent");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        //发送通知：点击推送通知的处理事件
        EventBus.getDefault().post(new EventCfg.ClickPushNoticationEvent(data));
//        PushInfo info = (PushInfo) JsonUtil.parsData(content, PushInfo.class);
//        if (!MainActivityPresenter.isAlive) {
//            jumpGuideActivity(context, info);
//        } else {
//            if (info.PushType == 1) {
//                PushDataInfo pushDataInfo = (PushDataInfo) JsonUtil.parsData(info.Data, PushDataInfo.class);
//                if (LoginManager.getInstance().isLogin()) {
////              IntentUtil.toWebActivity(context, pushDataInfo.LinkUrl, "", "");
//                    Intent intent = new Intent(context, WebToolbarActivity.class);
//                    String linkUrl = HttpUtil.getUrl(pushDataInfo.LinkUrl);
//                    intent.putExtra("loadUrl", linkUrl);
//                    intent.putExtra("errUrl", "");
//                    intent.putExtra("title", "");
////                    intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
//                    //| Intent.FLAG_ACTIVITY_CLEAR_TASK
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    context.startActivity(intent);
//                } else {
//                    Intent intent = new Intent(context, LoginActivity.class);
//                    //| Intent.FLAG_ACTIVITY_CLEAR_TASK
////                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    context.startActivity(intent);
//                }
//            } else if (info.PushType == 2) {
//                PushDataInfo pushDataInfo = (PushDataInfo) JsonUtil.parsData(info.Data, PushDataInfo.class);
//                Intent intent = new Intent(context, MatchDetailActivity.class);
//                Bundle bundle = new Bundle();
////                long matchId = 0;
////                try {
////                    matchId = Long.parseLong(pushDataInfo.LinkUrl);
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
//                if (pushDataInfo != null) {
//                    bundle.putLong("matchId", pushDataInfo.MatchId);
//                } else {
//                    //做兼容性
//                    bundle.putLong("matchId", 0);
//                }
//                intent.putExtras(bundle);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                CLog.d("wulianshu", "push MatchDetailActivity:" + pushDataInfo.MatchId);
//                context.startActivity(intent);
//            }
//        }

    }
}