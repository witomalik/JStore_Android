package com.example.jstore_android_wito;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText nameInput = (EditText) findViewById(R.id.nameInput);
        final EditText usernameInput = (EditText) findViewById(R.id.usernameInput);
        final EditText emailInput = (EditText) findViewById(R.id.emailInput);
        final EditText passInput = (EditText) findViewById(R.id.passInput);
        final Button loginButton = (Button) findViewById(R.id.signupButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = nameInput.getText().toString();
                final String username = usernameInput.getText().toString();
                final String email = emailInput.getText().toString();
                final String password = passInput.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse != null){
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register Success!").create().show();
                                finish();
                            }
                        }
                        catch (JSONException e){
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setMessage("Register Failed!").create().show();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(name, username, email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
        final TextView loginClickable = (TextView) findViewById(R.id.loginClickable);
        loginClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
