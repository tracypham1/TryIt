package com.example.tryit.ui.shoppinglist;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tryit.R;

// about the adapter and adding to it:
// https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView

public class ShoppingListViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ShoppingListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is shopping list fragment");
    }
    //String[] myStringArray = {"Ingredient 1", "Ingredient 2", "Ingredient 3"};

    public LiveData<String> getText() {
        return mText;
    }
}

