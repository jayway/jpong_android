package com.jayway.pong.model;


import org.json.JSONException;
import org.json.JSONObject;

public class Step {

    public Ball ball;
    public Paddle playerPaddle;
    public Paddle remotePlayerPaddle;
    public Players players;
    public Bounds bounds;

    @Override
    public String toString() {
        return "Step: " + ball + " playerPaddle=" + playerPaddle + " remotePlayerPaddle=" + remotePlayerPaddle;
    }
}
