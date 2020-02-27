package com.ci123.cifilemanager.ui;

import android.app.Application;

import com.ci123.cifilemodule.CIFileManager;

/**
 * @author: 11304
 * @date: 2020/2/27
 * @desc:
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CIFileManager.getInstance().init(getApplicationContext());
    }
}
