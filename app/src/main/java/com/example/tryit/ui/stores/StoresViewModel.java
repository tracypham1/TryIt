package com.example.tryit.ui.stores;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

// about the adapter and adding to it:
// https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView

public class StoresViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public StoresViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is stores fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

