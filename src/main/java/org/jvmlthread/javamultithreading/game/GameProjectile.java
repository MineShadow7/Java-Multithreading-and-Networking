package org.jvmlthread.javamultithreading.game;

import com.google.gson.annotations.Expose;

public class GameProjectile {
    @Expose
    private double x;
    @Expose
    private double y;
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
    public GameProjectile(double x, double y, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }
}

