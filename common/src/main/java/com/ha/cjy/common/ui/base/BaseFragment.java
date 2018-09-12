package com.ha.cjy.common.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ha.cjy.common.ui.stateview.LoadStateViewHelper;
import com.ha.cjy.common.ui.stateview.LoadStateViewResultHelper;
import com.ha.cjy.common.ui.stateview.LocalStateViewFactory;

/**
 * fragment基类
 * Created by cjy on 2018/7/18.
 */

public abstract class BaseFragment extends Fragment {
    /**
     * 根视图
     */
    public View mRootView;
    /**
     * 上下文环境
     */
    public Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getFragmentLayoutId(),null);
        getBundleData(getArguments());
        return mRootView;
    }

    private void init(){
        initView();
        initData();
        initListener();
    }

    /**
     * 查找控件
     * @param resId
     * @param <T>
     * @return
     */
    protected <T extends View> T findViewById(int resId) {
        return (T) mRootView.findViewById(resId);
    }

    /**
     * 获取布局资源id
     * @return
     */
    public abstract int getFragmentLayoutId();

    /**
     * 获取传进来的数据
     */
    public abstract void getBundleData(Bundle data);

    /**
     * 初始化视图
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 初始化监听
     */
    public abstract void initListener();

    /**
     * 初始化默认的状态视图帮助类
     */
    public LoadStateViewResultHelper getLocalLoadHelper(){
        LoadStateViewHelper stateViewHelper = new LoadStateViewHelper(getActivity(), getContentView(), null, null, new View.OnClickListener() {
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
     * 获取内容视图
     * @return
     */
    public abstract View getContentView();

    /**
     * 加载失败
     */
    public abstract void onClickLoadFailed();

    /**
     * 加载空视图
     */
    public abstract void onClickLoadEmpty();
}
