package com.example.sathish.weatherreportapplication.volley;

import org.json.JSONObject;

public interface AppVolleyNetWorkResponse {

	void onError(String error);

	void onSuccessResponse(JSONObject response);
}
