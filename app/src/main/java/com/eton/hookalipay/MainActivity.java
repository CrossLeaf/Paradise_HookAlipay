package com.eton.hookalipay;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String qrCodeURL = "https://qr.alipay.com/fkx15214gyaiipzdaez0c1b?t=1587090600573"; // 暫不支持此種方式，請在支付寶內打開操作
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.wv_alipay);
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
//        intentToAlipay();
        webViewToAlipay();
//        test123();
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

    private boolean isModuleActive() {
        return false;
    }
    String a = "123456";
    Map<String, String> mmap = new HashMap<>();
    private void test123() {
        if (mmap != null) {
            String aaa = "aaa";
            isModuleActive();
        } else {
            String bbb = "bbb";
            webViewToAlipay();
        }
    }
}