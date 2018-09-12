package com.ha.cjy.common.ui.widget.expandablelist;

import java.io.Serializable;

/**
 * 头部Group实体类
 */
public class Section implements Serializable {

    private final String name;
    /**
     * 是否展开
     */
    public boolean isExpanded;

    public Section(String name) {
        this.name = name;
        isExpanded = true;
    }

    public String getName() {
        return name;
    }
}
