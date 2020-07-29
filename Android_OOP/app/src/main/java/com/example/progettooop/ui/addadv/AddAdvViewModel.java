package com.example.progettooop.ui.addadv;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddAdvViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public AddAdvViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is addadv fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
