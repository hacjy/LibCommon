package com.ha.cjy.common.util.viewpager;

import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**
 * Viewpage帮助类
 * 滑动与tab指示器联系
 * Created by cjy on 2018/7/18.
 */

public class ViewPageViewHelp implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager mViewPager;
    //指示器视图集合
    private List<View> mTabView;
    //页面集合
    private List<View> mViews;
    //页面变化回调
    private ViewPageViewHelp.IViewpageViewHelpCallBack mCallBack;

    public ViewPageViewHelp() {
    }

    /**
     * 设置数据
     * @param viewPager
     * @param views 页面集合
     * @param titles 指示器集合
     * @param startIndex 开始位置
     * @param callback 回调
     */
    public void setData(ViewPager viewPager, List<View> views, List<View> titles, int startIndex, ViewPageViewHelp.IViewpageViewHelpCallBack callback) {
        this.mViewPager = viewPager;
        this.mTabView = titles;
        this.mViews = views;
        this.mCallBack = callback;
        this.mViewPager.addOnPageChangeListener(this);
        CommonViewPagerAdapter mAdapter = new CommonViewPagerAdapter(views);
        this.mViewPager.setAdapter(mAdapter);

        for(int i = 0; i < this.mTabView.size(); ++i) {
            View v = (View)this.mTabView.get(i);
            v.setTag(Integer.valueOf(i));
            v.setOnClickListener(this);
        }

        this.mViewPager.setCurrentItem(startIndex);
        if(this.mCallBack != null) {
            if(!this.mViews.isEmpty()) {
                this.mCallBack.onSelectedPage((View)this.mViews.get(startIndex),startIndex);
            }

            if(this.mTabView != null && this.mTabView.size() > 0) {
                this.mCallBack.setSelected((View)this.mTabView.get(startIndex),startIndex, true);
            }
        }

    }

    public void onClick(View v) {
        int index = ((Integer)v.getTag()).intValue();
        this.mViewPager.setCurrentItem(index);
    }

    /**
     * 设置选中的tab
     * @param index
     */
    private void setTabs(int index) {
        int size = this.mTabView.size();
        if(size > 1) {
            for(int i = 0; i < size; ++i) {
                if(i == index) {
                    if(this.mCallBack != null) {
                        this.mCallBack.setSelected((View)this.mTabView.get(i),i, true);
                        this.mCallBack.onSelectedPage((View)this.mViews.get(i),i);
                    }
                } else if(this.mCallBack != null) {
                    this.mCallBack.setSelected((View)this.mTabView.get(i), i,false);
                }
            }

        }
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    public void onPageSelected(int position) {
        this.setTabs(position);
    }

    public void onPageScrollStateChanged(int state) {
    }

    public List<View> getViews() {
        return this.mViews;
    }


    /**
     * 回调接口
     */
    public interface IViewpageViewHelpCallBack {
        /**
         * 设置Tab是否选中的样式
         * @param view
         * @param isSelect
         */
        void setSelected(View view, int position, boolean isSelect);

        /**
         * 选中的页面
         * @param view
         */
        void onSelectedPage(View view, int position);
    }
}
