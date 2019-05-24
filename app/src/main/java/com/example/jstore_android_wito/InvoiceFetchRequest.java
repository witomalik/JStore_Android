package com.example.jstore_android_wito;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class InvoiceFetchRequest extends StringRequest {
    private static final String INVO_URL = "http://10.0.2.2:8080/invoice";
    public InvoiceFetchRequest(int id, Response.Listener<String> listener){
        super(Method.GET, INVO_URL+"/"+id, listener, null);
    }
}
