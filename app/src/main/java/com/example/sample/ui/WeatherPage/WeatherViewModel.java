package com.example.sample.ui.WeatherPage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WeatherViewModel extends ViewModel{

    private MutableLiveData<String> mText;

    public WeatherViewModel()  {
        mText = new MutableLiveData<>();
        mText.setValue("날씨");

    }

    public LiveData<String> getText() {

        return mText;
    }
}
