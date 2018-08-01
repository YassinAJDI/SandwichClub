package com.yassinajdi.sandwichclub.ui.details;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

/**
 * A creator is used to inject the sandwich ID into the ViewModel
 */
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Application mApplication;

    private final int mPosition;

    public static ViewModelFactory getInstance(Application application, int position) {

        return new ViewModelFactory(application, position);
    }

    private ViewModelFactory(Application application, int position) {
        mApplication = application;
        mPosition = position;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SandwichViewModel.class)) {
            //noinspection unchecked
            return (T) new SandwichViewModel(mApplication, mPosition);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
