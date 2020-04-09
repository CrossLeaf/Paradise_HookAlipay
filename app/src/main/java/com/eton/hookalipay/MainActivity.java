package com.eton.hookalipay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, isModuleActive() ? "module is active" : "module not active", Toast.LENGTH_SHORT).show();
    }

    private boolean isModuleActive(){
        return false;
    }
}