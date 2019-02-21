package com.yyzm.baseapplication;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.util.Iterator;
import java.util.Stack;

/**
 * @作者 11587
 * @描述 ActivityStackManager
 * @创建时间 2019/2/19 22:52
 */
public class ActivityStackManager implements Application.ActivityLifecycleCallbacks {
    private Stack<Activity> activityStack;

    public ActivityStackManager() {
        activityStack = new Stack<>();
    }

    public static class Instance {
        public static ActivityStackManager INSTANCE = new ActivityStackManager();
    }

    public static ActivityStackManager getInstance() {
        return Instance.INSTANCE;
    }

    public void regist(Application app) {
        app.registerActivityLifecycleCallbacks(this);
    }

    public void unregist(Application app) {
        app.unregisterActivityLifecycleCallbacks(this);
    }


    /* @param activity  需要添加进栈管理的activity
     */
    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    /**
     * @param activity 需要从栈管理中删除的activity
     * @return
     */
    public boolean removeActivity(Activity activity) {
        return activityStack.remove(activity);
    }

    /**
     * @param activity 查询指定activity在栈中的位置，从栈顶开始
     * @return
     */
    public int searchActivity(Activity activity) {
        return activityStack.search(activity);
    }

    /**
     * @param activity 将指定的activity从栈中删除然后finish()掉
     */
    public void finishActivity(Activity activity) {
        activityStack.pop().finish();
    }

    /**
     * @param activity 将指定类名的activity从栈中删除并finish()掉
     */
    public void finishActivityClass(Class<Activity> activity) {
        if (activity != null) {
            Iterator<Activity> iterator = activityStack.iterator();
            while (iterator.hasNext()) {
                Activity next = iterator.next();
                if (next.getClass().equals(activity)) {
                    iterator.remove();
                    finishActivity(next);
                }
            }
        }
    }

    /**
     * 销毁所有的activity
     */
    public void finishAllActivity() {
        while (!activityStack.isEmpty()) {
            activityStack.pop().finish();
        }
    }

    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {

        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.i("***************", "onActivityCreated" + savedInstanceState);
        activityStack.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.i("***************", "onActivityStarted");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.i("***************", "onActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.i("***************", "onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.i("***************", "onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.i("***************", "onActivitySaveInstanceState" + outState);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (searchActivity(activity) == activityStack.size()) {
            /*判断销毁的activity是不是最后一个，如果是，则退出App，关闭清理所有东西*/
            AppExit(activity);
            Log.i("***************", "App退出了");
        }
    }
}
