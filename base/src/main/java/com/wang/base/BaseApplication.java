package com.wang.base;

import android.app.Application;

public class BaseApplication extends Application {

    public static BaseApplication mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mApp = this;
    }

}
