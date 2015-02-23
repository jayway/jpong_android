package com.jayway.pong;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class ChatActivity extends ActionBarActivity {

    private static final String TAG = ChatActivity.class.getCanonicalName();
    private Socket socket = null;
    private TextView chatView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        findViewById(R.id.send_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        chatView = (TextView) findViewById(R.id.chat_text);
    }

    @Override
    protected void onStart() {
        super.onStart();
        connectWebSocket();
    }

    @Override
    protected void onStop() {
        super.onStop();
        socket.disconnect();
    }

    private void connectWebSocket() {
        Log.d(TAG, "connecting");
        try {
            socket = IO.socket("http://jaywaypongserver.herokuapp.com");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Log.d(TAG, "connected: " + socket.connected());
                addPlayer("Android#" + Math.random() * (1 << 10));
            }

        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Log.d(TAG, "disconnected " + args);
            }

        }).on("players", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Log.d(TAG, "players " + args);
            }

        }).on("message", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                if (args != null && args.length > 0 && args[0] instanceof JSONObject) {
                    try {
                        final String text = ((JSONObject) args[0]).getString("message");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                StringBuffer tv = new StringBuffer();
                                tv.append(text);
                                tv.append("\n");
                                tv.append(chatView.getText());
                                chatView.setText(tv.toString());
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        }).on("step", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Log.d(TAG, "step " + args);
            }

        }).on("winning", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Log.d(TAG, "winning " + args);
            }

        });
        socket.connect();

    }

    private void addPlayer(String playerName) {
        if (playerName != null && playerName.length() > 0) {
            JSONObject obj = null;
            try {
                obj = new JSONObject().put("playername", playerName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            socket.emit("add player", obj);
        }
    }

    private void sendMessage() {
        EditText editText = (EditText) findViewById(R.id.chat_input);
        String text = editText.getText().toString();
        if (text != null && text.length() > 0) {
            JSONObject obj = null;
            try {
                obj = new JSONObject().put("message", text);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            socket.emit("message", obj);
            editText.setText("");
        }
    }

}
