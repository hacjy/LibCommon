package com.ha.cjy.common.ui.stateview;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ha.cjy.common.R;

/**
 * 视图工厂：生成各种状态视图，包括默认的状态视图
 * Created by cjy on 2018/7/17.
 */

public class LocalStateViewFactory {
    public final static int CLICK_ACTION_FAILED = 2;
    public final static int CLICK_ACTION_EMPTY = 3;

    /**
     * 添加视图
     * 根视图必须是LinearLayout或者RelativeLayout
     *
     * @param view
     */
    private static void addView(View view, View contentView) {
        View parentView = (View) contentView.getParent();
        if (parentView != null) {
            if (parentView instanceof RelativeLayout) {
                RelativeLayout parent = (RelativeLayout) parentView;
                parent.addView(view);
                RelativeLayout.LayoutParams l = new RelativeLayout.LayoutParams(view.getLayoutParams());
                l.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                l.width = RelativeLayout.LayoutParams.MATCH_PARENT;
                l.height = RelativeLayout.LayoutParams.MATCH_PARENT;
                view.setLayoutParams(l);
            } else if (parentView instanceof LinearLayout) {
                LinearLayout parent = (LinearLayout) parentView;
                parent.addView(view);
                LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(view.getLayoutParams());
                l.width = LinearLayout.LayoutParams.MATCH_PARENT;
                l.height = LinearLayout.LayoutParams.MATCH_PARENT;
                l.gravity = Gravity.CENTER;
                view.setLayoutParams(l);
            } else {
                throw new IllegalArgumentException("ParentView must be a RelativeLayout or LinearLayout!!!");
            }
        }
    }

    /**
     * 页面加载失败页面
     *
     * @param context
     * @return
     */
    public static View getLoadFailedView(Context context, int resid, View contentView, View.OnClickListener onClickListener) {
        return getLoadView(context,CLICK_ACTION_FAILED,resid,contentView,onClickListener);
    }

    /**
     * 页面加载空页面
     *
     * @param context
     * @return
     */
    public static View getLoadEmptyView(Context context, int resid, View contentView, View.OnClickListener onClickListener) {
        return getLoadView(context,CLICK_ACTION_EMPTY,resid,contentView,onClickListener);
    }

    /**
     * 定制视图
     * @param context
     * @param clickAction
     * @param resid
     * @param contentView
     * @param onClickListener
     * @return
     */
    private static View getLoadView(Context context, int clickAction,int resid, View contentView, View.OnClickListener onClickListener){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resid, null);
        if (onClickListener != null) {
            view.setTag(clickAction);
            view.setOnClickListener(onClickListener);
        }
        view.setVisibility(View.GONE);
        addView(view, contentView);
        return view;
    }

    /**
     * 默认的失败视图
     * @param context
     * @param contentView
     * @param onClickListener
     * @return
     */
    public static View getLoadDefaultFailedView(Context context, View contentView, View.OnClickListener onClickListener){
        View view = getLoadDefaultView(context,contentView,0,CLICK_ACTION_FAILED,context.getString(R.string.txt_load_failed_view),onClickListener);
        return view;
    }

    /**
     * 默认的空视图
     * @param context
     * @param contentView
     * @param onClickListener
     * @return
     */
    public static View getLoadDefaultEmptyView(Context context, View contentView, View.OnClickListener onClickListener){
        View view = getLoadDefaultView(context,contentView,0,CLICK_ACTION_EMPTY,context.getString(R.string.txt_load_empty_view),onClickListener);
        return view;
    }

    /**
     * 默认的视图
     * @param context
     * @param contentView
     * @param onClickListener
     * @return
     */
    public static View getLoadDefaultView(Context context, View contentView,int drawableId,int clickAction,String text, View.OnClickListener onClickListener){
        View view = LayoutInflater.from(context).inflate(R.layout.load_state_view_no_success, null);
        TextView nodataText = view.findViewById(R.id.tv_nodataText);
        ImageView nodataIcon = view.findViewById(R.id.iv_nodataIcon);
        if (text != null && text.length() >= 0) {
            nodataText.setText(text);
        }
        if (drawableId != 0){
            nodataIcon.setImageResource(drawableId);
        }
        if (onClickListener != null) {
            view.setTag(clickAction);
            view.setOnClickListener(onClickListener);
        }
        view.setVisibility(View.GONE);
        addView(view, contentView);
        return view;
    }

}
