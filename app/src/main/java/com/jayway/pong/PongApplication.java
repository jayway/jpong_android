package com.jayway.pong;

import android.app.Application;

import com.jayway.pong.server.PongServer;

public class PongApplication extends Application {

    private PongServer pongServer;

    @Override
    public void onCreate() {
        super.onCreate();
        getPongServer().connectWebSocket();
    }

    @Override
    public void onTerminate() {
        getPongServer().disconnect();
        super.onTerminate();
    }

    public PongServer getPongServer() {
        if (pongServer == null) {
            pongServer = new PongServer();
        }

        return pongServer;
    }

    public String myUserName() {
        return pongServer.getUserName();
    }

}
