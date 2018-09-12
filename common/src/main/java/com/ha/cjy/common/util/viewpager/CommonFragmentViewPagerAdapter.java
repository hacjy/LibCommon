package com.ha.cjy.common.util.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ha.cjy.common.ui.base.BaseFragment;

import java.util.List;

/**
 * 通用加载Fragment的Viewpager Adapter
 * Created by cjy on 2018/7/23.
 */

public class CommonFragmentViewPagerAdapter extends FragmentPagerAdapter {
    private List<? extends BaseFragment> mFragments;

    public CommonFragmentViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public CommonFragmentViewPagerAdapter(FragmentManager fm, List<? extends BaseFragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments==null?null:mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments==null?0:mFragments.size();
    }
}
