package com.inventor.sagar.databindingmvvmotpdemo.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.inventor.sagar.databindingmvvmotpdemo.R;
import com.inventor.sagar.databindingmvvmotpdemo.databinding.ActivityOtpBinding;
import com.inventor.sagar.databindingmvvmotpdemo.viewmodel.OTPViewModel;

public class OtpActivity extends AppCompatActivity {
    public static Context instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = getApplicationContext();
        ActivityOtpBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_otp);
        binding.setViewModel(new OTPViewModel());
    }
}
