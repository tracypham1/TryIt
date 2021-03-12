package com.example.tryit.ui.generatedrecipes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

// about the adapter and adding to it:
// https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView

public class GeneratedRecipesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GeneratedRecipesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is generated recipes fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

