package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.util;

public class JsConst {

    public static final String CALLBACK_GET_CONTENT = "uexSecurityKeyboard.cbGetContent";
    /**
     * 键盘点击的监听，回调数据为输入类型，分为： INPUT_TYPE_TEXT = 0; 不显示输入框时给网页回调。 INPUT_TYPE_DEL
     * =1; 不显示输入框时给网页回调。 INPUT_TYPE_DONE = 2 无论是否显示输入框，都给网页回调。
     * 
     */
    public static final String ON_KEY_PRESS = "uexSecurityKeyboard.onKeyPress";
    public static final String ON_SHOW_KEY_BOARD = "uexSecurityKeyboard.onShowKeyboard";
    public static final String ON_HIDE_KEY_BOARD = "uexSecurityKeyboard.onHideKeyboard";
}
