package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard;

public class JsConst {

    public static final int KEYBOARD_MODE_NUMBER = 0;
    public static final int KEYBOARD_MODE_CUSTOM = 1;
    public static final int KEYBOARD_MODE_DEFAULT = 2;
    public static final int KEYCODE_CHANGE_LETTER = -7;
    public static final int KEYCODE_CHANGE_SYMBOL = -8;
    public static final int KEYCODE_CHANGE_NUMBER = -9;
    public static final String CALLBACK_GET_CONTENT = "uexSecurityKeyboard.cbGetContent";
    /**
     * 键盘点击的监听，回调数据为输入类型，分为：
     *  INPUT_TYPE_TEXT = 0; 不显示输入框时给网页回调。
     *  INPUT_TYPE_DEL =1; 不显示输入框时给网页回调。
     *  INPUT_TYPE_DONE = 2 无论是否显示输入框，都给网页回调。
     */
    public static final String ON_KEY_PRESS = "uexSecurityKeyboard.onKeyPress";
}
