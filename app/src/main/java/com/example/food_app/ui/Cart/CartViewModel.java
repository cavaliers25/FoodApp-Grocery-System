package com.example.food_app.ui.Cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CartViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public CartViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Cart fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}