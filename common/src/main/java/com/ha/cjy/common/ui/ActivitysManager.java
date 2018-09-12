package com.ha.cjy.common.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Iterator;
import java.util.Stack;

/**
 * Activity 管理
 * Created by cjy on 2018/7/18.
 */

public class ActivitysManager {
    //栈表
    private static Stack<Activity> activityStack;
    private static ActivitysManager instance;

    private ActivitysManager() {
    }

    public static ActivitysManager getInstance() {
        if (instance == null){
            synchronized (ActivitysManager.class){
                if(instance == null) {
                    instance = new ActivitysManager();
                }
            }
        }
        return instance;
    }

    /**
     * 添加Activity
     * @param activity
     */
    public void addActivity(Activity activity) {
        if(activityStack == null) {
            activityStack = new Stack();
        }

        activityStack.add(activity);
    }

    /**
     * 当前的Activity
     * @return
     */
    public Activity currentActivity() {
        if(activityStack != null && !activityStack.isEmpty()) {
            Activity activity = (Activity)activityStack.lastElement();
            return activity;
        } else {
            return null;
        }
    }

    /**
     * 查找Activity
     * @param cls
     * @return
     */
    public Activity findActivity(Class<?> cls) {
        Activity activity = null;
        Iterator iterator = activityStack.iterator();

        while(iterator.hasNext()) {
            Activity aty = (Activity)iterator.next();
            if(aty.getClass().equals(cls)) {
                activity = aty;
                break;
            }
        }

        return activity;
    }

    /**
     * 结束最后一个Activity
     */
    public void finishActivity() {
        Activity activity = (Activity)activityStack.lastElement();
        this.finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if(activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }

    }

    /**
     * 结束指定的Activity
     * @param cls
     */
    public void finishActivity(Class<?> cls) {
        Iterator iterator = activityStack.iterator();

        while(iterator.hasNext()) {
            Activity activity = (Activity)iterator.next();
            if(activity.getClass().equals(cls)) {
                this.finishActivity(activity);
            }
        }

    }

    /**
     * 获取Activity列表
     * @return
     */
    public static Stack<Activity> getActivityStack() {
        return activityStack;
    }

    public void finishOthersActivity(Class<?> cls) {
        Iterator iterator = activityStack.iterator();

        while(iterator.hasNext()) {
            Activity activity = (Activity)iterator.next();
            if(!activity.getClass().equals(cls)) {
                this.finishActivity(activity);
            }
        }

    }

    /**
     * 结束所有的Activity
     */
    public void finishAllActivity() {
        int i = 0;

        for(int size = activityStack.size(); i < size; ++i) {
            if(null != activityStack.get(i)) {
                ((Activity)activityStack.get(i)).finish();
            }
        }

        activityStack.clear();
    }

    /**
     * App退出
     * @param context
     */
    @SuppressLint("MissingPermission")
    public void AppExit(Context context) {
        try {
            this.finishAllActivity();
            ActivityManager activityMgr = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception var3) {
            System.exit(0);
        }

    }
}
