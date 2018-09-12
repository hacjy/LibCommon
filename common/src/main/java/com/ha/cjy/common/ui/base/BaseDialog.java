package com.ha.cjy.common.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.WindowManager;

import com.ha.cjy.common.ui.IInitView;
import com.ha.cjy.common.ui.dialog.DialogManager;
import com.ha.cjy.common.util.ScreenUtil;

/**
 * Dialog基类
 * Created by cjy on 2018/7/17.
 */

public class BaseDialog extends Dialog implements IInitView{

    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initDataBeforeView();
        this.initView();
        this.initListener();
        this.initData();
        DialogManager.getInstance().addDialog(this);
    }

    public void show() {
        super.show();
    }

    public void dismiss() {
        super.dismiss();
        DialogManager.getInstance();
        DialogManager.getDialogStack().remove(this);
    }

    @Override
    public void initDataBeforeView() {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        this.getWindow().setGravity(Gravity.CENTER);
        lp.width = ScreenUtil.getScreenWidth(this.getContext());
        this.getWindow().setAttributes(lp);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }
}
