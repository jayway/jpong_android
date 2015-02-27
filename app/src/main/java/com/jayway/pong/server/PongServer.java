package com.jayway.pong.server;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.jayway.pong.model.Message;
import com.jayway.pong.model.Step;
import com.jayway.pong.model.Winning;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class PongServer {

    private static final String TAG = PongServer.class.getCanonicalName();
    private Gson gson = new Gson();
    private Socket socket = null;
    private List<PongListener> pongListeners = new ArrayList<>();

    private String userName = "jennykallehannes #" + (int) (Math.random() * (1 << 10));

    public interface PongListener {
        public void onMessage(Message message);

        public void onPlayers(List<String> players);

        public void onStep(Step step);

        public void onWinning(Winning winning);
    }

    public void addPongListener(PongListener listener) {
        pongListeners.add(listener);
    }

    public void disconnect() {
        socket.disconnect();
    }

    public String getUserName() {
        return userName;
    }

    public void connectWebSocket() {
        Log.d(TAG, "connecting");
        try {
            socket = IO.socket("http://jaywaypongserver.herokuapp.com");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, "connected: " + socket.connected() + " " + getUserName());
                addPlayer(getUserName());
            }
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Log.d(TAG, "disconnected " + args);
            }

        }).on("players", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                //Log.d(TAG, "players " + args);
            }

        }).on("message", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, "message");
                if (args != null && args.length > 0 && args[0] instanceof JSONObject) {
                    try {
                        JSONObject jsonObject = ((JSONObject) args[0]);
                        Message message = gson.fromJson(jsonObject.toString(), Message.class);
                        for (PongListener listener : pongListeners) {
                            listener.onMessage(message);
                        }

                    } catch (Exception e) {

                    }
                }
            }
        }).on("step", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                //Log.d(TAG, "step");
                try {
                    JSONObject jsonObject = (JSONObject) args[0];
                    Step step = gson.fromJson(jsonObject.toString(), Step.class);
                    //Log.d("pong", "STEP:" + step.toString());
                    Log.d("", "Players: " + step.players.player1.name + " " + step.players.player2.name);
                    for (PongListener listener : pongListeners) {
                        listener.onStep(step);
                    }
                } catch (Exception e) {

                }
            }
        }).on("winning", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, "Winning");
                try {
                    JSONObject jsonObject = (JSONObject) args[0];
                    Winning winning = gson.fromJson(jsonObject.toString(), Winning.class);
                    Log.d("pong", "Winning:" + winning.toString());

                    for (PongListener listener : pongListeners) {
                        listener.onWinning(winning);
                    }
                } catch (Exception e) {

                }
            }
        });

        socket.connect();
    }

    public void addPlayer(String playerName) {
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

    public void ready() {
        JSONObject obj = null;
        try {
            obj = new JSONObject().put("ready", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("ready", obj);
    }

    public void move(int x, int y) {

        String s = "{\n" + "paddle : { x: " + x + ", y:" + y + "}}";
        JSONObject obj = null;
        try {
            obj = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("move", obj);
    }

    public void sendMessage(String text) {
        if (text != null && text.length() > 0) {
            JSONObject obj = null;
            try {
                obj = new JSONObject().put("message", text);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            socket.emit("message", obj);
        }
    }
}
