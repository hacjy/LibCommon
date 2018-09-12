package com.ha.cjy.common.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ha.cjy.common.R;


/**
 * 选择照片或者拍照的 popupwindow
 */
public class SelectPhotoPopupWindow extends PopupWindow {
    private TextView btn_take_photo, btn_pick_photo, btn_cancel;
    private View mMenuView;

    public SelectPhotoPopupWindow(final Context context, View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popup_select_photo, null);

        mMenuView.findViewById(R.id.pop_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
                params.alpha = 1f;
                ((Activity) context).getWindow().setAttributes(params);
                dismiss();
            }
        });

        btn_take_photo = (TextView) mMenuView.findViewById(R.id.btn_take_photo);
        btn_pick_photo = (TextView) mMenuView.findViewById(R.id.btn_pick_photo);
        btn_cancel = (TextView) mMenuView.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
                params.alpha = 1f;
                ((Activity) context).getWindow().setAttributes(params);
                dismiss();
            }
        });
        btn_pick_photo.setOnClickListener(itemsOnClick);
        btn_take_photo.setOnClickListener(itemsOnClick);
        this.setContentView(mMenuView);
        this.setWidth(AbsListView.LayoutParams.MATCH_PARENT);
        this.setHeight(AbsListView.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.AnimBottom);
        setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

//                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
//                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
                    params.alpha = 1f;
                    ((Activity) context).getWindow().setAttributes(params);
                    dismiss();
                }
                return true;
            }
        });

    }
}
