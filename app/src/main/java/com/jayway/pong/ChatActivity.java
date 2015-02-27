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
import com.jayway.pong.model.Step;
import com.jayway.pong.server.PongServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.List;

import static com.jayway.pong.server.PongServer.PongListener;

public class ChatActivity extends ActionBarActivity implements PongListener {

    private static final String TAG = ChatActivity.class.getCanonicalName();

    private TextView chatView = null;

    private PongServer pongServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        pongServer = ((PongApplication) getApplication()).getPongServer();
        pongServer.addPongListener(this);

        chatView = (TextView) findViewById(R.id.chat_text);

        findViewById(R.id.send_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.chat_input);
                String text = editText.getText().toString();
                pongServer.sendMessage(text);
                addChatMessage(text);
                editText.setText("");
            }
        });
    }

    @Override
    public void onMessage(String message) {
        addChatMessage(message);
    }

    @Override
    public void onPlayers(List<String> players) {

    }

    @Override
    public void onStep(Step step) {

    }

    private void addChatMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                StringBuffer tv = new StringBuffer();
                tv.append(message);
                tv.append("\n");
                tv.append(chatView.getText());
                chatView.setText(tv.toString());
            }
        });

    }
}
