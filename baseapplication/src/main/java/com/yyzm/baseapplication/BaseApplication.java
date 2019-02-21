package com.yyzm.baseapplication;

import android.app.Application;

/**
 * @作者 11587
 * @描述 BaseApplication
 * @创建时间 2019/2/19 21:30
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActivityStackManager.getInstance().regist(this);
    }
}
