package com.example.jstore_android_wito;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BuatPesananRequest extends StringRequest {
    private static final String Paid_URL = "http://10.0.2.2:8080/createinvoicepaid";
    private static final String Unpaid_URL = "http://10.0.2.2:8080/createinvoiceunpaid";
    private static final String Installment_URL = "http://10.0.2.2:8080/createinvoiceinstallment";
    private Map<String, String> params;

    public BuatPesananRequest(int idItem, int idCust, Response.Listener<String> listener){
        super(Method.POST, Paid_URL, listener, null);
        params = new HashMap<>();
        params.put("listItem", idItem+"");
        params.put("id_customer", idCust+"");
    }

    public BuatPesananRequest(int idItem, int idCust, Response.Listener<String> listener, String a){
        super(Method.POST, Unpaid_URL, listener, null);
        params = new HashMap<>();
        params.put("listItem", idItem+"");
        params.put("id_customer", idCust+"");
    }

    public BuatPesananRequest(int idItem, int idCust, int installmentPeriod, Response.Listener<String> listener){
        super(Method.POST, Installment_URL, listener, null);
        params = new HashMap<>();
        params.put("listItem", idItem+"");
        params.put("id_customer", idCust+"");
        params.put("installmentPeriod", installmentPeriod+"");
    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
