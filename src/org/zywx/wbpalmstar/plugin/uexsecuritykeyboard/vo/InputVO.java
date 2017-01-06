package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo;

public class InputVO {

    private String clearText = "";
    private String ciphertext = "";

    public InputVO(String clearText, String ciphertext) {
        this.clearText = clearText;
        this.ciphertext = ciphertext;
    }

    public String getClearText() {
        return clearText;
    }

    public String getCiphertext() {
        return ciphertext;
    }

    public void setClearText(String clearText) {
        this.clearText = clearText;
    }

    public void setCiphertext(String ciphertext) {
        this.ciphertext = ciphertext;
    }
}
