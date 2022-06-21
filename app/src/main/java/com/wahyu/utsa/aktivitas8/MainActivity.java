package com.wahyu.utsa.aktivitas8;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    MaterialEditText email, password;
    Button login;
    CheckBox checkedStatus;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        checkedStatus = findViewById(R.id.checkbox);
        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String loginStatus = sharedPreferences.getString(getResources().getString(R.string.prefStatus),"");
        if (loginStatus.equals("loggedin")){
            startActivity(new Intent(MainActivity.this,AppStartActivity.class));
            finish();
        }

        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tex_email = Objects.requireNonNull(email.getText()).toString();
                String tex_password = Objects.requireNonNull(password.getText()).toString();
                if (TextUtils.isEmpty(tex_email) || TextUtils.isEmpty(tex_password)){
                    Toast.makeText(MainActivity.this, "All Fields Required", Toast.LENGTH_SHORT).show();
                }else {
                    login(tex_email, tex_password);
                }
            }
        });

    }

    private void login(final String email, final String password){
        final ProgressDialog progressDialog = new ProgressDialog(
                MainActivity.this);
        progressDialog.setTitle("Registering your account");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        String uRl = "http://192.168.1.2/aktivitas8/login.php";
        StringRequest request = new StringRequest(Request.Method.POST,uRl,
                (String response) -> {
            if (response.equals("Login Success")){
                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (checkedStatus.isChecked()){
                    editor.putString(getResources().getString(R.string.prefStatus),"loggedin");
                }else {
                    editor.putString(getResources().getString(R.string.prefStatus),"loggedout");
                }
                editor.apply();
                startActivity(new Intent(MainActivity.this, AppStartActivity.class));
                progressDialog.dismiss();
                finish();
            }else {
                Toast.makeText(MainActivity.this,
                        response, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }, error -> {
            Toast.makeText(MainActivity.this,
                    error.toString(), Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("email",email);
                param.put("psw",password);
                return param;
            }
        };

        request.setRetryPolicy(
                new DefaultRetryPolicy(30000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getMySingleton(MainActivity.this).addRequestQueue(request);
    }

    public void linkregis(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}