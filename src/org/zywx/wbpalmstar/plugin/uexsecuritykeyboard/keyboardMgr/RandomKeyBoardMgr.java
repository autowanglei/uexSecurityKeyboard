package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.keyboardMgr;

import java.util.Random;

import org.zywx.wbpalmstar.engine.universalex.EUExUtil;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.EUExSecurityKeyboard;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo.OpenDataVO;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * 键盘
 * 
 * @Description
 * @Author Lewis(lgs@yitong.com.cn) 2014年6月18日 下午8:21:07
 * @Class AllKeyBoard Copyright (c) 2014 Shanghai P&C Information Technology
 *        Co.,Ltd. All rights reserved.
 */
/**
 * 随机键盘
 * 
 * @author yipeng.zhang(yipeng.zhang@3g2win.com)
 * @createdAt 2014年7月25日
 */
public class RandomKeyBoardMgr extends KeyboardBaseMgr {

    private static final String TAG = "RandomKeyBoardMgr";
    // private boolean isAnimRunning = false;// 是否正在动画中
    private LinearLayout keypad_num;
    private Button[] btnFunctions = new Button[2];
    private Button[] btnNumbs = new Button[10];
    private String inputValue = "";

    // 数字按钮
    private int[] keyNumIds = {
            EUExUtil.getResIdID(
                    "plugin_uexsecuritykeyboard_key_board_num_zero"),
            EUExUtil.getResIdID("plugin_uexsecuritykeyboard_key_board_num_one"),
            EUExUtil.getResIdID("plugin_uexsecuritykeyboard_key_board_num_two"),
            EUExUtil.getResIdID(
                    "plugin_uexsecuritykeyboard_key_board_num_three"),
            EUExUtil.getResIdID(
                    "plugin_uexsecuritykeyboard_key_board_num_four"),
            EUExUtil.getResIdID(
                    "plugin_uexsecuritykeyboard_key_board_num_five"),
            EUExUtil.getResIdID("plugin_uexsecuritykeyboard_key_board_num_six"),
            EUExUtil.getResIdID(
                    "plugin_uexsecuritykeyboard_key_board_num_seven"),
            EUExUtil.getResIdID(
                    "plugin_uexsecuritykeyboard_key_board_num_eight"),
            EUExUtil.getResIdID(
                    "plugin_uexsecuritykeyboard_key_board_num_nine") };

    // 功能按钮
    private int[] keyFunctionIds = {
            EUExUtil.getResIdID("plugin_uexsecuritykeyboard_btn_num_del"),
            EUExUtil.getResIdID("plugin_uexsecuritykeyboard_btn_num_done") };

    /**
     * 用来初始化随机键盘管理器
     * 
     * @param context
     * @param mEUExKeyboard
     * @param keyboardView
     * @param editText
     * @param dataVO
     */
    public RandomKeyBoardMgr(final Context context,
            final EUExSecurityKeyboard mEUExKeyboard,
            final View keyboardView, final EditText editText,
            OpenDataVO dataVO) {
        super(context);
        // 获取控件对象
        isCustom = true;
        findKeyBoardView(keyboardView);
        randomKeyNum(context, keyboardView);
        setEditTextListener(mEUExKeyboard, keyboardView,
                new KeyboardStatuController() {
                    @Override
                    public void hide(EUExSecurityKeyboard mEUExKeyboard,
                            View keyboardView, boolean isDone) {
                        hideKeyboard();
                    }

                    @Override
                    public void show(EUExSecurityKeyboard mEUExKeyboard,
                            View keyboardView) {
                        showKeyboard(context, mEUExKeyboard, keyboardView);
                    }
                });
    }

    /**
     * 初始化随机键盘的view
     */
    private void findKeyBoardView(View keyboardView) {
        View tempView = keyboardView;
        if (keyboardView.getParent() != null
                && keyboardView.getParent().getParent() != null) {
            tempView = (View) keyboardView.getParent().getParent();
        }
        keypad_num = (LinearLayout) tempView
                .findViewById(EUExUtil.getResIdID("keyboard_view"));
        for (int i = 0; i < btnNumbs.length; i++) {
            btnNumbs[i] = (Button) tempView.findViewById(keyNumIds[i]);
            if (btnNumbs[i] != null) {
                btnNumbs[i].setOnClickListener(keyBoardInputClickListener);
                // // 金额键盘的监听
                // if (null != keypadType && keypadType.equals("11")) {
                // btnNumbs[i].setOnClickListener(digitPadClickListener);
                // } else {
                // }
            }
        }

        for (int i = 0; i < btnFunctions.length; i++) {
            btnFunctions[i] = (Button) tempView.findViewById(keyFunctionIds[i]);
            if (btnFunctions[i] != null) {
                btnFunctions[i]
                        .setOnClickListener(keyBoardFunctionClickListener);
            }
        }
    }

    /**
     * 更新输入的内容
     * 
     * @Description
     * @version 3.0 2014-8-1
     */
    private void setValue(String inputStr) {

        // EUExRandomKeyboard.getPluginInstance().changeInputContent(inputValue,
        // cryptValue);
        // Selection.setSelection(inputFrame.getText(), inputFrame.getText()
        // .length());// 移动光标到最右
    }

    /**
     * 删除输入的内容
     * 
     * @Description
     * @version 3.0 2014-8-1
     */
    private void delValue() {
        inputValue = inputValue.substring(0, inputValue.length() - 1);
        // EUExRandomKeyboard.getPluginInstance().changeInputContent(inputValue,
        // cryptValue);
        // Selection.setSelection(inputFrame.getText(), inputFrame.getText()
        // .length());// 移动光标

    }

    /**
     * 清空内容
     */
    private void clear() {
        inputValue = "";

        // EUExRandomKeyboard.getPluginInstance().changeInputContent(inputValue,
        // cryptValue);
    }

    /**
     * 获取输入内容
     * 
     * @return
     */
    private String getInputContent() {
        return inputValue;
    }

    /**
     * 将密码替换成"*" dmq
     */
    StringBuffer replace;

    public String replacePwd(String pwd) {
        replace = new StringBuffer();
        for (int i = 0; i < pwd.length(); i++) {
            replace.append("*");
        }
        return replace.toString();
    }

    // 输入监听
    private OnClickListener keyBoardInputClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Button inputBtn = (Button) v;
            // 拼接录入值
            setValue(inputBtn.getText().toString());
        }
    };

    // 功能监听
    private OnClickListener keyBoardFunctionClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

            if (v.getId() == EUExUtil
                    .getResIdID("plugin_uexsecuritykeyboard_btn_num_del")) {
                delValue();
            } else if (v.getId() == EUExUtil
                    .getResIdID("plugin_uexsecuritykeyboard_btn_num_done")) {
            }
        }
    };

    /**
     * 显示键盘视图
     */
    public void showKeyboard(Context context,
            EUExSecurityKeyboard mEUExKeyboard, View keyboardView) {
        randomKeyNum(context, keyboardView);
    }

    /**
     * 隐藏键盘视图
     */
    public void hideKeyboard() {
    }

    /**
     * 键盘是否显示
     * 
     * @Description
     * @return
     * @author 孙靖
     * @version 1.0 2013年12月29日
     */
    public boolean isKeyBoardShow() {
        // if (null != relativeLayoutKeyBoard) {
        // int visibility = relativeLayoutKeyBoard.getVisibility();
        // if (visibility == View.VISIBLE) {
        // return true;
        // }
        // }
        return false;
    }

    /**
     * 加密和随机数字键盘
     * 
     * @Description
     * @author 孙靖
     * @version 1.0 2013年12月30日
     */
    private void randomKeyNum(Context context, final View keyboardView) {

        ((Activity) context).runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // 更新界面
                Random ran = new Random();
                java.util.List<Integer> list = new java.util.ArrayList<Integer>();
                while (list.size() < 10) {
                    int n = ran.nextInt(10);
                    if (!list.contains(n))
                        list.add(n);// 如果n不包涵在list中，才添加
                }
                for (int i = 0; i < keyNumIds.length; i++) {
                    if (keyboardView.getParent() != null
                            && keyboardView.getParent().getParent() != null) {
                        btnNumbs[i] = (Button) ((View) keyboardView.getParent()
                                .getParent()).findViewById(keyNumIds[i]);
                    } else {
                        Button button = (Button) keyboardView
                                .findViewById(keyNumIds[i]);
                        btnNumbs[i] = (Button) keyboardView
                                .findViewById(keyNumIds[i]);
                    }

                    btnNumbs[i].setText(list.get(i) + "");
                    btnNumbs[i].setOnClickListener(keyBoardInputClickListener);
                    // if (null != keypadType && keypadType.equals("11")) {
                    // btnNumbs[i].setOnClickListener(digitPadClickListener);
                    // } else {
                    // }
                }
                showBoardByType();
            }
        });
    }

    /**
     * 根据参数显示某种键盘
     * 
     * @Description
     * @param flag
     *            1=abc 2=数字 3=符号
     */
    private void showBoardByType() {
        keypad_num.setVisibility(View.VISIBLE);
    }
}