package com.example.sample.ui.WeatherPage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class WeatherViewModel {

    private MutableLiveData<String> mText;

    public WeatherViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("날씨 화면입니다.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
