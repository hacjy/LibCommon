package com.ha.cjy.common.util.viewpager;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 通用的Viewpager适配器
 * Created by cjy on 2018/7/18.
 */

public class CommonViewPagerAdapter extends PagerAdapter {
    private List<? extends View> mViews;

    public void setViews(List<? extends View> views) {
        this.mViews = views;
    }

    public List<? extends View> getViews() {
        return this.mViews;
    }

    public CommonViewPagerAdapter(List<? extends View> views) {
        this.mViews = views;
    }

    public int getCount() {
        return this.mViews.size();
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    public Object instantiateItem(ViewGroup container, int position) {
        container.addView((View)this.mViews.get(position), 0);
        return this.mViews.get(position);
    }

    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    public int getItemPosition(Object object) {
        return -2;
    }
}
