package com.ha.cjy.common.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * 对话框工具
 * Created by cjy on 2018/7/16.
 */

public class DialogUtil {

    public static synchronized AlertDialog.Builder showConfirmDialog(Context context, int theme, String title, String message, DialogInterface.OnClickListener positiveEvent, String positiveBtnText, DialogInterface.OnClickListener negativeEvent, String negativeBtnText) {
        AlertDialog.Builder dialog = null;
        if (theme != 0) {
            dialog = new AlertDialog.Builder(context, theme);
        }else{
            dialog = new AlertDialog.Builder(context);
        }
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.setPositiveButton(positiveBtnText, positiveEvent);
        if (negativeBtnText != null && !negativeBtnText.equals("")) {
            if (negativeEvent != null) {
                dialog.setNegativeButton(negativeBtnText, negativeEvent);
            } else {
                dialog.setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }
        }

        dialog.show();
        return dialog;
    }
}
