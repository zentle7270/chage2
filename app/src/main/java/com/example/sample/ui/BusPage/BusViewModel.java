package com.example.sample.ui.BusPage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BusViewModel extends ViewModel{
    private MutableLiveData<String> mText;

    public BusViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("버스 화면입니다.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
