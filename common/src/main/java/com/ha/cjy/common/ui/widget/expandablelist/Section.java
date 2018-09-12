package com.ha.cjy.common.ui.widget.expandablelist;

import java.io.Serializable;

/**
 * 头部Group实体类
 */
public class Section implements Serializable {
    /**
     * 唯一标识
     */
    public int id = 0;
    public String name;
    /**
     * 是否展开
     */
    public boolean isExpanded;

    public Section(int id,String name) {
        this.id = id;
        this.name = name;
        isExpanded = true;
    }

    public Section(String name) {
        this.name = name;
        isExpanded = true;
    }

    public String getName() {
        return name;
    }
}
