package com.inventor.sagar.databindingmvvmotpdemo.view;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.inventor.sagar.databindingmvvmotpdemo.R;
import com.inventor.sagar.databindingmvvmotpdemo.databinding.ActivitySuccessBinding;
import com.inventor.sagar.databindingmvvmotpdemo.model.User;

public class SuccessActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySuccessBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_success);
        SharedPreferences pref= getSharedPreferences("Login_Pref", 0);
        binding.setUser(new User(pref.getString("name","Hi"),pref.getString("mobile","Number Missing")));
    }
}
