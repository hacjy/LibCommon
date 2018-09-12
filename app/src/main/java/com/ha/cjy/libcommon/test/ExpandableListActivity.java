package com.ha.cjy.libcommon.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ha.cjy.common.ui.base.BaseToolbarActivity;
import com.ha.cjy.common.ui.widget.expandablelist.ExpandableListItemClickListener;
import com.ha.cjy.common.ui.widget.expandablelist.Item;
import com.ha.cjy.common.ui.widget.expandablelist.Section;
import com.ha.cjy.common.ui.widget.expandablelist.SectionedExpandableLayoutHelper;
import com.ha.cjy.common.util.ToolbarFactory;
import com.ha.cjy.libcommon.R;

import java.util.ArrayList;

/**
 * 二级列表页面
 * Created by cjy on 2018/7/31.
 */

public class ExpandableListActivity extends BaseToolbarActivity implements ExpandableListItemClickListener {
    public final static String BUNDLE_KEY = "bundle_key";
    private RecyclerView mRecyclerView;
    //二级列表帮助类
    private SectionedExpandableLayoutHelper sectionedExpandableLayoutHelper;
    /**
     * 列数 1 表示线性布局 其他的表示网格布局（几列）
     */
    private int mSpanCount = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandablelist);
    }

    @Override
    public void initView() {
        mSpanCount = getIntent().getIntExtra(BUNDLE_KEY,mSpanCount);

        mRecyclerView = findViewById(R.id.recycler_view);
        //设置二级列表适配器
        sectionedExpandableLayoutHelper = new SectionedExpandableLayoutHelper(this,
                mRecyclerView, this, mSpanCount);
    }


    @Override
    public void initData() {
        //random data
        ArrayList<Item> arrayList = new ArrayList<>();
        arrayList.add(new Item("iPhone", 0));
        arrayList.add(new Item("iPad", 1));
        arrayList.add(new Item("iPod", 2));
        arrayList.add(new Item("iMac", 3));
        sectionedExpandableLayoutHelper.addSection("Apple Products",true, arrayList);

        arrayList = new ArrayList<>();
        arrayList.add(new Item("LG", 0));
        arrayList.add(new Item("Apple", 1));
        arrayList.add(new Item("Samsung", 2));
        arrayList.add(new Item("Motorola", 3));
        arrayList.add(new Item("Sony", 4));
        arrayList.add(new Item("Nokia", 5));
        sectionedExpandableLayoutHelper.addSection("Companies",false, arrayList);

        arrayList = new ArrayList<>();
        arrayList.add(new Item("Chocolate", 0));
        arrayList.add(new Item("Strawberry", 1));
        arrayList.add(new Item("Vanilla", 2));
        arrayList.add(new Item("Butterscotch", 3));
        arrayList.add(new Item("Grape", 4));
        sectionedExpandableLayoutHelper.addSection("Ice cream",false, arrayList);


        sectionedExpandableLayoutHelper.notifyDataSetChanged();
        //添加item到指定的Section
        sectionedExpandableLayoutHelper.addItem("Ice cream", new Item("Tutti frutti",5));
        //更新数据
        sectionedExpandableLayoutHelper.notifyDataSetChanged();
    }

    @Override
    public void initListener() {

    }

    @Override
    public View getContentView() {
        return null;
    }

    @Override
    protected void initToolbar() {
        ToolbarFactory.initLeftBackToolbar(ExpandableListActivity.this,"","二级列表");
    }

    @Override
    protected int getToolbarLayout() {
        return R.layout.default_toolbar_layout;
    }

    /**
     * 子项点击事件
     * @param item
     */
    @Override
    public void itemClicked(Item item) {
        Toast.makeText(this, "Item: " + item.getName() + " clicked", Toast.LENGTH_SHORT).show();
    }

    /**
     * 头部Group点击事件
     * @param section
     */
    @Override
    public void itemClicked(Section section) {

    }
}
