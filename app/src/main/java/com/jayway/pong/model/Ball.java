package com.jayway.pong.model;

import org.json.JSONObject;

/**
 * Created by Hannes Gruber on 2015-02-27.
 */
public class Ball {
    public int x;
    public int y;
    public int x_speed;
    public int y_speed;
    public int radius;

    public Ball(int x, int y, int x_speed, int y_speed, int radius) {
        this.x = x;
        this.y = y;
        this.x_speed = x_speed;
        this.y_speed = y_speed;
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Ball: x=" + x + " y=" + y + " x_speed=" + x_speed + " y_speed=" + y_speed + " radius=" + radius;
    }
}
