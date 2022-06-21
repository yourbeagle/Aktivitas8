package com.wahyu.utsa.aktivitas8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AppStartActivity extends AppCompatActivity {

    Button btnLogout;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_start);
        btnLogout = findViewById(R.id.btnLogout);
        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getResources()
                        .getString(R.string.prefStatus),"logout");
                editor.apply();
                startActivity(new Intent(AppStartActivity
                        .this,MainActivity.class));
                finish();
            }
        });
    }
}