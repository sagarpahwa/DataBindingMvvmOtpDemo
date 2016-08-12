package com.inventor.sagar.databindingmvvmotpdemo.JSONPostRequest;

/**
 * Created by Sagar Pahwa on 12-08-2016.
 */
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Sagar Pahwa on 7/19/2016.
 */
public class JsonPostParser {
    InputStream in = null;
    HttpResponse response;
    String json="";
    JSONObject jobj = null;
    public JsonPostParser(){}
    public JSONObject sendJson(final String url, final JSONObject json) {
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            StringEntity se = new StringEntity(json.toString());
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,"application/json"));
            httpPost.setEntity(se);
            response = httpClient.execute(httpPost);
        }catch (Exception e){
            Log.e("post error:", ""+e.toString());}
        return getjson(response);
    }
    public JSONObject getjson(HttpResponse response){
        try {
            Log.e("response is:",""+response);
            if (response != null) {
                in = response.getEntity().getContent(); //Get the data in the entity
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    in));//, 8 //"iso-8859-1"
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            in.close();
            json = sb.toString();
            Log.e("response is:",""+json);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        // try parse the string to a JSON object
        try {
            jobj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        // return JSON String
        return jobj;
    }
}
