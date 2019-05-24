package com.example.jstore_android_wito;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PesananFetchRequest extends StringRequest {
    private static final String BASE_URL = "http://10.0.2.2:8080/invoicecustomer/";
    private Map<String, String> params;

    public PesananFetchRequest(int id_customer, Response.Listener<String> listener) {
        super(Method.GET, BASE_URL+id_customer, listener,null);
        params = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
