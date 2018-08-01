package com.yassinajdi.sandwichclub.ui.details;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.yassinajdi.sandwichclub.R;
import com.yassinajdi.sandwichclub.model.Sandwich;
import com.yassinajdi.sandwichclub.utils.AppExecutors;
import com.yassinajdi.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import timber.log.Timber;

public class SandwichViewModel extends AndroidViewModel {

    private final Context mContext;

    private final MutableLiveData<Sandwich> mSandwich = new MutableLiveData<>();

    public SandwichViewModel(@NonNull Application application, final int position) {
        super(application);
        Timber.d("Creating viewModel");

        // initialize data
        mContext = application.getApplicationContext();
        AppExecutors mExecutors = AppExecutors.getInstance();

        // parse json array on background thread
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Timber.d("Start parsing Json");

                String[] sandwiches = mContext.getResources().getStringArray(R.array.sandwich_details);

                Sandwich sandwich = null;
                try {
                    sandwich = JsonUtils.parseSandwichJson(sandwiches[position]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Timber.d("Json parsing finished");

                if (sandwich != null) {
                    Timber.d("Json not null");
                    // update sandwich livedata from background thread
                    mSandwich.postValue(sandwich);
                }
            }
        });
    }

    public LiveData<Sandwich> getSandwich() {
        return mSandwich;
    }
}
