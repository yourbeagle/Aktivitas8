package com.wahyu.utsa.aktivitas8;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister;
    EditText etUsername, etEmail, etPassword, etMobile, etGender;
    private String Link = "http://192.168.1.2/aktivitas8/register.php";
    private String username, email, password, mobile, gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnRegister = findViewById(R.id.btnRegister);
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmailAddress);
        etPassword = findViewById(R.id.etPassword);
        etMobile = findViewById(R.id.etPhone);
        etGender = findViewById(R.id.etGender);
        username = "";
        email = "";
        password = "";
        mobile = "";
        gender = "";
    }

    public void register(View view) {
        username = etUsername.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        mobile = etMobile.getText().toString().trim();
        gender = etGender.getText().toString().trim();
        if (!username.equals("") && !email.equals("") && !password.equals("") && !mobile.equals("") && !gender.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Link, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("success")) {
                        Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                        btnRegister.setClickable(false);
                    } else if (response.equals("failure")) {
                        Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("email", email);
                    params.put("password", password);
                    params.put("mobile", mobile);
                    params.put("gender", gender);
                    return params;
                }
            };
            stringRequest.setRetryPolicy(
                    new DefaultRetryPolicy(30000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getMySingleton(RegisterActivity.this).addRequestQueue(stringRequest);
        } else {
            Toast.makeText(RegisterActivity.this, "Harap Isi Field", Toast.LENGTH_SHORT).show();
        }
    }
}
