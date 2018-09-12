package com.ha.cjy.common.ui.stateview;

import java.util.List;

/**
 * 显示状态结果视图帮助类,开放给外界调用
 * Created by cjy on 2018/7/17.
 */

public class LoadViewResultHelper {

    public static void loadIsFailed(ILoadStateViewResult iLoadResult) {
        iLoadResult.onLoadFailed();
    }

    public static void loadIsEmpty(List data, ILoadStateViewResult iLoadResult) {
        if (data != null){
            if (data.size() > 0){
                iLoadResult.onLoadSuccess();
            }else{
                iLoadResult.onLoadEmpty();
            }
        }else{
            iLoadResult.onLoadEmpty();
        }
    }

}
