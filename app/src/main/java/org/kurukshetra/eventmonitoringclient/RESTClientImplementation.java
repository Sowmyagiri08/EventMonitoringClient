package org.kurukshetra.eventmonitoringclient;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;

public class RESTClientImplementation {
    private static final String CMS_URL = Constants.CMS_URL;
    static RequestQueue queue;
    private static final String BASE_URL = Constants.BASE_URL;
    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
    private static String getAbsoluteCMSUrl(String relativeUrl) {
        return CMS_URL + "/"+relativeUrl;
    }

    public static void normalLogin(final LoginEntity loginEntity, final LoginEntity.RestClientInterface restClientInterface, final Context context){
        queue = VolleySingleton.getInstance(context).getRequestQueue();
        String url = getAbsoluteUrl("/api/v1/participant/signin");
        JSONObject postParams = new JSONObject();
        JSONObject data = loginEntity.getParams();
        try {
            data.put("m",true);
            postParams.put("data",data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonBaseRequest jsonBaseRequest = new JsonBaseRequest(Request.Method.POST, url, postParams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Login Response",response.toString());
                Gson gson = new Gson();
                ResponseEntity responseEntity = gson.fromJson(response.toString(),ResponseEntity.class);
                try {
                    int statusCode = response.getJSONObject("code").getInt("statusCode");
                    if(statusCode == 200){
                        restClientInterface.onLogin(responseEntity.getToken(),200,null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("normal login","error");
                restClientInterface.onLogin("",error.networkResponse.statusCode,new VolleyError());
            }
        },30000,0);
        queue.add(jsonBaseRequest);
    }


}
