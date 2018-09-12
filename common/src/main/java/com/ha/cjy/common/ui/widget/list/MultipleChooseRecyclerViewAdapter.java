package com.ha.cjy.common.ui.widget.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ha.cjy.common.util.Util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 多选列表通用适配器
 * Created by cjy on 2018/8/1.
 */

public abstract class MultipleChooseRecyclerViewAdapter<T> extends CommonRecyclerViewAdapter<T>  {
    /**
     * 是否处于编辑状态
     */
    private boolean mIsEdit;
    /**
     * 选中的数据集合{key：object，value：boolean}
     */
    private Map<T,Boolean> mSelectedMap =  new HashMap<>();

    public MultipleChooseRecyclerViewAdapter(Context context) {
        super(context);
    }

    public MultipleChooseRecyclerViewAdapter(Context context, List<T> datats) {
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
                            if (mSelectedMap.size() == 0){
                                mSelectedMap.put(data,true);
                            }else {
                                if (mSelectedMap.get(data) != null && mSelectedMap.get(data).booleanValue()) {
                                    mSelectedMap.put(data, false);
                                } else {
                                    mSelectedMap.put(data, true);
                                }
                            }
                           notifyDataSetChanged();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }else{
            setHidden(viewHolder,true);
        }
       if (mSelectedMap.get(data) != null && mSelectedMap.get(data).booleanValue()){
            setSelected(viewHolder,true);
       }else{
           setSelected(viewHolder,false);
       }
    }

    /**
     * 重置数据
     */
    public void resetData(){
        mSelectedMap.clear();
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
        Map<T,Boolean> selectedList = getSelectedData();
        if (!Util.isCollectionEmpty(mData)) {
            mData.removeAll( Arrays.asList(selectedList));
        }
        notifyDataSetChanged();
    }

    /**
     * 获取已选择的列表
     *
     * @return
     */
    public Map<T,Boolean> getSelectedData() {
        return mSelectedMap;
    }

    /**
     * 获取已选择的列表
     *
     * @return
     */
    public List<T> getSelectedListData() {
        Map<T,Boolean> selectedList = getSelectedData();
        return (List<T>) Arrays.asList(selectedList);
    }

    /**
     * 获取已选择的数量
     *
     * @return
     */
    public int getSelectedCount() {
        int size = 0;
        for (Map.Entry<T, Boolean> entry : getSelectedData().entrySet()) {
            if (entry.getValue()){
                size++;
            }
        }
        return size;
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
     * @param isSelected
     */
    public abstract void setSelected(RecyclerView.ViewHolder v,boolean isSelected);
}
