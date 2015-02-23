package com.jayway.pong;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;


public class ChatActivity extends ActionBarActivity {

    private Gson gson;

    private Socket socket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        gson = new Gson();

        findViewById(R.id.send_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
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
        Log.d("TAG", "connecting");
        try {
            socket = IO.socket("http://jaywaypongserver.herokuapp.com");
            //socket = IO.socket("http://192.168.0.116:3000");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Log.d("TAG", "connected: " + socket.connected());
                addPlayer("android player");
            }

        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Log.d("TAG", "disconnected " + args);
            }

        }).on("players", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Log.d("TAG", "players " + args);
            }

        }).on("message", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("message: " + args[0]);
            }

        }).on("step", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Log.d("TAG", "step " + args);
            }

        }).on("winning", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Log.d("TAG", "winning " + args);
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
