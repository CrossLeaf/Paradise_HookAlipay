package com.eton.hookalipay;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class MyWebSocketClient extends WebSocketClient {
    private static final String TAG = MyWebSocketClient.class.getSimpleName();

    public MyWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d(TAG, "onOpen: status = " + handshakedata.getHttpStatus() +
                "\n status message = " + handshakedata.getHttpStatusMessage());
    }

    @Override
    public void onMessage(String message) {
        Log.d(TAG, "onMessage: " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d(TAG, "onClose: code = " + code + " reason = " + reason + " remote = " + remote);
    }

    @Override
    public void onError(Exception ex) {
        Log.d(TAG, "onError: " + ex);
    }
}
