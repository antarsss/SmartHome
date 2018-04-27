package com.n8plus.smarthome.Presenter.ChangeInformation;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.n8plus.smarthome.Utils.common.Constant;
import com.n8plus.smarthome.Utils.common.VolleySingleton;
import com.n8plus.smarthome.View.ChangeInformation.ChangeInfoViewImpl;
import com.n8plus.smarthome.View.HomePage.HomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class ChangeInfoPresenter implements ChangeInfoPresenterImpl {
    ChangeInfoViewImpl changeInfoView;
    String URI = Constant.URL + "/user/";

    public ChangeInfoPresenter(ChangeInfoViewImpl changeInfoView) {
        this.changeInfoView = changeInfoView;
    }

    @Override
    public void changeInfo(final String username, final Map<String, String> headers) {
        ((AppCompatActivity) changeInfoView).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, URI + username, new JSONObject(headers),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    System.out.println("response: " + response);
                                    String message = response.getString("message");
                                    if (message.equals("OK")) {
                                        changeInfoView.changeInfoSuccess();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                changeInfoView.changeInfoFailure();
                            }
                        });
                jsonObjectRequest.setRetryPolicy(new
                        DefaultRetryPolicy(60000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                VolleySingleton.getInstance((Context) changeInfoView).addToRequestQueue(jsonObjectRequest);
            }
        });
    }

    @Override
    public void changeAvatar(final String username, final Map<String, String> headers) {
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, URI + username, new JSONObject(headers),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", String.valueOf(error));
                    }
                });
        VolleySingleton.getInstance((Context) changeInfoView).addToRequestQueue(jsonObjectRequest);
    }
}

