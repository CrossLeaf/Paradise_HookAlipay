package com.eton.hookalipay;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, isModuleActive() ? "module is active" : "module not active", Toast.LENGTH_SHORT).show();
//        Uri uri = Uri.parse("https://qr.alipay.com/fkx17131t7ucml5ews4qx74?t=1586507412835");
        Uri uri = Uri.parse("https://qr.alipay.com/fkx13028e1j1oirmrwthi70?t=1586847941929");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private boolean isModuleActive() {
        return false;
    }
}