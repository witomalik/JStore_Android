package com.example.jstore_android_wito;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class BuatPesananActivity extends AppCompatActivity {

    private int itemId;
    private String itemName;
    private String itemCategory;
    private String itemStatus;
    private double itemPrice;
    private int installmentPeriod;
    private String selectedPayment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_pesanan);
        final TextView textPeriod = findViewById(R.id.textPeriod);
        final TextView item_name = findViewById(R.id.item_name);
        final TextView item_category = findViewById(R.id.item_category);
        final TextView item_status = findViewById(R.id.item_status);
        final TextView item_price = findViewById(R.id.item_price);
        final TextView installment_period =  findViewById(R.id.installment_period);
        final TextView total_price = findViewById(R.id.total_price);
        final RadioGroup radioGroup = findViewById(R.id.radioGroup);
        final RadioButton paid = findViewById(R.id.paid);
        final RadioButton unpaid = findViewById(R.id.unpaid);
        final RadioButton installment = findViewById(R.id.installment);
        final Button hitung = findViewById(R.id.hitung);
        final Button pesan = findViewById(R.id.pesan);

        final int id_customer = getIntent().getExtras().getInt("id_customer");

        itemId = getIntent().getExtras().getInt("id_item");
        itemName = getIntent().getExtras().getString("name");
        itemCategory = getIntent().getExtras().getString("category");
        itemStatus = getIntent().getExtras().getString("status");
        itemPrice = getIntent().getExtras().getInt("price");

        pesan.setVisibility(View.INVISIBLE);
        textPeriod.setVisibility(View.INVISIBLE);
        installment_period.setVisibility(View.INVISIBLE);

        item_name.setText(itemName);
        item_category.setText(itemCategory);
        item_status.setText(itemStatus);
        item_price.setText(String.format("%s",itemPrice));
        total_price.setText(Integer.toString(0));

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                pesan.setVisibility(View.INVISIBLE);
                if (checkedId == installment.getId()){
                    textPeriod.setVisibility(View.VISIBLE);
                    installment_period.setVisibility(View.VISIBLE);
                    selectedPayment = "Ins";
                }
                else if (checkedId == paid.getId()) {
                    textPeriod.setVisibility(View.INVISIBLE);
                    installment_period.setVisibility(View.INVISIBLE);
                    selectedPayment = "Pa";
                }
                else {
                    textPeriod.setVisibility(View.INVISIBLE);
                    installment_period.setVisibility(View.INVISIBLE);
                    selectedPayment = "Un";
                }

            }
        });

        hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedPayment.equals("Pa") || selectedPayment.equals("Un")){
                    total_price.setText(item_price.getText().toString());
                    pesan.setVisibility(View.VISIBLE);
                }
                else if(selectedPayment.equals("Ins")){
                    String period = installment_period.getText().toString();
                    if(!period.equals("")){
                        installmentPeriod = Integer.parseInt(period);
                    }
                    if (installmentPeriod == 0) {
                        installment_period.setError("Period must be greater than 0");
                        installment_period.requestFocus();
                        return;
                    }
                    double total = itemPrice/installmentPeriod;
                    total_price.setText(String.format("%s", total));
                    pesan.setVisibility(View.VISIBLE);
                }
                else{
                    hitung.setError("Masukkan pilihan!");
                }
                hitung.setVisibility(View.GONE);
            }
        });

        pesan.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            if(jsonResponse!=null){
                                AlertDialog.Builder builder = new AlertDialog.Builder(BuatPesananActivity.this);
                                builder.setMessage("Success!").create().show();
                                finish();
                            }
                        }catch (JSONException e){
                            AlertDialog.Builder builder = new AlertDialog.Builder(BuatPesananActivity.this);
                            builder.setMessage("Failed").create().show();
                            e.printStackTrace();
                        }
                    }
                };

                if(selectedPayment.equals("Pa")){
                    BuatPesananRequest buatPesananRequest = new BuatPesananRequest(itemId, id_customer, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(BuatPesananActivity.this);
                    queue.add(buatPesananRequest);
                }else if(selectedPayment.equals("Un")){
                    BuatPesananRequest buatPesananRequest = new BuatPesananRequest(itemId,id_customer,responseListener,"a");
                    RequestQueue queue = Volley.newRequestQueue(BuatPesananActivity.this);
                    queue.add(buatPesananRequest);
                }else{
                    BuatPesananRequest buatPesananRequest = new BuatPesananRequest(itemId, id_customer, installmentPeriod, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(BuatPesananActivity.this);
                    queue.add(buatPesananRequest);
                }
            }
        });

    }
}
