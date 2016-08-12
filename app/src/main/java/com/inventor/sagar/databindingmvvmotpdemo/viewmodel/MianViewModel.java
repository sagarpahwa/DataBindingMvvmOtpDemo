package com.inventor.sagar.databindingmvvmotpdemo.viewmodel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.inventor.sagar.databindingmvvmotpdemo.Config;
import com.inventor.sagar.databindingmvvmotpdemo.JSONPostRequest.JsonPostParser;
import com.inventor.sagar.databindingmvvmotpdemo.view.MainActivity;
import com.inventor.sagar.databindingmvvmotpdemo.view.OtpActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sagar Pahwa on 12-08-2016.
 */
public class MianViewModel {

    private static final String TAG = "MainViewModel";
    private String message= "Request Failed! \n Try Again.";
    SharedPreferences pref = MainActivity.instance.getSharedPreferences("Login_Pref", 0);
    SharedPreferences.Editor editor = pref.edit();
//    private ProgressDialog pDialog;
    public static String uname = " ";
    public static String upass = " ";
    public static String umobile = " ";
    private SpannableStringBuilder text;

    public void onProceed(View view) {
        if(uname.equals(" ")||upass.equals(" ")||umobile.equals(" ")){
            Toast.makeText(MainActivity.instance, "Please Validate Your Data", Toast.LENGTH_SHORT).show();
        }
        else{
            if(isValidPhoneNumber(umobile)){
                Toast.makeText(MainActivity.instance, "Phone Number not valid", Toast.LENGTH_SHORT).show();
            }
            else {

                new RequestSms().execute();
            }
        }
    }

    public void onNameChanged(CharSequence s, int start, int before, int count) {
        if (!s.toString().equals("")) {
            uname = s.toString();
            Log.e("name", "onTextChanged " + s);
        }
    }

    public void onMobileChanged(CharSequence s, int start, int before, int count) {
        if (!s.toString().equals("")) {
            umobile = s.toString();
            Log.e("name", "onTextChanged " + s);
        }
    }

    public void onPassEnter(CharSequence s, int start, int before, int count) {
        if (!s.toString().equals("")) {
            upass = s.toString();
            Log.e("name", "onTextChanged " + s);
        }
    }

    class RequestSms extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*pDialog = new ProgressDialog(MainActivity.instance);
            pDialog.setMessage("Applying Leave...");
            pDialog.setCancelable(false);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();*/
        }

        @SuppressWarnings("unchecked")
        @Override
        protected String doInBackground(String... args) {
            JSONObject sendjson = new JSONObject();
            try {
                sendjson.put("name", uname);
                sendjson.put("mobile", umobile);
                sendjson.put("password", upass);
                Log.e("sending Api Query :", Config.URL_REQUEST_SMS + sendjson.toString());
                JSONObject recievejson = null;
                recievejson = new JsonPostParser().sendJson(Config.URL_REQUEST_SMS, sendjson);
                Log.e("respose is:", " " + recievejson);
                if (recievejson != null) {
                    if (!recievejson.getBoolean("error")) {
                        message = recievejson.getString("message");
                        editor.putString("name", uname);
                        editor.putString("mobile", umobile);
                        editor.putString("password", upass);
                        editor.commit();
                        MainActivity.instance.startActivity(new Intent(MainActivity.instance, OtpActivity.class));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            //pDialog.dismiss();
            Intent myIntent = new Intent(MainActivity.instance, OtpActivity.class);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MainActivity.instance.startActivity(myIntent);
            Toast.makeText(MainActivity.instance, message + "", Toast.LENGTH_SHORT).show();
        }
    }/*
    public MianViewModel() {
        setColoredText();
    }

    private void setColoredText() {
            String str = "Already Have an Account?";
            text = new SpannableStringBuilder(str);
            // make "Already" (characters 0 to 7) red
            this.text.setSpan(new ForegroundColorSpan(Color.RED), 0, 7, 0);
            // make "Have" (characters 8 to 13) one and a half time bigger than the textbox
            this.text.setSpan(new RelativeSizeSpan(1.5f), 8, 13, 0);
            // make "an" (characters 14 to 16) display a toast message when touched
            final Context context = MainActivity.instance;
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "an", Toast.LENGTH_LONG).show();
                }
            };
            this.text.setSpan(clickableSpan, 14, 16, 0);
            // make "account" (characters 17 to 25) struck through
            this.text.setSpan(new StrikethroughSpan(), 17, 25, 0);
    }*/
    private static boolean isValidPhoneNumber(String mobile) {
        String regEx = "^[0-9]{10}$";
        return mobile.matches(regEx);
    }
}