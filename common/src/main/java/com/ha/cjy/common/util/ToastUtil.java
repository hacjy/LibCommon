package com.ha.cjy.common.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 解决miuitoast带有app名称的问题
 * Created by cjy on 2018/7/12.
 */

public class ToastUtil {
    private static Toast mToast;

    public static void showToast(Context context, String text) {
        if (mToast == null) {
            //解决miuitoast带有app名称的问题
            mToast=Toast.makeText(context,null,0);
            mToast.setText(text);
        } else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            mToast.setText(text);
        }
        mToast.show();
    }
}
