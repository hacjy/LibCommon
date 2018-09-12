package com.ha.cjy.libcommon.test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.ha.cjy.common.ui.widget.list.SingleChooseRecyclerViewAdapter;
import com.ha.cjy.libcommon.R;

import java.util.List;

/**
 * 可编辑的单选(SingleChooseRecyclerViewAdapter)/多选(MultipleChooseRecyclerViewAdapter)游戏列表适配器
 * Created by cjy on 2018/7/17.
 */

public class GameEditListViewAdapter extends SingleChooseRecyclerViewAdapter<String> {

    public GameEditListViewAdapter(Context context) {
        super(context);
    }

    public GameEditListViewAdapter(Context context, List<String> datats) {
        super(context, datats);
    }

    @Override
    public View createView(ViewGroup arg0, int arg1) {
        //如果root为null，会造成item宽度不能铺满屏幕
        return LayoutInflater.from(mContext).inflate(R.layout.item_game_listview,arg0, false);
    }

    @Override
    public RecyclerView.ViewHolder initViewHolder(View view, int arg1) {
        GameViewHold viewHold = new GameViewHold(view);
        return viewHold;
    }

    @Override
    public void bindData(RecyclerView.ViewHolder v, int position, List<String> datas) {
        GameViewHold viewHold = (GameViewHold) v;
        viewHold.position = position;
        String name = mData.get(position);
        viewHold.tvName.setText("游戏"+(position+1)+"、"+name);
    }

    @Override
    public View getClickView(RecyclerView.ViewHolder v) {
        GameViewHold viewHold = (GameViewHold) v;
        return viewHold.itemView;
    }

    @Override
    public void setHidden(RecyclerView.ViewHolder v,boolean isHidden) {
        GameViewHold viewHold = (GameViewHold) v;
        if (isHidden){
            viewHold.ivCheck.setVisibility(View.GONE);
        }else{
            viewHold.ivCheck.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 单选列表需要实现的方法
     * @param v
     * @param selectedPosition
     */
    @Override
    public void setSelected(RecyclerView.ViewHolder v, int selectedPosition) {
        GameViewHold viewHold = (GameViewHold) v;
        if (selectedPosition == -1){
            viewHold.ivCheck.setImageResource(R.drawable.ic_chk_normal);
        }else{
            if (viewHold.position == selectedPosition){
                viewHold.ivCheck.setImageResource(R.drawable.ic_chk_checked);
            }else{
                viewHold.ivCheck.setImageResource(R.drawable.ic_chk_normal);
            }
        }

    }

//    /**
//     * 多选列表需要实现的方法
//     * @param v
//     * @param isSelected
//     */
//    @Override
//    public void setSelected(RecyclerView.ViewHolder v, boolean isSelected) {
//        GameViewHold viewHold = (GameViewHold) v;
//        if (isSelected){
//            viewHold.ivCheck.setImageResource(R.drawable.ic_chk_checked);
//        }else{
//            viewHold.ivCheck.setImageResource(R.drawable.ic_chk_normal);
//        }
//    }

    class GameViewHold extends RecyclerView.ViewHolder{
        int position;
        TextView tvName;
        ImageView ivCheck;
        View itemView;


        public GameViewHold(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvName = itemView.findViewById(R.id.tv_name);
            ivCheck = itemView.findViewById(R.id.iv_check);
        }
    }
}
