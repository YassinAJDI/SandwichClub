package com.yassinajdi.sandwichclub;

import android.app.Application;

import timber.log.Timber;

public class SandwichApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
