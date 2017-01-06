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
    private boolean isHighlight = true;
    private boolean isRandom = false;
    private boolean isCleanPassword = false;
    private int maxInputLength = -1;
    private String hintText = "";
    private int textSize = -1;
    private String textColor;
    private String hintTextColor;
    /** *输入框背景颜色 */
    private String backgroundColor;
    private String logoPath;

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

    public int getMaxInputLength() {
        return maxInputLength;
    }

    public void setMaxInputLength(int maxInputLength) {
        this.maxInputLength = maxInputLength;
    }

    public String getHintText() {
        return hintText;
    }

    public void setHintText(String hintText) {
        this.hintText = hintText;
    }

    public int getTextSize() {
        return textSize;
    }

    public String getTextColor() {
        return textColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getHintTextColor() {
        return hintTextColor;
    }

    public void setHintTextColor(String hintTextColor) {
        this.hintTextColor = hintTextColor;
    }

    public boolean isHighlight() {
        return isHighlight;
    }

    public void setIsHighlight(boolean showClickEffect) {
        this.isHighlight = showClickEffect;
    }

    public boolean isRandom() {
        return isRandom;
    }

    public void setIsRandom(boolean isRandom) {
        this.isRandom = isRandom;
    }

    public boolean isCleanPassword() {
        return isCleanPassword;
    }

    public void setCleanPassword(boolean isCleanPassWord) {
        this.isCleanPassword = isCleanPassWord;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }
}
