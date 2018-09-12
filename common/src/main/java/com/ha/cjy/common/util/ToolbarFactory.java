package com.ha.cjy.common.util;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ha.cjy.common.R;
import com.ha.cjy.common.ui.base.BaseToolbarActivity;

/**
 * 标题栏视图工厂
 * Created by cjy on 2018/7/19.
 */

public class ToolbarFactory {

    /**
     * 初始化toolbar
     *
     * @param activity
     * @param leftText
     * @param leftRes
     * @param title
     * @param rightText
     * @param rightRes
     * @param leftClick
     * @param rightClick
     */
    public static void initToolbar(BaseToolbarActivity activity, String leftText, int leftRes, String title, String rightText, int rightRes,
                                   View.OnClickListener leftClick, View.OnClickListener rightClick) {
        Toolbar toolbar = activity.getToolbar();
        TextView tvLeft = (TextView) toolbar.findViewById(R.id.titleLeftText);
        tvLeft.setText(leftText);
        tvLeft.setCompoundDrawablesWithIntrinsicBounds(leftRes, 0, 0, 0);
        TextView tvTitle = (TextView) toolbar.findViewById(R.id.titleCenterText);
        tvTitle.setText(title);
        TextView tvRight = (TextView) toolbar.findViewById(R.id.titleRightText);
        tvRight.setText(rightText);
        tvRight.setCompoundDrawablesWithIntrinsicBounds(0, 0, rightRes, 0);
        tvLeft.setOnClickListener(leftClick);
        tvRight.setOnClickListener(rightClick);
    }

    /**
     * 初始化左键返回，右键有具体功能的toolbar，纯文字
     *
     * @param activity
     * @param leftText
     * @param title
     * @param rightText
     * @param rightClick
     */
    public static void initLeftBackAndRightOnlyTextToolbar(final BaseToolbarActivity activity, String leftText, String title, String rightText, View.OnClickListener leftClick,View.OnClickListener rightClick) {
        initToolbar(activity, leftText, R.drawable.ic_return, title, rightText, 0, leftClick, rightClick);
    }

    /**
     * 初始化左键返回，右键有具体功能的toolbar，纯文字
     *
     * @param activity
     * @param leftText
     * @param title
     * @param rightText
     * @param rightClick
     */
    public static void initLeftBackAndRightOnlyTextToolbar(final BaseToolbarActivity activity, String leftText, String title, String rightText, View.OnClickListener rightClick) {
        initToolbar(activity, leftText, R.drawable.ic_return, title, rightText, 0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        }, rightClick);
    }

    /**
     * 初始化左键返回的toolbar
     *
     * @param activity
     * @param leftText
     * @param title
     * @param backClick
     */
    public static void initLeftBackToolbar(BaseToolbarActivity activity, String leftText, String title, View.OnClickListener backClick) {
        initToolbar(activity, leftText, R.drawable.ic_return, title, "", 0, backClick, null);
    }

    /**
     * 初始化左键返回的toolbar，默认返回直接退出activity
     *
     * @param activity
     * @param leftText
     * @param title
     */
    public static void initLeftBackToolbar(final BaseToolbarActivity activity, String leftText, String title) {
        initLeftBackToolbar(activity, leftText, title, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    /**
     * 初始化左键返回，右键功能的toolbar
     * @param activity
     * @param leftText
     * @param title
     * @param right
     * @param rightResId
     * @param rightClick
     */
    public static void initLeftBackRightFuncToolbar(final BaseToolbarActivity activity,String leftText,String title,String right,int rightResId,View.OnClickListener rightClick){
        initToolbar(activity, leftText, R.drawable.ic_return, title, right, rightResId, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        },rightClick);
    }
}
