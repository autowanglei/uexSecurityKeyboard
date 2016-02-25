package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo;

import java.io.Serializable;

public class OpenDataVO extends DataBaseVO implements Serializable {

    private static final long serialVersionUID = 2495646906994872093L;

    private double x = 0;
    private double y = 0;
    private double width = -2;
    private double height = -2;
    private int keyboardType = 0;
    private String keyboardDescription = "";

    public int getX() {
        return (int) x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public int getY() {
        return (int) y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getWidth() {
        return (int) width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public int getHeight() {
        return (int) height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getKeyboardType() {
        return keyboardType;
    }

    public void setKeyboardType(int keyboardType) {
        this.keyboardType = keyboardType;
    }

    public String getKeyboardDescription() {
        return keyboardDescription;
    }

    public void setKeyboardDescription(String keyboardDescription) {
        this.keyboardDescription = keyboardDescription;
    }
}
