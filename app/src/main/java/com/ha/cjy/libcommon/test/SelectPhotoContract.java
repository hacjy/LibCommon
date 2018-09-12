package com.ha.cjy.libcommon.test;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

/**
 * 选择图片的接口协议
 * Created by cjy on 2018/9/12.
 */

public interface SelectPhotoContract {
    interface View{
        android.view.View.OnClickListener getClickListener();
        Activity getActivity();
        android.view.View getLayoutView();
        ImageView getPhotoView();
    }

    interface Presenter{
        void onActivityResult(int requestCode, int resultCode, Intent data);
        void showSelectPopupWindow();
        void cameraClick();
        void photoClick();
    }
}
