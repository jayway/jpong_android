package com.jayway.pong.json.request;

/**
 * Created by erbsman on 2/20/15.
 */
public class AddPlayer {
    public String getPlayername() {
        return playername;
    }

    public void setPlayername(String playername) {
        this.playername = playername;
    }

    String playername;
}
