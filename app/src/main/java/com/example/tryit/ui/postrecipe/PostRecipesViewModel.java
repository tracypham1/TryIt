package com.example.tryit.ui.postrecipe;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

// about the adapter and adding to it:
// https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView

public class PostRecipesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PostRecipesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is post recipes fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

