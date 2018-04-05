package com.n8plus.smarthome.Presenter.Login;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.n8plus.smarthome.Activity.HomeActivity;
import com.n8plus.smarthome.Utils.common.Constain;
import com.n8plus.smarthome.Utils.common.VolleySingleton;
import com.n8plus.smarthome.View.Login.LoginActivity;
import com.n8plus.smarthome.View.Login.LoginViewImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hiep_Nguyen on 3/30/2018.
 */

public class LoginPresenter implements LoginPresenterImpl {
    LoginViewImpl loginView;

    public LoginPresenter(LoginViewImpl loginView) {
        this.loginView = loginView;
    }

    @Override
    public void checkLogin(String usn, String pass) {
        String URI = Constain.URL +"/login";
        System.out.println("URL: "+URI);
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", usn);
        params.put("password", pass);
        System.out.println("Set usn va pass");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URI, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("login")) {
                                System.out.println("Vo day r");
                                loginView.loginSuccess();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("change Pass response -->> " + error.toString());
                        loginView.loginFailure();
                    }
                });

        request.setRetryPolicy(new
                DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance((Context) loginView).addToRequestQueue(request);
    }
}
