package com.example.tryit.ui.draftrecipe;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DraftRecipeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DraftRecipeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is where you can enter your recipes and save them as drafts.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}