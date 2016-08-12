package com.inventor.sagar.databindingmvvmotpdemo.viewmodel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.inventor.sagar.databindingmvvmotpdemo.service.HttpService;
import com.inventor.sagar.databindingmvvmotpdemo.view.MainActivity;
import com.inventor.sagar.databindingmvvmotpdemo.view.OtpActivity;

/**
 * View model for the MainActivity
 */
public class OTPViewModel{
    private SharedPreferences pref = MainActivity.instance.getSharedPreferences("Login_Pref", 0);
    private SharedPreferences.Editor editor = pref.edit();
    private String message= "Request Failed! \n Try Again.";
    private String uotp = " ";
    public void onVerify(View view){
        if(!uotp.equals(" ")){
            if(uotp.length()==6){
                Intent grapprIntent = new Intent(OtpActivity.instance, HttpService.class);
                grapprIntent.putExtra("otp", uotp);
                OtpActivity.instance.startService(grapprIntent);
            }
            else Toast.makeText(OtpActivity.instance, "Wrong OTP", Toast.LENGTH_SHORT).show();

        }
    }
    public void onOtpEnter(CharSequence s, int start, int before, int count) {
        if (!s.toString().equals("")) {
            uotp = s.toString();
            Log.e("name", "onTextChanged " + s);
        }
    }


}