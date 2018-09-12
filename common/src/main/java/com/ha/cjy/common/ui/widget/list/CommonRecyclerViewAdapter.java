package com.ha.cjy.common.ui.widget.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用的列表适配器
 * Created by cjy on 2018/7/17.
 */

public abstract class CommonRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IAdapterHelp<T> {
    /**
     * 上下文
     */
    protected Context mContext;
    /**
     * 数据
     */
    protected List<T> mData;

    /**
     * 单击
     */
    private IOnItemCilick mItemClick;
    /**
     * 长按
     */
    private IOnItemLongCilick mItemLongCilick;

    public CommonRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    public CommonRecyclerViewAdapter(Context context, List<T> datats) {
        mData = datats;
        mContext = context;
    }

    public CommonRecyclerViewAdapter(Context context, List<T> datats, IOnItemCilick itemClick, IOnItemLongCilick itemLongCilick) {
        mData = datats;
        mContext = context;
        mItemClick = itemClick;
        mItemLongCilick = itemLongCilick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
        View view = onCreateView(arg0, arg1);
        RecyclerView.ViewHolder baseViewHolder = createViewHolder(view, arg1);
        baseViewHolder.itemView.setOnClickListener(mClickListener);
        baseViewHolder.itemView.setOnLongClickListener(mLongClickListener);
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder arg0, int arg1) {
        arg0.itemView.setTag(arg1);
        onMyBindViewHolder(arg0, arg1, mData);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<T> list) {
        if (list != null && list.size() > 0) {
            mData = list;
        }
    }

    public void clear() {
        mData.clear();
    }

    public int getCount() {
        return (mData == null || mData.isEmpty()) ? 0 : mData.size();
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public List<T> getData() {
        return mData;
    }

    public void notifyDataSetChanged(List<T> data) {
        if (data == null) {
            data = new ArrayList<T>();
        }
        this.mData = data;
        this.notifyDataSetChanged();
    }

    public void removeData(int position) {
        if (mData != null && !mData.isEmpty()) {
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public void addDataNotifyDataSetChanged(T t) {
        if (mData == null) {
            mData = new ArrayList<T>();
        }
        if (t != null) {
            mData.add(t);
            notifyDataSetChanged();
        }

    }

    @Override
    public void addBatchDataNotifyDataSetChanged(List<T> ts) {

        if (mData == null) {
            mData = new ArrayList<T>();
        }
        if (ts != null && !ts.isEmpty()) {
            mData.addAll(ts);
            notifyDataSetChanged();
        }

    }

    @Override
    public void addDataNotifyDataSetChanged(int position, T t) {
        if (mData == null) {
            mData = new ArrayList<T>();
        }
        if (t != null) {
            mData.add(position, t);
            notifyItemInserted(position);
        }
    }

    /**
     * 创建view
     *
     * @return
     */
    public abstract View onCreateView(ViewGroup arg0, int arg1);

    /**
     * 创建一个viewholder
     *
     * @param view
     * @return
     */
    public abstract RecyclerView.ViewHolder createViewHolder(View view, int arg1);

    /**
     * 绑定数据
     *
     * @param v
     * @param position
     * @param datas
     */
    public abstract void onMyBindViewHolder(RecyclerView.ViewHolder v, int position, List<T> datas);


    /**
     * RecyclerView点击
     *
     * @author linbinghuang
     */
    public interface IOnItemCilick {
        void onItemClick(View v, int position);
    }

    /**
     * RecyclerView长按
     *
     * @author linbinghuang
     */
    public interface IOnItemLongCilick {
        void onItemLongClick(View v, int position);
    }


    /**
     * 点击事件
     */
    private View.OnClickListener mClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mItemClick != null) {
                int position = (Integer) v.getTag();
                mItemClick.onItemClick(v, position);
            }
        }
    };

    /**
     * 长按事件
     */
    private View.OnLongClickListener mLongClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {
            if (mItemLongCilick != null) {
                int position = (Integer) v.getTag();
                mItemLongCilick.onItemLongClick(v, position);
            }
            return false;
        }
    };

    /**
     * 设置点击
     *
     * @param mItemClick
     */
    public void setOnItemClick(IOnItemCilick mItemClick) {
        this.mItemClick = mItemClick;
    }

    /**
     * 设置长按
     *
     * @param mItemLongCilick
     */
    public void setOnItemLongCilick(IOnItemLongCilick mItemLongCilick) {
        this.mItemLongCilick = mItemLongCilick;
    }
}
