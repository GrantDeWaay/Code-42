package edu.iastate.code42.utils;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

import edu.iastate.code42.R;

import java.net.URISyntaxException;


public class WebSocketRunUnitTests {

        private static final String webs = "wss://socketsbay.com/wss/v2/2/demo/";

    private static WebSocketClient cc;

    public static void startTests(TextView out){
        Draft[] drafts = {
                new Draft_6455()
        };
        try{
        cc = new WebSocketClient(new URI(webs), (Draft) drafts[0]) {
            @Override
            public void onMessage(String message) {
                Log.d("", "run() returned: " + message);
                String s = out.getText().toString();
                out.setText(s + "\nServer:" + message);
            }

            @Override
            public void onOpen(ServerHandshake handshake) {
                Log.d("OPEN", "run() returned: " + "is connecting");

            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.d("CLOSE", "onClose() returned: " + reason);
            }

            @Override
            public void onError(Exception e) {
                Log.d("Exception:", e.toString());
            }
        };
    }
        catch (URISyntaxException e) {
            Log.d("Exception:", e.getMessage());
            e.printStackTrace();
        }
        cc.connect();

    }


}