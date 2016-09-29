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
    private boolean isScrollWithWeb = false;
    private boolean showClearText = true;
    private boolean showInputBox = true;

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

    public boolean isScrollWithWeb() {
        return isScrollWithWeb;
    }

    public void setIsScrollWithWeb(boolean isScrollWithWeb) {
        this.isScrollWithWeb = isScrollWithWeb;
    }

    public boolean isShowClearText() {
        return showClearText;
    }

    public void setShowClearText(boolean showClearText) {
        this.showClearText = showClearText;
    }

    public boolean isShowInputBox() {
        return showInputBox;
    }

    public void setShowInputBox(boolean showInputBox) {
        this.showInputBox = showInputBox;
    }
}
