package com.example.jstore_android_wito;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText emailInput = (EditText) findViewById(R.id.emailInput);
        final EditText passInput = (EditText) findViewById(R.id.passInput);
        final Button loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailInput.getText().toString();
                final String password = passInput.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if(jsonResponse!=null){
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Success!").create().show();
                                int id_customer = jsonResponse.getInt("id");
                                String name_customer = jsonResponse.getString("name");
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("id_customer", id_customer);
                                intent.putExtra("name_customer", name_customer);
                                //((EditText)emailInput).setText("");
                                //((EditText)passInput).setText("");
                                startActivity(intent);

                            }
                        } catch (JSONException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            //((EditText)emailInput).setText("");
                            //((EditText)passInput).setText("");
                            builder.setMessage("Login Failed!").create().show();

                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(email,password,responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

        final TextView registerClickable = (TextView) findViewById(R.id.registerClickable);
        registerClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regisIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(regisIntent);
            }
        });
    }

    private void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText)view).setText("");
            }

            if(view instanceof ViewGroup && (((ViewGroup)view).getChildCount() > 0))
                clearForm((ViewGroup)view);
        }
    }
}
