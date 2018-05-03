package com.n8plus.smarthome.Presenter.StartPresenter.RegisterPage;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.n8plus.smarthome.Utils.common.Constant;
import com.n8plus.smarthome.Utils.common.VolleySingleton;
import com.n8plus.smarthome.View.StartPage.RegisterPage.RegisterViewImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class RegisterPresenter implements RegisterPresenterImpl {
    RegisterViewImpl registerView;

    public RegisterPresenter(RegisterViewImpl registerView) {
        this.registerView = registerView;
    }

    @Override
    public void registerAccount(final Map<String, String> headers) {
        ((AppCompatActivity) registerView).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constant.SIGNUP, new JSONObject(headers),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Boolean result = (Boolean) response.get("success");
                                    if(result){
                                        registerView.registerSuccess();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                registerView.registerFailure();
                            }
                        });
                VolleySingleton.getInstance((Context) registerView).addToRequestQueue(jsonObjectRequest);
            }
        });
    }
}
