package com.jayway.pong.model;

/**
 * Created by Hannes Gruber on 2015-02-27.
 */
public class Ball {
    int x;
    int y;
    int x_speed;
    int y_speed;
    int radius;

    public Ball(int x, int y, int x_speed, int y_speed, int radius) {
        this.x = x;
        this.y = y;
        this.x_speed = x_speed;
        this.y_speed = y_speed;
        this.radius = radius;
    }
}
