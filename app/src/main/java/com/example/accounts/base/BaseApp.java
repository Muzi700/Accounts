package com.example.accounts.base;

import android.app.Application;

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseUtil.getInstance().init(getApplicationContext());
    }
}
