package com.ha.cjy.common.ui.widget.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ha.cjy.common.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * 单选列表通用适配器
 * Created by cjy on 2018/8/1.
 */

public abstract class SingleChooseRecyclerViewAdapter<T> extends CommonRecyclerViewAdapter<T>  {
    /**
     * 是否处于编辑状态
     */
    private boolean mIsEdit;
    /**
     * 选中的数据集合
     */
    private List<T> mSelectedList =  new ArrayList<>();
    /**
     * 上一次选中的数据项的位置
     */
    private int mPrePosition = -1;
    /**
     * 选中的数据项的位置
     */
    private int mSelectedPosition = -1;

    public SingleChooseRecyclerViewAdapter(Context context) {
        super(context);
    }

    public SingleChooseRecyclerViewAdapter(Context context, List<T> datats) {
        super(context, datats);
    }

    public void setIsEdit(boolean mIsEdit) {
        this.mIsEdit = mIsEdit;
    }

    @Override
    public View onCreateView(ViewGroup arg0, int arg1) {
        View view = createView(arg0,arg1);
        return view;
    }

    @Override
    public RecyclerView.ViewHolder createViewHolder(View view, int arg1) {
        return initViewHolder(view,arg1);
    }

    @Override
    public void onMyBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position, List<T> datas) {
        final T data = mData.get(position);
        bindData(viewHolder, position, datas);
        if (mIsEdit){
            //处于编辑状态
            setHidden(viewHolder,false);
            getClickView(viewHolder).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        //处于编辑状态
                        if (mIsEdit == true ) {
                           if (mSelectedPosition == -1){
                               mSelectedPosition = position;
                               mSelectedList.add(data);
                               mPrePosition = position;
                           }else{
                               if (mSelectedPosition == position){
                                   mSelectedPosition = -1;
                                   mPrePosition = position;
                                   mSelectedList.clear();
                               }else{
                                   mPrePosition = mSelectedPosition;
                                   mSelectedPosition = position;
                                   mSelectedList.clear();
                                   mSelectedList.add(data);
                               }
                           }
                           //局部刷新：刷新上一次选中的和当前选中的，其他不更新
                           notifyItemChanged(mPrePosition);
                           notifyItemChanged(mSelectedPosition);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }else{
            setHidden(viewHolder,true);
        }
        setSelected(viewHolder,mSelectedPosition);
    }

    /**
     * 重置数据
     */
    public void resetData(){
        mSelectedList.clear();
        mSelectedPosition = -1;
    }

    /**
     * 移除数据
     */
    public void removeData(T data) {
        if (!Util.isCollectionEmpty(mData)) {
            mData.remove(data);
        }
        notifyDataSetChanged();
    }

    /**
     * 移除数据
     */
    public void removeData() {
        List<T> selectedList = getSelectedData();
        if (!Util.isCollectionEmpty(mData)) {
            mData.removeAll(selectedList);
        }
        notifyDataSetChanged();
    }

    /**
     * 获取已选择的列表
     *
     * @return
     */
    public List<T> getSelectedData() {
        return mSelectedList;
    }

    /**
     * 获取已选择的数量
     *
     * @return
     */
    public int getSelectedCount() {
        return getSelectedData().size();
    }

    /**
     * 创建view
     *
     * @return
     */
    public abstract View createView(ViewGroup arg0, int arg1);

    /**
     * 创建一个viewholder
     *
     * @param view
     * @return
     */
    public abstract RecyclerView.ViewHolder initViewHolder(View view, int arg1);

    /**
     * 绑定数据
     *
     * @param v
     * @param position
     * @param datas
     */
    public abstract void bindData(RecyclerView.ViewHolder v, int position, List<T> datas);

    /**
     * 获取要设置点击监听的View
     * @param v
     * @return
     */
    public abstract View getClickView(RecyclerView.ViewHolder v);

    /**
     * 设置是否显示
     * @param isHidden
     */
    public abstract void setHidden(RecyclerView.ViewHolder v,boolean isHidden);

    /**
     * 设置选中未选中状态
     * @param selectedPosition
     */
    public abstract void setSelected(RecyclerView.ViewHolder v,int selectedPosition);
}
