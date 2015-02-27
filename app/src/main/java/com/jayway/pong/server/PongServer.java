package com.jayway.pong.server;


import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.jayway.pong.model.Step;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class PongServer {

    private static final String TAG = PongServer.class.getCanonicalName();
    private Socket socket = null;
    private List<PongListener> pongListeners = new ArrayList<>();

    public interface PongListener {
        public void onMessage(String message);

        public void onPlayers(List<String> players);

        public void onStep(Step step);
    }

    public void addPongListener(PongListener listener) {
        pongListeners.add(listener);
    }

    public void disconnect() {
        socket.disconnect();
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
                Log.d(TAG, "connected: " + socket.connected());
                addPlayer("jennykallehannes");
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
                Log.d(TAG, "message");
                if (args != null && args.length > 0 && args[0] instanceof JSONObject) {
                    try {
                        final String text = ((JSONObject) args[0]).getString("message");
                        for (PongListener listener : pongListeners) {
                            listener.onMessage(text);
                        }

                    } catch (Exception e) {

                    }
                }
            }
        }).on("step", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, "step");
                if (args != null && args.length > 0 && args[0] instanceof JSONObject) {
                    try {
                        final String text = ((JSONObject) args[0]).getString("message");
                        for (PongListener listener : pongListeners) {
                            listener.onStep(new Step());
                        }

                    } catch (Exception e) {

                    }
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
