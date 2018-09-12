package com.ha.cjy.common.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.ha.cjy.common.R;
import com.ha.cjy.common.ui.base.BaseDialog;
import com.ha.cjy.common.util.ScreenUtil;

/**
 * 加载对话框
 * Created by cjy on 2018/7/17.
 */

public class LoadingDialog extends BaseDialog {
    private static LoadingDialog mDialog;
    private ILoadingCallBack mCallBack;
    //是否可以取消
    private boolean canCancel = false;

    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    public LoadingDialog(Context context, int theme, boolean canCancel){
        super(context, theme);
        this.canCancel = canCancel;
    }


    //显示dilog
    public static LoadingDialog showDialog(Context context) {
        if (mDialog != null) {
            mDialog.dismissDialog();
            mDialog = null;
        }
        if (mDialog == null) {
            mDialog = new LoadingDialog(context, R.style.style_loading_dialog);
            mDialog.show();
        }
        return mDialog;
    }

    public static LoadingDialog showDialog(Context context,boolean canCancel){
        if (mDialog != null) {
            mDialog.dismissDialog();
            mDialog = null;
        }
        if (mDialog == null) {
            mDialog = new LoadingDialog(context, R.style.style_loading_dialog,canCancel);
            mDialog.show();
        }
        return mDialog;
    }


    //销毁dialog
    public static void dismissDialog() {

        try {
            if (mDialog != null) {
                mDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {
        try {
            super.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initDataBeforeView() {
        Window win = getWindow();
//        win.setType(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY);
        //间距为0
        win.getDecorView().setPadding(0, 0, 0, 0);
        win.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = ScreenUtil.dip2px(getContext(), 100);
        lp.height = ScreenUtil.dip2px(getContext(), 100);

        win.setAttributes(lp);
        setCancelable(canCancel);

    }

    @Override
    public void initView() {
        setContentView(R.layout.loading_view);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
        setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (KeyEvent.KEYCODE_BACK == keyCode) {
                    dismiss();
                }
                return false;
            }
        });
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mCallBack != null) {
            mCallBack.dismissCallBack();
        }
        mDialog = null;
    }

    public interface ILoadingCallBack {
        void dismissCallBack();
    }

    public LoadingDialog setCallBack(ILoadingCallBack mCallBack) {
        this.mCallBack = mCallBack;
        return this;
    }
}
