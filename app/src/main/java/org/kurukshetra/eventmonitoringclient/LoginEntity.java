package org.kurukshetra.eventmonitoringclient;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ompra on 11/21/2017.
 */

public class LoginEntity {

    private String email;
    private String password;

    public LoginEntity(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public interface RestClientInterface{
        void onLogin(String token, int code,VolleyError error);
    }

    public JSONObject getParams(){
        JSONObject loginEntityJson = null;
        Gson gson = new Gson();
        String loginString = gson.toJson(this);
        try {
            loginEntityJson = new JSONObject(loginString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return loginEntityJson;
    }
}
