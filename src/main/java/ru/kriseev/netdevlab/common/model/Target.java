package ru.kriseev.netdevlab.common.model;

import com.google.gson.annotations.Expose;

public class Target {
    @Expose
    private final double radius;
    @Expose
    private final double x;
    @Expose
    private double y;
    private final double maxY;
    private final double minY;
    private final double speed;
    private boolean movingUp = true;
    private final int scoreIncrement;

    public int getScoreIncrement() {
        return scoreIncrement;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRadius() {
        return radius;
    }
    public void step() {
        if (movingUp) {
            y += speed;
            if (y > maxY) {
                movingUp = false;
            }
        } else {
            y -= speed;
            if (y < minY) {
                movingUp = true;
            }
        }
    }
    public boolean checkHit(Arrow arrow) {
        double dx = arrow.getX() - x;
        double dy = arrow.getY() - y;
        return dx * dx + dy * dy <= radius * radius;
    }

    public Target(double radius, double x, double y, double maxY, double minY, double speed, int scoreIncrement) {
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.maxY = maxY;
        this.minY = minY;
        this.speed = speed;
        this.scoreIncrement = scoreIncrement;
    }
}
