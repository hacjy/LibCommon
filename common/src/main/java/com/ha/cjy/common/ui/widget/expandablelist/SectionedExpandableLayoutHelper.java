package com.ha.cjy.common.ui.widget.expandablelist;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * RecyclerView二级列表，支持展开收起，支持网格布局和线性布局
 */
public class SectionedExpandableLayoutHelper implements SectionStateChangeListener {

    /**
     * Group和Item对应的数据列表集合
     */
    private LinkedHashMap<Section, ArrayList<Item>> mSectionDataMap = new LinkedHashMap<Section, ArrayList<Item>>();
    /**
     * 数据：Group与Item数据混合一起的列表
     */
    private ArrayList<Object> mDataArrayList = new ArrayList<Object>();
    /**
     * Group数据集合
     */
    private HashMap<String, Section> mSectionMap = new HashMap<String, Section>();

    /**
     * 适配器
     */
    private SectionedExpandableGridAdapter mSectionedExpandableGridAdapter;
    /**
     * 列表
     */
    private RecyclerView mRecyclerView;

    public SectionedExpandableLayoutHelper(Context context, RecyclerView recyclerView, ExpandableListItemClickListener itemClickListener,
                                           int gridSpanCount) {

        //setting the recycler view
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, gridSpanCount);
        recyclerView.setLayoutManager(gridLayoutManager);
        mSectionedExpandableGridAdapter = new SectionedExpandableGridAdapter(context, mDataArrayList,
                gridLayoutManager, itemClickListener, this);
        recyclerView.setAdapter(mSectionedExpandableGridAdapter);

        mRecyclerView = recyclerView;
    }

    /**
     * 设置是否当前只展开一个Group
     * @param isShowSingleGroup
     */
    public void setShowSingleGroup(boolean isShowSingleGroup){
        mSectionedExpandableGridAdapter.setShowSingleGroup(isShowSingleGroup);
    }

    public void notifyDataSetChanged() {
        generateDataList();
        mSectionedExpandableGridAdapter.notifyDataSetChanged();
    }

    public void addSection(String section, ArrayList<Item> items) {
        Section newSection;
        mSectionMap.put(section, (newSection = new Section(section)));
        mSectionDataMap.put(newSection, items);
    }

    public void addSection(int flagId,String section, boolean isExpand,ArrayList<Item> items) {
        Section newSection = new Section(flagId,section);
        newSection.isExpanded = isExpand;
        mSectionMap.put(section,newSection );
        mSectionDataMap.put(newSection, items);
    }

    public void addSection(String section, boolean isExpand,ArrayList<Item> items) {
        Section newSection = new Section(section);
        newSection.isExpanded = isExpand;
        mSectionMap.put(section,newSection );
        mSectionDataMap.put(newSection, items);
    }

    public void addItem(String section, Item item) {
        mSectionDataMap.get(mSectionMap.get(section)).add(item);
    }

    public void removeItem(String section, Item item) {
        mSectionDataMap.get(mSectionMap.get(section)).remove(item);
    }

    public void removeSection(String section) {
        mSectionDataMap.remove(mSectionMap.get(section));
        mSectionMap.remove(section);
    }

    private void generateDataList () {
        mDataArrayList.clear();
        for (Map.Entry<Section, ArrayList<Item>> entry : mSectionDataMap.entrySet()) {
            Section key = entry.getKey();
            mDataArrayList.add(key);

            //是否显示子项，是通过是否添加子项数据来控制的
            if (key.isExpanded)
                mDataArrayList.addAll(entry.getValue());
        }
    }

    @Override
    public void onSectionStateChanged(Section section, boolean isOpen) {
        if (!mRecyclerView.isComputingLayout()) {
            section.isExpanded = isOpen;
            notifyDataSetChanged();
        }
    }
}
