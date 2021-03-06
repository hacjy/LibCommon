package com.ha.cjy.libcommon.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.ha.cjy.common.ui.widget.list.CommonRecyclerViewAdapter;
import com.ha.cjy.common.ui.widget.list.DefaultRecyclerView;
import com.ha.cjy.common.ui.widget.list.IAdapterHelp;
import com.ha.cjy.common.ui.widget.list.IHttpPresenter;


/**
 * 游戏列表
 * Created by cjy on 2018/7/17.
 */

public class GameEditListView extends DefaultRecyclerView {
    /**
     * 是否初始化完了,主要是在tab（viewpager）页面有实际的用处
     */
    private boolean isAlreadyInitData = false;
    /**
     * 类型
     */
    private int type = 0;
    /**
     * 适配器
     */
    private GameEditListViewAdapter adapter;

    public GameEditListView(Context context) {
        super(context);
    }

    public GameEditListView(Context context, int type) {
        super(context);
        this.type = type;
    }

    public GameEditListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public IHttpPresenter getIHttpPresenter() {
        return new GameEditListViewPresenter(this);
    }

    @Override
    public IAdapterHelp getRecyclerViewAdapter() {
        adapter = new GameEditListViewAdapter(getContext());
        adapter.setOnItemClick(new CommonRecyclerViewAdapter.IOnItemCilick() {
            @Override
            public void onItemClick(View v, int position) {
                ((GameEditListViewPresenter) mP).onItemClick(position);
            }
        });
        return adapter;
    }

    @Override
    public void onClickLoadFailed() {
        mP.initRequestData();
    }

    @Override
    public void onClickLoadEmpty() {
        mP.initRequestData();
    }

    public void requestionData(){
        //isAlreadyInitData用来控制第一次加载，再次点击不主动加载
        if (!isAlreadyInitData){
            ((GameEditListViewPresenter) mP).setType(this.type);
            isAlreadyInitData = true;
            mP.initRequestData();
        }
        //不是第一次加载，是否是空数据，是的话再次请求 加isAlreadyInitData这个判断避免重复发请求
        if (isAlreadyInitData && (getAdapter() == null || (getAdapter() != null && getAdapter().getCount() == 0))){
            mP.initRequestData();
        }
    }

    /**
     * 设置编辑状态
     * @param isEdit
     */
    public void setEdit(boolean isEdit){
        if (adapter != null){
            adapter.setIsEdit(isEdit);
            adapter.notifyDataSetChanged();
            if (!isEdit){
                adapter.resetData();
                getRefreshLayout().setEnabled(true);
            }else{
                getRefreshLayout().setEnabled(false);
            }
        }
    }

}
