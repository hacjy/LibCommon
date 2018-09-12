package com.ha.cjy.libcommon.test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.ha.cjy.common.ui.widget.list.CommonRecyclerViewAdapter;
import com.ha.cjy.libcommon.R;

import java.util.List;

/**
 * 游戏列表适配器
 * Created by cjy on 2018/7/17.
 */

public class GameListViewAdapter extends CommonRecyclerViewAdapter<String> {

    public GameListViewAdapter(Context context) {
        super(context);
    }

    public GameListViewAdapter(Context context, List<String> datats) {
        super(context, datats);
    }

    public GameListViewAdapter(Context context, List<String> datats, IOnItemCilick itemClick, IOnItemLongCilick itemLongCilick) {
        super(context, datats, itemClick, itemLongCilick);
    }

    @Override
    public View onCreateView(ViewGroup arg0, int arg1) {
        //如果root为null，会造成item宽度不能铺满屏幕
        return LayoutInflater.from(mContext).inflate(R.layout.item_game_listview,arg0, false);
    }

    @Override
    public RecyclerView.ViewHolder createViewHolder(View view, int arg1) {
        GameViewHold viewHold = new GameViewHold(view);
        return viewHold;
    }

    @Override
    public void onMyBindViewHolder(RecyclerView.ViewHolder v, int position, List<String> datas) {
        GameViewHold viewHold = (GameViewHold) v;
        String name = mData.get(position);
        viewHold.tvName.setText("游戏"+(position+1)+"、"+name);
    }

    class GameViewHold extends RecyclerView.ViewHolder{
        TextView tvName;


        public GameViewHold(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
