package com.ha.cjy.libcommon.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ha.cjy.common.ui.base.BaseToolbarActivity;
import com.ha.cjy.common.util.ToolbarFactory;
import com.ha.cjy.libcommon.R;


/**
 * 测试：可编辑的列表页面
 * Created by cjy on 2018/7/17.
 */

public class GameEditListViewActivity extends BaseToolbarActivity {
    /**
     * 列表容器
     */
    private FrameLayout mLayoutListViewContainer;
    /**
     * 列表
     */
    private GameEditListView mGameListView;
    /**
     * 是否处于编辑状态
     */
    private boolean mIsEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtity_game_listview);
    }

    @Override
    public void initView() {
        mLayoutListViewContainer = findViewById(R.id.layout_listview_container);
        mGameListView = new GameEditListView(GameEditListViewActivity.this);
        mLayoutListViewContainer.addView(mGameListView);
    }

    @Override
    public void initData() {
        mGameListView.requestionData();
    }


    @Override
    public void initListener() {

    }


    @Override
    public View getContentView() {
        return mLayoutListViewContainer;
    }

    @Override
    protected void initToolbar() {
        ToolbarFactory.initLeftBackAndRightOnlyTextToolbar(GameEditListViewActivity.this, "", "可编辑的列表", "编辑", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsEdit) {
                    setEdit(true);
                }else{
                    complete();
                }
                setRightText();
            }
        });
        try {
            //设置右边文本颜色
            TextView rightView = this.getToolbar().findViewById(R.id.titleRightText);
            if (rightView != null) {
                rightView.setTextColor(getResources().getColor(R.color.white));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 取消
     */
    private void complete(){
        setEdit(false);
        setRightText();
    }

    /**
     * 设置编辑状态
     * @param isEdit
     */
    public void setEdit(boolean isEdit){
        mIsEdit = isEdit;
        mGameListView.setEdit(isEdit);
    }

    /**
     * 设置右边文本
     */
    public void setRightText(){
        String text = getRightText();
        try {
            TextView rightView = this.getToolbar().findViewById(R.id.titleRightText);
            if (rightView != null) {
                rightView.setText(text);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取右边文本
     */
    private String getRightText(){
        String rightText = "编辑";
        if (mIsEdit){
            rightText = "取消";
        }
        return rightText;
    }

    @Override
    protected int getToolbarLayout() {
        return R.layout.default_toolbar_layout;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsEdit) {
                complete();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
