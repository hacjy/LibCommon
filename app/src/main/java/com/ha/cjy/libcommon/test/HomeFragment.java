package com.ha.cjy.libcommon.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ha.cjy.common.ui.base.BaseFragment;
import com.ha.cjy.common.ui.qrcode.QrCodeActivity;
import com.ha.cjy.common.ui.stateview.LoadViewResultHelper;
import com.ha.cjy.common.ui.update.UpdateVersionHelper;
import com.ha.cjy.common.ui.update.UpdateVersionResultInfo;
import com.ha.cjy.common.ui.update.UpdateView;
import com.ha.cjy.common.util.ToastUtil;
import com.ha.cjy.common.util.Util;
import com.ha.cjy.libcommon.R;


/**
 * 测试 首页
 * Created by hacjy on 2018/7/18.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener  {
    public final static String BUNDLE_DATA = "position";
    private int mPosition;

    private LinearLayout mLayoutContent;
    private Button mBtnLoadFailedView;
    private Button mBtnLoadEmptyView;
    private Button mBtnListView;
    private Button mBtnViewPager;
    private Button mBtnQrcode;
    private Button mBtnDownload;
    private Button mBtnOpenExpandableList;
    private Button mBtnSelectPhoto;
    private Button mBtnFastListView;
    private Button mBtnEditListView;
    private TextView mTvText;
    private RadioGroup mRgSelect;
    /**
     * 是否已经初始化了
     */
    private boolean isAlreadyInit = false;
    /**
     * 默认1 表示选择线性布局
     */
    private int spanCount = 1;

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void getBundleData(Bundle data) {
        mPosition = data.getInt(BUNDLE_DATA);
    }

    @Override
    public void initView() {
        mLayoutContent = findViewById(R.id.layout_content);
        mBtnLoadFailedView = findViewById(R.id.btn_load_failed_view);
        mBtnLoadEmptyView = findViewById(R.id.btn_load_empty_view);
        mBtnListView = findViewById(R.id.btn_list_view);
        mBtnViewPager = findViewById(R.id.btn_viewpager);
        mBtnQrcode = findViewById(R.id.btn_qrcode);
        mTvText = findViewById(R.id.tv_text);
        mBtnDownload = findViewById(R.id.btn_download);
        mBtnOpenExpandableList = findViewById(R.id.btn_open_expandablelist);
        mRgSelect = findViewById(R.id.rg_select);
        mBtnSelectPhoto = findViewById(R.id.btn_select_photo);
        mBtnEditListView = findViewById(R.id.btn_edit_listview);
        mBtnFastListView = findViewById(R.id.btn_fast_listview);
    }

    @Override
    public void initData() {
        mTvText.setText("第["+mPosition+"]个页面");
        if (!isAlreadyInit){
            isAlreadyInit = true;
            ToastUtil.showToast(getActivity(),"第"+mPosition+"个页面初始化数据了");
        }
    }

    @Override
    public void initListener() {
        mBtnLoadFailedView.setOnClickListener(this);
        mBtnLoadEmptyView.setOnClickListener(this);
        mBtnListView.setOnClickListener(this);
        mBtnViewPager.setOnClickListener(this);
        mBtnQrcode.setOnClickListener(this);
        mBtnDownload.setOnClickListener(this);
        mBtnOpenExpandableList.setOnClickListener(this);
        mBtnSelectPhoto.setOnClickListener(this);
        mBtnEditListView.setOnClickListener(this);
        mBtnFastListView.setOnClickListener(this);

        mRgSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_linearlayout){
                    spanCount =  1;
                }else{
                    spanCount = 3;
                }
            }
        });
    }

    @Override
    public View getContentView() {
        return mLayoutContent;
    }

    @Override
    public void onClickLoadFailed() {
        ToastUtil.showToast(getActivity(),"失败后点击刷新");
    }

    @Override
    public void onClickLoadEmpty() {
        ToastUtil.showToast(getActivity(),"空视图点击刷新");
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_load_failed_view) {
            LoadViewResultHelper.loadIsFailed(getLocalLoadHelper());
        } else if (i == R.id.btn_load_empty_view) {
            LoadViewResultHelper.loadIsEmpty(null, getLocalLoadHelper());
        } else if (i == R.id.btn_list_view) {
            Intent intent = new Intent(getActivity(), GameListViewActivity.class);
            startActivity(intent);
        } else if (i == R.id.btn_viewpager) {
            Intent intent = new Intent(getActivity(), GameViewPagerViewActivity.class);
            startActivity(intent);
        } else if (i == R.id.btn_qrcode) {
            Intent intent = new Intent(getActivity(), QrCodeActivity.class);
            startActivity(intent);
        } else if (i == R.id.btn_download) {
            testUpdate();
        } else if (i == R.id.btn_open_expandablelist) {
            Intent intent = new Intent(getActivity(), ExpandableListActivity.class);
            intent.putExtra(ExpandableListActivity.BUNDLE_KEY, spanCount);
            startActivity(intent);
        } else if (i == R.id.btn_select_photo) {
            Intent intent = new Intent(getActivity(), SelectPhotoActivity.class);
            startActivity(intent);
        } else if (i == R.id.btn_edit_listview) {
            Intent intent = new Intent(getActivity(), GameEditListViewActivity.class);
            startActivity(intent);
        }  else if (i == R.id.btn_fast_listview) {
            Intent intent = new Intent(getActivity(), FastListViewActivity.class);
            startActivity(intent);
         }
    }

    private void testUpdate(){
        if (new UpdateVersionHelper(getActivity()).isUpdateVersion(99)) {
            UpdateVersionResultInfo info = new UpdateVersionResultInfo();
            info.AppName = "有妖气";
            info.InnerVersion = 99+"";
            info.AppUpdateTime = 232564;
            info.PackageName = "com.you.yq";
            info.PackageSize = 123456;
            info.VersionNumber = 99+"";
            info.UpdateTitle = "更新内容";
            info.UpdateType = 0;
            info.UpdateUrl = "http://down.s.qq.com/download/1106501964/apk/10018365_com.tencent.tmgp.pubgm.apk";
            info.UpdateContent = "1、修复bug;\r\n2、完善逻辑;\r\n3、优化性能";
            info.UpdateIco = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532355248104&di=38b2dd9c622a82b816592e2bfc1e599e&imgtype=0&src=http%3A%2F%2Fs13.sinaimg.cn%2Fmw690%2F002s6ERFgy6JIGGtrvmfc%26690";
            UpdateView.showDialog(getActivity(), info);
        } else {
            ToastUtil.showToast(getActivity(), "当前已是最新版本");
        }
    }
}
