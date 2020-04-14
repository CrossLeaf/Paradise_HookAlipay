package com.eton.hookalipay;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, isModuleActive() ? "module is active" : "module not active", Toast.LENGTH_SHORT).show();
//        Uri uri = Uri.parse("https://qr.alipay.com/fkx17131t7ucml5ews4qx74?t=1586507412835");
//        Uri uri = Uri.parse("https://qr.alipay.com/fkx13028e1j1oirmrwthi70?t=1586847941929");
//        Uri uri = Uri.parse("https://qr.alipay.com/fkx13028e1j1oirmrwthi70");
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        String intentFullUri = "alipays://platformapi/startapp?appId=10000007&url=https://qr.alipay.com/fkx13028e1j1oirmrwthi70";
//        try {
//            // "intent://platformapi/startapp?saId=10000007&qrcode=https://qr.alipay.com/fkx13028e1j1oirmrwthi70?#Intent;scheme=alipays;package=com.eg.android.AlipayGphone;end"
//            Intent intent = Intent.parseUri(intentFullUri, Intent.URI_INTENT_SCHEME);
//            startActivity(intent);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
        intentToAlipay();
    }

    String qrCodeURL = "https://qr.alipay.com/fkx13028e1j1oirmrwthi70"; // 暫不支持此種方式，請在支付寶內打開操作

    private void intentToAlipay() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = null;
        try {
            uri = Uri.parse("alipays://platformapi/startapp?saId=10000007&qrcode=" + URLEncoder.encode(qrCodeURL, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(uri);
        startActivity(intent);
    }

    private boolean isModuleActive() {
        return false;
    }
}