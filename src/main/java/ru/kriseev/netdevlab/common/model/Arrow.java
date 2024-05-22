package ru.kriseev.netdevlab.common.model;

import com.google.gson.annotations.Expose;

public class Arrow {
    @Expose
    private double y;
    @Expose
    private double x;
    private final double speed;
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    public void step() {
        x += speed;
    }

    public Arrow(double x, double y, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }
}
