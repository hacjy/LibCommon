package com.ha.cjy.common.ui.widget.list;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.ha.cjy.common.R;
import com.ha.cjy.common.ui.ILoadViewResult;
import com.ha.cjy.common.ui.base.BaseView;
import com.ha.cjy.common.ui.stateview.LoadStateViewHelper;
import com.ha.cjy.common.ui.stateview.LoadStateViewResultHelper;
import com.ha.cjy.common.ui.stateview.LocalStateViewFactory;

/**
 * 默认列表控件
 * 1、支持头部底部视图添加
 * 2、支持上拉加载，下拉刷新
 * 3、支持item点击事件
 * Created by cjy on 2018/7/17.
 */

public abstract class DefaultRecyclerView extends BaseView implements IRecyclerLoadView{
    /**
     * 刷新控件
     */
    protected DefaultSwipeRefreshLayout mSrfly;
    /**
     * 列表
     */
    protected LoadRecyclerView mRecyclerView;
    /**
     * 适配器
     */
    protected IAdapterHelp mAdapter;
    /**
     * 请求数据的p层接口
     */
    protected IHttpPresenter mP;
    /**
     * 列表item装饰器
     */
    protected RecyclerView.ItemDecoration itemDecoration;
    /**
     * 列表的布局管理器
     */
    private RecyclerView.LayoutManager manager;

    public DefaultRecyclerView(Context context) {
        super(context);
    }

    public DefaultRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void initListener() {
        //下拉刷新
        mSrfly.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrfly.setRefreshing(true);
                mP.refreshLoad();
            }
        });
        //上拉加载
        mRecyclerView.prepareLoad(new INextLoadCallBack() {
            @Override
            public void loadNextPage() {
                mP.load();
            }
        }, getPreloadNum());
    }

    @Override
    public void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.base_list_refresh_view, this);
        mSrfly = (DefaultSwipeRefreshLayout) findViewById(R.id.swrf_ly);
        mRecyclerView = (LoadRecyclerView) findViewById(R.id.recycler_view_list);
        if (manager == null) {
            manager = new LinearLayoutManager(getContext());
        }
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setLoadViewResult(getLocalLoadHelper());
        mSrfly.setChildView(mRecyclerView);
    }

    /**
     * 单击监听
     * @param onItemClick
     */
    public void setOnItemClick(ItemClickRecyclerView.IOnItemCilick onItemClick) {
        mRecyclerView.setOnItemClick(onItemClick);
    }

    /**
     * 长按点击监听
     */
    public void setOnItemLongCilick(ItemClickRecyclerView.IOnItemLongCilick mItemLongCilick) {
        mRecyclerView.setOnItemLongCilick(mItemLongCilick);
    }

    @Override
    public void initData() {
        mP = getIHttpPresenter();

    }

    public DefaultSwipeRefreshLayout getRefreshLayout(){
        return mSrfly;
    }

    /**
     * 设置默认的recyclerView的layoutManager,若不设置的话,默认LinearLayoutManager
     *
     * @param manager 设置的layoutManager
     */
    public void setDefaultLayoutManager(RecyclerView.LayoutManager manager) {
        this.manager = manager;
    }

    /**
     * 设置默认的recyclerview的itemDecoration，不设置的话就不处理
     *
     * @param itemDecoration
     */
    public void setItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        this.itemDecoration = itemDecoration;
    }

    @Override
    public IAdapterHelp getAdapter() {
        if (mAdapter == null) {
            mAdapter = getRecyclerViewAdapter();
            mRecyclerView.setAdapter((RecyclerView.Adapter) mAdapter);
            if (itemDecoration != null) {
                mRecyclerView.addItemDecoration(itemDecoration);
            }
        }
        return mAdapter;
    }

    //添加头部视图
    public void addHeaderView(View view) {
        mRecyclerView.addHeaderView(view);
    }

    //移除头部视图
    public void removeHeaderView() {
        mRecyclerView.removeHeaderView();
    }

    //添加底部视图
    public void addFootView(View view) {
        mRecyclerView.addFootView(view);
    }

    //设置预加载的下标
    public int getPreloadNum() {
        return 5;
    }

    //获取p层
    public abstract IHttpPresenter getIHttpPresenter();

    //获取适配器
    public abstract IAdapterHelp getRecyclerViewAdapter();

    public void setHttpPresenter(IHttpPresenter iHttpPresenter) {
        mP = iHttpPresenter;
    }

    public void setAdapterHelp(IAdapterHelp adapterHelp) {
        if (mAdapter == null) {
            mAdapter = adapterHelp;
            mRecyclerView.setAdapter((RecyclerView.Adapter) mAdapter);
            if (itemDecoration != null) {
                mRecyclerView.addItemDecoration(itemDecoration);
            }
        }
    }

    @Override
    public ILoadViewResult getLoadViewResultState() {
        return mSrfly;
    }

    @Override
    public void onLoadSuccess() {
        mSrfly.onLoadSuccess();
    }

    @Override
    public void onLoadFailed() {
        mSrfly.onLoadFailed();
    }

    @Override
    public void onLoadEmpty() {
        mSrfly.onLoadEmpty();
    }

    /**
     * 初始化默认的状态视图帮助类
     */
    public LoadStateViewResultHelper getLocalLoadHelper(){
        LoadStateViewHelper stateViewHelper = new LoadStateViewHelper(getContext(), mSrfly, null, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickAction = (int) v.getTag();
                switch (clickAction){
                    case LocalStateViewFactory.CLICK_ACTION_FAILED:{
                        onClickLoadFailed();
                        break;
                    }
                    case LocalStateViewFactory.CLICK_ACTION_EMPTY:{
                        onClickLoadEmpty();
                        break;
                    }
                }
            }
        });
        LoadStateViewResultHelper resultHelper = new LoadStateViewResultHelper(stateViewHelper);
        return resultHelper;
    }

    /**
     * 加载失败
     */
    public abstract void onClickLoadFailed();

    /**
     * 加载空视图
     */
    public abstract void onClickLoadEmpty();
}
