package com.inventor.sagar.databindingmvvmotpdemo.service;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.inventor.sagar.databindingmvvmotpdemo.Config;
import com.inventor.sagar.databindingmvvmotpdemo.JSONPostRequest.JsonPostParser;
import com.inventor.sagar.databindingmvvmotpdemo.view.SuccessActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ravi on 04/04/15.
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class HttpService extends IntentService {
    private String otp;
    private JSONObject response;
    private static String TAG = HttpService.class.getSimpleName();

    public HttpService() {
        super(HttpService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            otp = intent.getStringExtra("otp");
            verifyOtp();
        }
    }


    private void verifyOtp() {
        new OTPResult().execute();
        try{
            if(response!=null) {
                JSONObject responseObj = response;

                // Parsing json object response
                // response will be a json object
                boolean error = responseObj.getBoolean("error");
                String message = responseObj.getString("message");

                if (!error) {
                    // parsing the user profile information
                    JSONObject profileObj = responseObj.getJSONObject("profile");

                    String name = profileObj.getString("name");
                    String pass = profileObj.getString("password");
                    String mobile = profileObj.getString("mobile");

                    Intent intent = new Intent(HttpService.this, SuccessActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(getApplicationContext(), "Failed to Connect", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(HttpService.this, SuccessActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(),
                    "Error: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
    class OTPResult extends AsyncTask<String, String, String> {
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
                sendjson.put("otp", otp);
                Log.e("sending Api Query :", Config.URL_VERIFY_OTP + sendjson.toString());
                JSONObject recievejson = null;
                recievejson = new JsonPostParser().sendJson(Config.URL_REQUEST_SMS, sendjson);
                Log.e("respose is:", " " + recievejson);
                if (recievejson != null) {
                    response = recievejson;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            //pDialog.dismiss();
        }
    }

}
