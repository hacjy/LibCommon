package com.ha.cjy.common.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ha.cjy.common.R;
import com.ha.cjy.common.ui.base.BaseView;
import com.ha.cjy.common.util.viewpager.ViewPageViewHelp;

import java.util.ArrayList;
import java.util.List;

/**
 * 提供默认的tab+viewpager的页面
 * Created by cjy on 2018/7/18.
 */

public abstract class DefaultViewPagerView extends BaseView implements ViewPageViewHelp.IViewpageViewHelpCallBack {
    /**
     * ViewPager
     */
    private ViewPager mViewPager;
    /**
     * tab标题的容器
     */
    private LinearLayout mLayoutTab;
    /**
     * viewpager的帮助类
     */
    private ViewPageViewHelp viewPageViewHelp;
    /**
     * 页面集合
     */
    private List<View> listViews;

    public DefaultViewPagerView(Context context) {
        super(context);
    }

    public DefaultViewPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void initView() {
        //root为null的话，会导致页面显示不出来，原因暂时未知？
        View view = LayoutInflater.from(mContext).inflate(R.layout.default_viewpager_tab_view,this);
        mViewPager = view.findViewById(R.id.viewpager);
        mLayoutTab = view.findViewById(R.id.layout_tab);

        initViewPagerData();
    }

    /**
     * 初始化一些viewpager的数据和view
     */
    private void initViewPagerData(){

        List<View> tabViews = createTabTitles();
        listViews = initPageViews();
        viewPageViewHelp = new ViewPageViewHelp();
        viewPageViewHelp.setData(mViewPager,listViews,tabViews,0,this);
    }

    private List<View> createTabTitles(){
        List<View> tabViews = new ArrayList<>();
        List<String> titles = initTabTitles();
        if (titles != null && titles.size() > 0){
            int size = titles.size();
            for (int i = 0; i < size; i++){
                View view = createTabIndicationView(titles.get(i));
                tabViews.add(view);
            }
        }
        return tabViews;
    }

    private View createTabIndicationView(String text){
       View view = LayoutInflater.from(mContext).inflate(R.layout.defatlt_viewpager_tab_item,null);
       TextView textView = view.findViewById(R.id.tv_tab_item);
       textView.setText(text);
       mLayoutTab.addView(textView,new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1));
       return view;
    }

    @Override
    public void initData() {
        //第一个直接初始化
        initRequestData(0);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void setSelected(View view, int position,boolean isSelect) {
        view.setSelected(isSelect);
        if (isSelect){
            ((TextView)view).setTextColor(getResources().getColor(R.color.viewpager_tab_text_selectedcolor));
        }else{
            ((TextView)view).setTextColor(getResources().getColor(R.color.viewpager_tab_text_normalcolor));
        }
    }

    @Override
    public void onSelectedPage(View view, int position) {
        initRequestData(position);
    }


    /**
     * 初始化页面指示器标题集合
     * @return
     */
    public abstract List<String> initTabTitles();

    /**
     * 初始化页面视图集合
     * @return
     */
    public abstract List<View> initPageViews();

    /**
     * 初始化页面的默认请求
     * @param pageIndex 页面下标
     */
    public abstract void initRequestData(int pageIndex);
}
