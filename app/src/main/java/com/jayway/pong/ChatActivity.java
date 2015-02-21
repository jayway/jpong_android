package com.jayway.pong;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.jayway.pong.json.request.AddPlayer;
import com.jayway.pong.json.request.Message;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;


public class ChatActivity extends ActionBarActivity {

    private WebSocketClient webSocketClient;

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        gson = new Gson();

        connectWebSocket();

        //addPlayer("Player 3");

        findViewById(R.id.send_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void connectWebSocket() {
        URI uri;
        try {
            uri = new URI("ws://jaywaypongserver.herokuapp.com:80");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }


        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
                //webSocketClient.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
                addPlayer("player test");
            }

            @Override
            public void onMessage(final String s) {
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("TAG", "message from server: " + s);
//                        TextView textView = (TextView) findViewById(R.id.chat_text);
//                        textView.setText(textView.getText() + "\n" + message);
                    }
                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s + " bool: " + b + " i: " + i);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };

        Log.i("TAG", "connect to: "+uri.toASCIIString());
        webSocketClient.connect();
    }

    private void addPlayer(String playerName) {
        if (playerName != null && playerName.length() > 0) {
            AddPlayer addPlayer = new AddPlayer();
            addPlayer.setPlayername(playerName);
            String json = gson.toJson(addPlayer);
            Log.d("TAG", "json: " + json);
            webSocketClient.send(json);

        }
    }

    private void sendMessage() {
        EditText editText = (EditText) findViewById(R.id.chat_input);
        String text = editText.getText().toString();
        if (text != null && text.length() > 0) {
            Message message = new Message();
            message.setMessage(text);
            String json = gson.toJson(message);
            Log.d("TAG", "json: " + json);
            webSocketClient.send(json);
            editText.setText("");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
