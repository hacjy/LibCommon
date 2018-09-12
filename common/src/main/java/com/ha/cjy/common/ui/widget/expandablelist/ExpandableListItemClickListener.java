package com.ha.cjy.common.ui.widget.expandablelist;

/**
 * 头部Group和子项item的监听
 */
public interface ExpandableListItemClickListener {
    void itemClicked(Item item);
    void itemClicked(Section section);
}
