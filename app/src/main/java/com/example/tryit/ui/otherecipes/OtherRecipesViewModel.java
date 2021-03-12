package com.example.tryit.ui.otherecipes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

// about the adapter and adding to it:
// https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView

public class OtherRecipesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OtherRecipesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is other recipes fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

