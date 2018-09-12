//package com.ha.cjy.commomlib.test;
//
//import android.os.Bundle;
//import android.support.v4.view.ViewPager;
//import android.util.Log;
//import android.view.View;
//import android.widget.LinearLayout;
//
//import com.ha.cjy.commomlib.R;
//import com.ha.cjy.commomlib.ui.widget.DefaultBottomtTabLayout;
//import com.ha.cjy.commomlib.ui.base.BaseActivity;
//import com.ha.cjy.commomlib.ui.base.BaseFragment;
//import com.ha.cjy.commomlib.ui.widget.ViewPagerSlide;
//import com.ha.cjy.commomlib.util.ToastUtil;
//import com.ha.cjy.commomlib.util.viewpager.CommonFragmentViewPagerAdapter;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 测试：首页 方式一ViewPager+Fragment 缺点：没有实现懒加载
// */
//public class MainActivity extends BaseActivity{
//    //ViewPager容器
//    private LinearLayout mLayoutContent;
//    //底部Tab
//    private DefaultBottomtTabLayout mTabLayout;
//
//    //ViewPager
//    private ViewPagerSlide mViewPager;
//    private List<BaseFragment> mFragmentList = new ArrayList<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Log.e("程序savedInstanceState=",savedInstanceState+"");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    public void initDataBeforeView() {
//
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        //恢复页面时，不做保存数据的恢复，这样避免在应用设置页面设置权限（仅当权限被关闭情况）后在返回导致页面异常关闭，从而重新启动页面时，页面显示错误的问题
//    }
//
//    @Override
//    public void initView(){
//        mTabLayout = findViewById(R.id.tabLayout);
//        mLayoutContent = findViewById(R.id.layout_content);
//        mViewPager = findViewById(R.id.viewpager);
//    }
//
//    @Override
//    public void initData() {
//        initPageView();
//
//        mViewPager.setSlide(false);
//        mViewPager.setSmoothScroll(false);
//        mViewPager.setAdapter(new CommonFragmentViewPagerAdapter(getSupportFragmentManager(),mFragmentList));
//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//
//        //默认选中第一个
//        mTabLayout.setSelect(0);
//        mViewPager.setCurrentItem(0);
//    }
//
//    /**
//     * 初始化fragment
//     */
//    private void initPageView(){
//        HomeFragment mFirstFragment = null,mSecondFragment = null,mThirdFragment = null;
//        if (mFirstFragment == null){
//            mFirstFragment = new HomeFragment();
//            Bundle bundle = new Bundle();
//            bundle.putInt(HomeFragment.BUNDLE_DATA,1);
//            mFirstFragment.setArguments(bundle);
//        }
//        if (mSecondFragment == null){
//            mSecondFragment = new HomeFragment();
//            Bundle bundle = new Bundle();
//            bundle.putInt(HomeFragment.BUNDLE_DATA,2);
//            mSecondFragment.setArguments(bundle);
//        }
//        if (mThirdFragment == null){
//            mThirdFragment = new HomeFragment();
//            Bundle bundle = new Bundle();
//            bundle.putInt(HomeFragment.BUNDLE_DATA,3);
//            mThirdFragment.setArguments(bundle);
//        }
//
//        mFragmentList.add(mFirstFragment);
//        mFragmentList.add(mSecondFragment);
//        mFragmentList.add(mThirdFragment);
//    }
//
//    @Override
//    public void initListener(){
//        mTabLayout.setSwitchTabCallback(new DefaultBottomtTabLayout.SwitchTabCallback() {
//            @Override
//            public void switchTab(int position) {
//                transPage(position);
//            }
//        });
//    }
//
//    /**
//     * 切换页面
//     * @param position
//     */
//    private void transPage(int position){
//        mViewPager.setCurrentItem(position);
//    }
//
//
//
//    @Override
//    public View getContentView() {
//        return findViewById(R.id.layout_content);
//    }
//
//    @Override
//    public void onClickLoadFailed() {
//
//    }
//
//    @Override
//    public void onClickLoadEmpty() {
//
//    }
//
//    int backPressCount = 0;
//    /**
//     * 按2次退出应用
//     */
//    @Override
//    public void onBackPressed() {
//        if (backPressCount >= 1) {
//            super.onBackPressed();
//        } else {
//            backPressCount++;
//            ToastUtil.showToast(this, "再按一次退出应用");
//        }
//        mTabLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                backPressCount = 0;
//            }
//        }, 2000);
//    }
//
//}

package com.ha.cjy.libcommon.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.ha.cjy.common.ui.base.BaseActivity;
import com.ha.cjy.common.ui.base.BaseFragment;
import com.ha.cjy.common.ui.update.download.DownloadModel;
import com.ha.cjy.common.ui.widget.DefaultBottomtTabLayout;
import com.ha.cjy.common.util.ToastUtil;
import com.ha.cjy.libcommon.R;


/**
 * 测试：首页 方式2：FragmentManager add、show、hide :问题：在应用权限设置页面关闭权限然后再次进入页面，页面显示错误，暂时未解决。
 */
public class MainActivity extends BaseActivity {
    //底部Tab
    private DefaultBottomtTabLayout mTabLayout;
    //当前Fragment
    private BaseFragment mCurrentFragment;
    //3个Fragment
    private HomeFragment mFirstFragment;
    private HomeFragment mSecondFragment;
    private HomeFragment mThirdFragment;
    //Fragment管理器
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initDataBeforeView() {

    }

    @Override
    public void initView(){
        mTabLayout = findViewById(R.id.tabLayout);
    }

    @Override
    public void initData() {
        //默认选中第一个
        mTabLayout.setSelect(0);

        mFragmentManager = getSupportFragmentManager();
        if (mFirstFragment == null){
            mFirstFragment = new HomeFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(HomeFragment.BUNDLE_DATA,1);
            mFirstFragment.setArguments(bundle);
        }
        mFragmentManager.beginTransaction().add(R.id.layout_content,mFirstFragment,"1").commitAllowingStateLoss();
        mCurrentFragment = mFirstFragment;
    }

    @Override
    public void initListener(){
        mTabLayout.setSwitchTabCallback(new DefaultBottomtTabLayout.SwitchTabCallback() {
            @Override
            public void switchTab(int position) {
                transPage(position);
            }
        });
    }

    /**
     * 切换页面
     * @param position
     */
    private void transPage(int position){
        switch (position){
            case 0:{
                showFragment(mFirstFragment,position+1);
                break;
            }
            case 1:{
                showFragment(mSecondFragment,position+1);
                break;
            }
            case 2:{
                showFragment(mThirdFragment,position+1);
                break;
            }
        }
    }



    /**
     * 显示隐藏Fragment
     * @param fragment
     */
    private void showFragment(BaseFragment fragment,int position){
        if (mCurrentFragment != null && fragment == mCurrentFragment)
            return;
        if (fragment == null){
            switch (position){
                case 1:{
                    fragment = new HomeFragment();
                    mFirstFragment = (HomeFragment) fragment;
                    break;
                }
                case 2:{
                    fragment = new HomeFragment();
                    mSecondFragment = (HomeFragment) fragment;
                    break;
                }
                case 3:{
                    fragment = new HomeFragment();
                    mThirdFragment = (HomeFragment) fragment;
                    break;
                }
            }
            Bundle bundle = new Bundle();
            bundle.putInt(HomeFragment.BUNDLE_DATA,position);
            fragment.setArguments(bundle);

            mFragmentManager.beginTransaction().add(R.id.layout_content,fragment,String.valueOf(position)).commitAllowingStateLoss();
        }else{
            mFragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss();
        }
        mFragmentManager.beginTransaction().hide(mCurrentFragment).commitAllowingStateLoss();
        mCurrentFragment = fragment;
    }


    @Override
    public View getContentView() {
        return findViewById(R.id.layout_content);
    }

    @Override
    public void onClickLoadFailed() {

    }

    @Override
    public void onClickLoadEmpty() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DownloadModel.GET_UNKNOWN_APP_SOURCES) {
            //如果是从安装未知应用界面返回,就继续判断权限
            if (data != null){
                int action = data.getIntExtra(DownloadModel.UNKNOWN_APP_SOURCES_ACTION,0);
                if (action == 0){//第一次提示，就再校测一次，第2次就不提示了，避免没有设置“允许未知应用安装”导致无限弹出设置页面的问题
                    DownloadModel.checkIsAndroidO(1);
                }
            }
        }
    }


    int backPressCount = 0;
    /**
     * 按2次退出应用
     */
    @Override
    public void onBackPressed() {
        if (backPressCount >= 1) {
            super.onBackPressed();
        } else {
            backPressCount++;
            ToastUtil.showToast(this, "再按一次退出应用");
        }
        mTabLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                backPressCount = 0;
            }
        }, 2000);
    }

}

