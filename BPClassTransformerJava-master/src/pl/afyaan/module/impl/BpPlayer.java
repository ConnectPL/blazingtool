package pl.afyaan.module.impl;

import java.util.ArrayList;
import java.util.List;

public class BpPlayer {
    public static List<BpPlayer> bpPlayers = new ArrayList<>();

    private String userName;
    private double x;
    private double y;
    private double z;

    public BpPlayer() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "BpPlayer{" +
                "userName='" + userName + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
