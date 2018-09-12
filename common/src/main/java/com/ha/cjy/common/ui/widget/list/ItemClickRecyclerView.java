package com.ha.cjy.common.ui.widget.list;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * 支持item click的RecyclerView
 * Created by cjy on 2018/7/17.
 */

public class ItemClickRecyclerView extends HeaderFooterRecyclerView {
    /**
     * 手势
     */
    private GestureDetectorCompat mCompat;
    /**
     * 单击监听
     */
    private ItemClickRecyclerView.IOnItemCilick mItemClick;
    /**
     * 长按监听
     */
    private ItemClickRecyclerView.IOnItemLongCilick mItemLongCilick;

    public ItemClickRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    public ItemClickRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public ItemClickRecyclerView(Context context) {
        super(context);
        this.init();
    }

    private void init() {
        GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {
            public void onLongPress(MotionEvent e) {
                if(ItemClickRecyclerView.this.mItemLongCilick != null) {
                    View view = ItemClickRecyclerView.this.findChildViewUnder(e.getX(), e.getY());
                    int i = ItemClickRecyclerView.this.getChildPosition(view);
                    ItemClickRecyclerView.this.mItemLongCilick.onItemLongClick(view, i);
                }
            }

            public boolean onSingleTapConfirmed(MotionEvent e) {
                if(ItemClickRecyclerView.this.mItemClick == null) {
                    return false;
                } else {
                    View view = ItemClickRecyclerView.this.findChildViewUnder(e.getX(), e.getY());
                    int i = ItemClickRecyclerView.this.getChildPosition(view);
                    ItemClickRecyclerView.this.mItemClick.onItemClick(view, i);
                    return super.onSingleTapConfirmed(e);
                }
            }
        };
        this.mCompat = new GestureDetectorCompat(this.getContext(), gestureListener);
        RecyclerView.OnItemTouchListener itemTouchListener = new RecyclerView.OnItemTouchListener() {
            public void onTouchEvent(RecyclerView arg0, MotionEvent arg1) {
            }

            public void onRequestDisallowInterceptTouchEvent(boolean b) {
            }

            public boolean onInterceptTouchEvent(RecyclerView arg0, MotionEvent arg1) {
                if(ItemClickRecyclerView.this.mCompat != null) {
                    ItemClickRecyclerView.this.mCompat.onTouchEvent(arg1);
                }

                return false;
            }
        };
        this.addOnItemTouchListener(itemTouchListener);
    }

    public void setCompat(GestureDetectorCompat mCompat) {
        this.mCompat = mCompat;
    }

    public void setOnItemClick(ItemClickRecyclerView.IOnItemCilick mItemClick) {
        this.mItemClick = mItemClick;
    }

    public void setOnItemLongCilick(ItemClickRecyclerView.IOnItemLongCilick mItemLongCilick) {
        this.mItemLongCilick = mItemLongCilick;
    }


    public interface IOnItemLongCilick {
        void onItemLongClick(View view, int position);
    }

    public interface IOnItemCilick {
        void onItemClick(View view, int position);
    }
}
