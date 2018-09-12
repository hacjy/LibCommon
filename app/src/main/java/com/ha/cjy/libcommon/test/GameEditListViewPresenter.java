package com.ha.cjy.libcommon.test;

import android.util.Log;


import com.ha.cjy.common.ui.dialog.LoadingDialog;
import com.ha.cjy.common.ui.stateview.LoadViewResultHelper;
import com.ha.cjy.common.ui.widget.list.IAdapterHelp;
import com.ha.cjy.common.ui.widget.list.IHttpPresenter;
import com.ha.cjy.common.ui.widget.list.IRecyclerLoadView;
import com.ha.cjy.common.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 游戏列表p层
 * Created by cjy on 2018/7/17.
 */

public class GameEditListViewPresenter implements IHttpPresenter {
    /**
     * 列表对象
     */
    private IRecyclerLoadView mRecyclerLoadView;
    /**
     * 是否正在刷新
     */
    private boolean refreshing;
    /**
     * 数据
     */
    private List<String> list = new ArrayList<>();

    private int pageNum = 0;

    private int type = 0;

    public GameEditListViewPresenter(IRecyclerLoadView recyclerLoadView) {
        this.mRecyclerLoadView = recyclerLoadView;
    }

    public void setType(int type){
        this.type = type;
    }

    @Override
    public void load() {
    }

    @Override
    public void refreshLoad() {
        if (refreshing)
            return;
        refreshing = true;
        requestData(0);
    }

    @Override
    public void initRequestData() {
        if (refreshing)
            return;
        refreshing = true;
        LoadingDialog.showDialog(((GameEditListView)mRecyclerLoadView).getContext());
        Log.e("requestData,type","执行了,"+type);
        requestData(0);
    }

    public void onItemClick(int position){
        ToastUtil.showToast(((GameEditListView)mRecyclerLoadView).getContext(),"点击了position="+position);
    }

    private void requestData(final int page){
        ((GameEditListView) mRecyclerLoadView).postDelayed(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.dismissDialog();
                refreshing = false;
                List<String> tempList = null;
                IAdapterHelp adapterHelp = null;
                try {
                    adapterHelp = mRecyclerLoadView.getAdapter();
                    tempList = getData(page);
                    if (page == 0) {
                        list.clear();
                    }
                    list.addAll(tempList);
                    adapterHelp.notifyDataSetChanged(list);
                }catch (Exception e){
                    LoadingDialog.dismissDialog();
                    e.printStackTrace();
                }finally {
                    LoadViewResultHelper.loadIsEmpty(list,mRecyclerLoadView.getLoadViewResultState());
                }
            }
        },1000);
    }

    /**
     * 获取数据
     * @param page
     * @return
     */
    private List<String> getData(int page){
        int count = 20;
        String name = "仙剑3";
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < count; i++){
            datas.add(name+i);
        }
        return datas;
    }
}
