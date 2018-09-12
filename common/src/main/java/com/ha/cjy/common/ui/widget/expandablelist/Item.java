package com.ha.cjy.common.ui.widget.expandablelist;

import java.io.Serializable;

/**
 * 子项item
 */
public class Item implements Serializable{

    private final String name;
    private final int id;

    public Item(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
