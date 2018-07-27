package com.udacity.sandwichclub.ui.sandwichlist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.udacity.sandwichclub.model.Sandwich;

import java.util.List;

/**
 * Exposes the data to be used in the sandwich list screen.
 *
 * @author Yassin Ajdi
 */
public class SandwichListViewModel extends AndroidViewModel {

    private final Context mContext;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
//    private final MutableLiveData<List<Sandwich>> mObservableSandwiches;

    public SandwichListViewModel(@NonNull Application application) {
        super(application);

        mContext = application.getApplicationContext();

    }


}
