package com.ha.cjy.common.ui.widget;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ha.cjy.common.R;
import com.ha.cjy.common.ui.base.BaseView;

/**
 * 默认的底部tab控件，通常出现在首页，进行页面的切换
 * Created by cjy on 2018/7/18.
 */

public class DefaultBottomtTabLayout extends BaseView {
    private final static String TAG = "DefaultBottomtTabLayout";

    /**
     * 底部tab控件
     */
    private TabLayout mBottomTabLayout;
    /**
     * 红点图标
     */
    private ImageView mIvRedPoint;
    /**
     * tab图标和名称
     */
    private int[] mTabIconList;
    private String[] mTabNameList;
    /**
     * tab点击回调
     */
    private SwitchTabCallback mCallback;


    public DefaultBottomtTabLayout(Context context) {
        super(context);
    }

    public DefaultBottomtTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置回调监听
     * @param callback
     */
    public void setSwitchTabCallback(SwitchTabCallback callback){
        mCallback = callback;
    }

    @Override
    public void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.default_bottom_tablayout,this);
        mBottomTabLayout = view.findViewById(R.id.bottom_tab);
    }

    /**
     * 创建tab item
     * @param position
     * @return
     */
    private View getTabView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.default_bottom_tablayout_item, null);
        ImageView ivIcon = view.findViewById(R.id.iv_main_tab_item_icon);
        ivIcon.setImageResource(mTabIconList[position]);
        TextView tvName = view.findViewById(R.id.tv_main_tab_item_name);
        tvName.setText(mTabNameList[position]);
        mIvRedPoint = view.findViewById(R.id.iv_redpoint);
        return view;
    }

    /**
     * 设置tab的图标和文本信息
     */
    public void setTabInfo(int[] tabIcons,String[] tabNames){
        if (tabIcons != null){
            mTabIconList = tabIcons;
        }
        if (tabNames != null){
            mTabNameList = tabNames;
        }
        int count  = findMin();
        if (mBottomTabLayout != null) {
            mBottomTabLayout.removeAllTabs();
            for (int i = 0; i < count; ++i) {
                mBottomTabLayout.addTab(mBottomTabLayout.newTab().setCustomView(getTabView(i)));
            }
        }
    }

    /**
     * 设置红点图标的显示隐藏
     * @param visibale
     */
    public void setRedPointVisibility(int visibale){
        if (mIvRedPoint != null){
            mIvRedPoint.setVisibility(visibale);
        }
    }

    /**
     * 查找最小值，避免图标数组与文本数组长度不等导致下标越界的异常
     * @return
     */
    private int findMin(){
        int iconLength = mTabIconList.length;
        int nameLength = mTabNameList.length;
        if (iconLength >= nameLength){
            return nameLength;
        }else{
            return iconLength;
        }
    }

    @Override
    public void initData() {
        if (mTabNameList == null){
            mTabNameList = new String[]{"首页","消息","我的"};
        }
        if (mTabIconList == null){
            mTabIconList = new int[]{R.drawable.icon_bottom_tablayout, R.drawable.icon_bottom_tablayout, R.drawable.icon_bottom_tablayout};
        }
        for (int i = 0; i < mTabNameList.length; i++) {
            mBottomTabLayout.addTab(mBottomTabLayout.newTab().setCustomView(getTabView(i)));
        }
    }

    @Override
    public void initListener() {
        mBottomTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (mCallback != null){
                    mCallback.switchTab(position);
                }else{
                    Log.e(TAG,"必须设置SwitchTabCallback监听，否则是收不到tab点击事件的！！！");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 获取TabLayout对象
     * @return
     */
    public TabLayout getTabLayout(){
        return mBottomTabLayout;
    }

    /**
     * 设置选中tab
     * @param position
     */
    public void setSelect(int position){
        if (mBottomTabLayout != null){
            int count =  mBottomTabLayout.getTabCount();
            if (position > 0 && position < count) {
                mBottomTabLayout.getTabAt(position).select();
            }
        }
    }

    /**
     * 页面切换监听
     */
    public interface SwitchTabCallback{
        void switchTab(int position);
    }
}
