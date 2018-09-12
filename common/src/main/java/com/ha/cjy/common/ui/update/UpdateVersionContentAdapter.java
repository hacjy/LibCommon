package com.ha.cjy.common.ui.update;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ha.cjy.common.R;
import com.ha.cjy.common.ui.widget.list.CommonRecyclerViewAdapter;

import java.util.List;

/**
 * 检查更新的内容adapter类
 * Created by willy on 2016/12/16.
 */

public class UpdateVersionContentAdapter extends CommonRecyclerViewAdapter {

    public UpdateVersionContentAdapter(Context context, List datats) {
        super(context, datats);
    }

    @Override
    public View onCreateView(ViewGroup arg0, int arg1) {
        return LayoutInflater.from(mContext).inflate(R.layout.update_version_content_item_layout,null);
    }

    @Override
    public void onMyBindViewHolder(RecyclerView.ViewHolder v, int position, List datas) {
        ViewHolder holder = (ViewHolder) v;
        String text = (String) datas.get(position);
        holder.contentText.setText(text);
    }

    @Override
    public RecyclerView.ViewHolder createViewHolder(View view, int arg1) {
        ViewHolder holder = new ViewHolder(view);
        holder.contentText = (TextView) view.findViewById(R.id.contentItemText);
        return holder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView contentText;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
