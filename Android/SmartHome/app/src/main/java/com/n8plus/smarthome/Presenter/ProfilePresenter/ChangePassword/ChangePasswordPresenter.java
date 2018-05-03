package com.n8plus.smarthome.Presenter.ProfilePresenter.ChangePassword;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.n8plus.smarthome.Utils.common.Constant;
import com.n8plus.smarthome.Utils.common.VolleySingleton;
import com.n8plus.smarthome.View.ProfilePage.ChangePassword.ChangePasswordViewImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class ChangePasswordPresenter implements ChangePasswordPresenterImpl {

    ChangePasswordViewImpl changePasswordView;

    public ChangePasswordPresenter(ChangePasswordViewImpl changePasswordView) {
        this.changePasswordView = changePasswordView;
    }

    @Override
    public void changePassword(final String username, final Map<String, String> headers) {
        ((AppCompatActivity) changePasswordView).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, Constant.UPDATE_USER + username, new JSONObject(headers),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    boolean result = (boolean) response.get("success");
                                    if (result) {
                                        changePasswordView.changePasswordSuccess();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                changePasswordView.changePasswordFailure();
                            }
                        });
                VolleySingleton.getInstance((Context) changePasswordView).addToRequestQueue(jsonObjectRequest);
            }
        });
    }
}
