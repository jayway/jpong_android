package com.jayway.pong;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jayway.pong.model.Message;
import com.jayway.pong.model.Step;
import com.jayway.pong.model.Winning;
import com.jayway.pong.server.PongServer;

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
                addChatMessage(new Message(pongServer.getUserName(), text));
                editText.setText("");
            }
        });
    }

    @Override
    public void onMessage(Message message) {
        addChatMessage(message);
    }

    @Override
    public void onPlayers(List<String> players) {

    }

    @Override
    public void onStep(Step step) {

    }

    @Override
    public void onWinning(Winning winning) {

    }

    private void addChatMessage(final Message message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                StringBuffer tv = new StringBuffer();
                tv.append(message.player + " : " + message.message );
                tv.append("\n");
                tv.append(chatView.getText());
                chatView.setText(tv.toString());
            }
        });

    }
}
