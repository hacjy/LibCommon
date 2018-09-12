package com.ha.cjy.common.ui.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.ha.cjy.common.ui.IInitView;

/**
 * View基类
 * Created by cjy on 2018/7/17.
 */

public abstract class BaseView extends RelativeLayout implements IInitView {
    public Context mContext;

    public BaseView(Context context) {
        super(context);
        this.init(context);
    }

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    private void init(Context context) {
        mContext = context;
        this.initDataBeforeView();
        this.initView();
        this.initData();
        this.initListener();
    }

    @Override
    public void initDataBeforeView() {

    }
}
