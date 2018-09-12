package com.ha.cjy.common.ui.widget.expandablelist;

/**
 * 头部Group展开收起状态的监听
 */
public interface SectionStateChangeListener {
    void onSectionStateChanged(Section section, boolean isOpen);
}