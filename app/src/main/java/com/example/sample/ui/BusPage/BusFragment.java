package com.example.sample.ui.BusPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sample.R;

public class BusFragment extends Fragment {
    private BusViewModel busViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        busViewModel =
                ViewModelProviders.of(this).get(BusViewModel.class);
        View root = inflater.inflate(R.layout.fragment_bus, container, false);
        final TextView textView = root.findViewById(R.id.text_setting);
        busViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
