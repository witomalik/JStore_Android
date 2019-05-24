package com.example.jstore_android_wito;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Supplier> listSupplier = new ArrayList<>();
    private ArrayList<Item> listItem = new ArrayList<>();
    private HashMap<Supplier, ArrayList<Item>> childMapping = new HashMap<>();
    private int id_customer;
    private String name_customer;

    private ArrayList<Invoice> invoices = new ArrayList<>();
    MainListAdapter mainListAdapter;
    ExpandableListView expListView;

    RecyclerView rvOrder;
    TextView wellcomeMessage;



//    ArrayList<String> listDataHeader;
//    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id_customer = getIntent().getExtras().getInt("id_customer");
        name_customer = getIntent().getExtras().getString("name_customer");

        // get the listview
        expListView = findViewById(R.id.lvExp);
        rvOrder = findViewById(R.id.rvOrder);
        wellcomeMessage = findViewById(R.id.welcomeMessage);

        // preparing list data
//        prepareListData();

        mainListAdapter= new MainListAdapter(MainActivity.this, listSupplier, childMapping);

        // setting list adapter
        expListView.setAdapter(mainListAdapter);

        refreshList();

        initRecyclerView();

        wellcomeMessage.setText("Welcome, "+name_customer+"!");

    }

    private void initRecyclerView(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonResponse = new JSONArray(response);
                    for(int i=0;i<jsonResponse.length();i++){
                        JSONObject invoice = jsonResponse.getJSONObject(i);

                        int id = invoice.getInt("id");
                        String date = invoice.getString("date");
                        JSONArray item_json = invoice.getJSONArray("item");

                        ArrayList<String> items = new ArrayList<>();
                        for(int y=0;y<item_json.length();y++){
                            for(Item item : listItem){
                                if(item.getId() == Integer.valueOf(item_json.get(y).toString().trim())){
                                    items.add(item.getName());
                                }
                            }
                        }

                        String invoiceType = invoice.getString("invoiceType");
                        String invoiceStatus = invoice.getString("invoiceStatus");
                        Integer totalPrice = invoice.getInt("totalPrice");

                        if(invoiceStatus.equals("Paid")){
                            Invoice temp = new Invoice(id, date, items, totalPrice, invoiceType, invoiceStatus);
                            temp.setActive(false);
                            invoices.add(temp);
                        }else if(invoiceStatus.equals("Unpaid")) {
                            Invoice temp = new Invoice(id, date, items, totalPrice, invoiceType, invoiceStatus);
                            temp.setActive(false);
                            invoices.add(temp);
                        }else if(invoiceStatus.equals("Installment")) {
                            int installmentPeriod = invoice.getInt("installmentPeriod");
                            int installmentPrice = invoice.getInt("installmentPrice");
                            Invoice temp = new Invoice(id, date, items, totalPrice, invoiceType, invoiceStatus, installmentPeriod, installmentPrice);
                            temp.setActive(false);
                            invoices.add(temp);
                        }
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                OrderRecyclerViewAdapter adapter = new OrderRecyclerViewAdapter(invoices, MainActivity.this);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                rvOrder.setLayoutManager(layoutManager);
                rvOrder.setAdapter(adapter);
            }
        };

        PesananFetchRequest request = new PesananFetchRequest(id_customer,responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(request);
    }

    protected void refreshList(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    if(jsonResponse!=null){
                        for(int i=0; i<jsonResponse.length(); i++){
                            JSONObject item = jsonResponse.getJSONObject(i);
                            JSONObject supplier = item.getJSONObject("supplier");
                            JSONObject location = supplier.getJSONObject("location");
                            Location location1 = new Location( location.getString("city"), location.getString("province"), location.getString("description"));
                            Supplier supplier1 = new Supplier(supplier.getInt("id"), supplier.getString("name"), supplier.getString("email"), supplier.getString("phoneNumber"), location1);

                            if(listSupplier.size()>0){
                                for(Supplier sup : listSupplier){
                                    if(!(sup.getId() == supplier1.getId())){
                                        listSupplier.add(supplier1);
                                    }
                                }
                            }
                            else{
                                listSupplier.add(supplier1);
                            }

                            Item item1 = new Item(item.getInt("id"),item.getString("name"), item.getInt("price"),item.getString("category"),  item.getString("status"),supplier1);
                            listItem.add(item1);
                        }
                        for(Supplier sup : listSupplier){
                            ArrayList<Item> temp = new ArrayList<>();
                            for(Item item : listItem){
                                if(item.getSupplier().getName().equals(sup.getName()) || item.getSupplier().getEmail().equals(sup.getEmail()) || item.getSupplier().getPhoneNumber().equals(sup.getPhoneNumber())){
                                    temp.add(item);
                                }
                            }
                            childMapping.put(sup,temp);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Refresh Failed!").create().show();
                }

                List<String> listDataHeader= new ArrayList<>();
                HashMap<String, List<String>> listDataChild = new HashMap<>();
                for(Supplier s : listSupplier) {
                    listDataHeader.add(s.getName());
                    ArrayList<Item> tmpItem = childMapping.get(s);
                    ArrayList<String> item = new ArrayList<>();
                    for(Item i : tmpItem){
                        item.add(i.getName());
                    }
                    listDataChild.put(s.getName(),item);
                }

                mainListAdapter = new MainListAdapter(MainActivity.this, listSupplier, childMapping);
                expListView.setAdapter(mainListAdapter);
                expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        Item selected = childMapping.get(listSupplier.get(groupPosition)).get(childPosition);
                        Intent itemIntent = new Intent(MainActivity.this, BuatPesananActivity.class);
                        itemIntent.putExtra("id_item", selected.getId());
                        itemIntent.putExtra("name", selected.getName());
                        itemIntent.putExtra("category", selected.getCategory());
                        itemIntent.putExtra("status", selected.getStatus());
                        itemIntent.putExtra("price", selected.getPrice());
                        itemIntent.putExtra("id_customer", id_customer);
                        startActivity(itemIntent);
                        return false;
                    }
                });
            }
        };
        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(menuRequest);
    }



}
