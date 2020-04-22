package com.eton.hookalipay;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
//    String qrCodeURL = "https://qr.alipay.com/fkx15333wmsxbsrkla5wzdb?t=1587524197818";   // 17元，暫不支持此種方式，請在支付寶內打開操作
    String qrCodeURL = "https://qr.alipay.com/fkx13238jokttv2dzbwf336?t=1587462740354";     // 自行設定金額
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, isModuleActive() ? "module is active" : "module not active", Toast.LENGTH_SHORT).show();

        webView = findViewById(R.id.wv_alipay);
        Button broadcastBtn = findViewById(R.id.btn_broadcast);
        Button intentBtn = findViewById(R.id.btn_intent);

        intentBtn.setOnClickListener((v) -> intentToAlipay());
        broadcastBtn.setOnClickListener(v -> broadcastToAlipay());
//        webViewToAlipay();
    }

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

    private void webViewToAlipay() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // 設定可以訪問檔案
        webSettings.setAllowFileAccess(true);
        // 設定支援縮放
        webSettings.setBuiltInZoomControls(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // webSettings.setDatabaseEnabled(true);
        // 使用localStorage則必須開啟
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("platformapi/startapp")) {
                    try {
                        Uri uri = Uri.parse(url);
                        Intent intent;
                        intent = Intent.parseUri(url,
                                Intent.URI_INTENT_SCHEME);
                        intent.addCategory("android.intent.category.BROWSABLE");
                        intent.setComponent(null);
                        intent.setSelector(null);
                        startActivity(intent);
                    } catch (Exception e) {
                    }
                } else {
                    view.loadUrl(url);
                }
                return true;
            }
        });
        webView.loadUrl(qrCodeURL);
    }

    private void broadcastToAlipay() {
        Intent intent = getPackageManager().getLaunchIntentForPackage(MainHook.Alipay);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

//        Intent broadCastIntent = new Intent();
//        broadCastIntent.setAction(AlipayBroadcast.COOKIE_STR_INTENT_FILTER_ACTION);
//        sendBroadcast(broadCastIntent);
    }

    private boolean isModuleActive() {
        return false;
    }
}