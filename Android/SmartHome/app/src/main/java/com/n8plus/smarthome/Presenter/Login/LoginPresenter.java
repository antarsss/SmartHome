package com.n8plus.smarthome.Presenter.Login;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.n8plus.smarthome.Model.User;
import com.n8plus.smarthome.Utils.common.Constant;
import com.n8plus.smarthome.Utils.common.VolleySingleton;
import com.n8plus.smarthome.View.LoadScreen.StartViewActivity;
import com.n8plus.smarthome.View.Login.LoginViewImpl;

import org.json.JSONArray;
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
    public void checkLogin(final String usn, String pass) {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("username", usn);
        params.put("password", pass);
        System.out.println(new JSONObject(params).toString());
        ((AppCompatActivity) loginView).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constant.AUTHENTICATE, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(final JSONObject response) {
                                try {
                                    if (response.getBoolean("success")) {
                                        JSONObject authen = new JSONObject();
                                        authen.put("token", response.getString("token"));
                                        boolean authenticated = StartViewActivity.mSocket.authorization(authen);
                                        if (authenticated) {
                                            Gson gson = new Gson();
                                            JSONObject jsonObject = (JSONObject) response.get("user");
                                            // Xu li avatar
                                            JSONArray jo_avatar = (JSONArray) jsonObject.get("avatar");
                                            JSONObject jo_data = jo_avatar.getJSONObject(0);
                                            JSONArray ja_bytes = (JSONArray) jo_data.get("data");
                                            jsonObject.remove("avatar");
                                            jsonObject.put("avatar", ja_bytes);
                                            User user = (User) gson.fromJson(jsonObject.toString(), User.class);
                                            loginView.loginSuccess(user);
                                        } else {
                                            loginView.loginFailure("Unauthorized");
                                        }
                                    } else {
                                        loginView.loginFailure(response.getString("message"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                loginView.loginFailure("Connecting error...");
                            }
                        });

                request.setRetryPolicy(new
                        DefaultRetryPolicy(60000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                VolleySingleton.getInstance((Context) loginView).addToRequestQueue(request);
            }
        });
    }
}
