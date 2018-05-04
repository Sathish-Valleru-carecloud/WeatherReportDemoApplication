package com.example.sathish.weatherreportapplication.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class AppVolleyApiManager {

    public static AppVolleyApiManager apiManager;
    private static RequestQueue requestQueue;


    public synchronized static void initVolley(Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
    }

    public static AppVolleyApiManager instance() {
        if (apiManager == null) {
            apiManager = new AppVolleyApiManager();
        }
        return apiManager;
    }

    public synchronized static RequestQueue getRequestQueue() {
        return requestQueue;
    }


    public void getJsonObjectResponse(String url, final AppVolleyNetWorkResponse netWorkResponse) {

        if (url.contains(" ")) {
            url = url.replaceAll(" ", "%20");
        }
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
               Log.v("", "API CALL : " + response.toString());
                netWorkResponse.onSuccessResponse(response);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("", "API CALL : " + error.getMessage());
                //netWorkResponse.onError(error.getMessage());
                String json = "";
                NetworkResponse response = error.networkResponse;
                if(response != null && response.data != null){
                    json = new String(response.data);
                    netWorkResponse.onError("Error from Volley");
                }
            }
        });

        jsonObjRequest.setTag(url);
        getRequestQueue().add(jsonObjRequest);
    }
}