package com.ha.cjy.libcommon.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;


import com.ha.cjy.common.ui.widget.DefaultViewPagerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 游戏viewpager控件
 * Created by cjy on 2018/7/18.
 */

public class GameViewPager extends DefaultViewPagerView {
    /**
     * 3个列表页面
     */
    private GameListView signUpListView,gamingListView,finishListView;

    public GameViewPager(Context context) {
        super(context);
    }

    public GameViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public List<String> initTabTitles() {
        List<String> titles = new ArrayList<>();
        for (int i =0; i < 3; i++){
            String text = "标题"+i;
            titles.add(text);
        }
        return titles;
    }

    @Override
    public List<View> initPageViews() {
        List<View> listViews = new ArrayList<>();
        signUpListView = new GameListView(mContext,0);
        gamingListView = new GameListView(mContext,1);
        finishListView = new GameListView(mContext,2);
        listViews.add(signUpListView);
        listViews.add(gamingListView);
        listViews.add(finishListView);
        return listViews;
    }

    @Override
    public void initRequestData(int pageIndex) {
        switch (pageIndex){
            case 0:{
                signUpListView.requestionData();
                break;
            }
            case 1:{
                gamingListView.requestionData();
                break;
            }
            case 2:{
                finishListView.requestionData();
                break;
            }
        }
    }
}
