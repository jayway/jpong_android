package com.jayway.pong.model;

/**
 * Created by Hannes Gruber on 2015-02-27.
 */
public class Paddle {

    int x;
    int y;
    int width;
    int height;

    public Paddle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return "Paddle: x=" + x + " y=" + y + " width=" + width + " height=" + height;
    }
}
