package com.inventor.sagar.databindingmvvmotpdemo.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.inventor.sagar.databindingmvvmotpdemo.R;
import com.inventor.sagar.databindingmvvmotpdemo.databinding.MainActivityBinding;
import com.inventor.sagar.databindingmvvmotpdemo.viewmodel.MianViewModel;

public class MainActivity extends AppCompatActivity {
    public static Context instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = getApplicationContext();
        MainActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        binding.setViewModel(new MianViewModel());
    }
}
