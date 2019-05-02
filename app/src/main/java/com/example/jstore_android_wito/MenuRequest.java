package com.example.jstore_android_wito;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MenuRequest extends StringRequest {
    private static final String Login_URL = "http://10.0.2.2:8080/items";
    private Map<String, String> params;
    public MenuRequest(Response.Listener<String> listener){
        super (Method.GET, Login_URL, listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
