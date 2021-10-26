package de.topcu.game.utils;

import lombok.Getter;

@Getter
public class Vector2D {

    public double x;
    public double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2D subtract(Vector2D first, Vector2D second) {
        return new Vector2D(first.x - second.x, first.y - second.y);
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public void add(Vector2D v) {
        x += v.x;
        y += v.y;
    }

    public void add(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public void multiply(double n) {
        x *= n;
        y *= n;
    }

    public void div(double n) {
        x /= n;
        y /= n;
    }

    public void normalize() {
        double m = magnitude();
        if (m != 0 && m != 1) {
            div(m);
        }
    }

    public void limit(double max) {
        if (magnitude() > max) {
            normalize();
            multiply(max);
        }
    }

    public double mag() {
        return Math.sqrt(x * x + y * y);
    }

    public double heading2D() {
        return Math.atan2(y, x);
    }

}
