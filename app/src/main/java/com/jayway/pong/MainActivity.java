package com.jayway.pong;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.jayway.pong.server.PongServer;


public class MainActivity extends ActionBarActivity {

    private PongServer pongServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pongServer = ((PongApplication) getApplication()).getPongServer();
        setContentView(R.layout.activity_main);

        findViewById(R.id.chat_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.play_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PongActivity.class);
                startActivity(intent);
            }
        });

    }

}
