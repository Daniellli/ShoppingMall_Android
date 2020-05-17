package com.example.xsc238.app;

import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.xsc238.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);

    }
}
