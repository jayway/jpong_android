package com.jayway.pong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;


public class PongActivity extends ActionBarActivity {

    private static final String TAG = PongActivity.class.getCanonicalName();
    private Socket socket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(new MyView(this));
    }

    private void connectWebSocket() {
        Log.d(TAG, "connecting");
        try {
            socket = IO.socket("http://jaywaypongserver.herokuapp.com");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket.on(com.github.nkzawa.socketio.client.Socket.EVENT_CONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Log.d(TAG, "connected: " + socket.connected());
                addPlayer("Android#" + (int) (Math.random() * (1 << 10)));
            }

        }).on(com.github.nkzawa.socketio.client.Socket.EVENT_DISCONNECT, new Emitter.Listener() {

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

                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
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

    public class MyView extends View {
        public MyView(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
            super.onDraw(canvas);
            // Draw the flag of Japan!
            // You might want to replace this with some paddles and a ball for pong...
            int x = getWidth();
            int y = getHeight();
            int radius;
            radius = (int) (Math.min(canvas.getWidth(), canvas.getHeight()) * (3f / 5f)) / 2;
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawPaint(paint);
            paint.setColor(0xffc70025);
            canvas.drawCircle(x / 2, y / 2, radius, paint);
        }
    }



}
