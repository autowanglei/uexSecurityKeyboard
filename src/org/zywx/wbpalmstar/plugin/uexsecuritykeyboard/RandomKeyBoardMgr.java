package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard;

import java.util.Random;

import org.zywx.wbpalmstar.engine.universalex.EUExUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
public class RandomKeyBoardMgr {

    private Activity activity;

    private static final String TAG = "AllKeyBoardMgr";

    public boolean isUpper = false;// 是否大写

    public boolean isAnimRunning = false;// 是否正在动画中

    // 默认字母键盘
    // private int keyFlag = 1;

    RelativeLayout relativeLayoutKeyBoard;

    LinearLayout keypad_abc, key_board_sign, keypad_num,
            linearLayoutSignBoardOne, linearLayoutSignBoardTwo;

    private String keypadType;

    private Button btnNum_X, btnNumBoardChangeAbc, btnNumBoardPoint;

    private View bottomfirstview;

    private Button btnSignBoardUp, btnSignBoardDown;

    // 输入长度限制(默认15)
    private int inputMaxLength = 16;
    // 录入文本记录
    public String inputValue = "";
    /**
     * 输入加密
     */
    private String cryptValue = "";
    // private Button[] btnFunctions = new Button[14];
    private Button[] btnNumbs = new Button[10];
    // private Button[] btnAbcs = new Button[26];
    // private Button[] btnSigns = new Button[32];
    // private ImageButton btn = null;

    // 数字按钮
    private int[] keyNumIds = {
            EUExUtil.getResIdID("plugin_randomkeyboard_key_board_num_zero"),
            EUExUtil.getResIdID("plugin_randomkeyboard_key_board_num_one"),
            EUExUtil.getResIdID("plugin_randomkeyboard_key_board_num_two"),
            EUExUtil.getResIdID("plugin_randomkeyboard_key_board_num_three"),
            EUExUtil.getResIdID("plugin_randomkeyboard_key_board_num_four"),
            EUExUtil.getResIdID("plugin_randomkeyboard_key_board_num_five"),
            EUExUtil.getResIdID("plugin_randomkeyboard_key_board_num_six"),
            EUExUtil.getResIdID("plugin_randomkeyboard_key_board_num_seven"),
            EUExUtil.getResIdID("plugin_randomkeyboard_key_board_num_eight"),
            EUExUtil.getResIdID("plugin_randomkeyboard_key_board_num_nine") };

    /**
     * 用来初始化随机键盘管理器
     * 
     * @param mContext
     *            所在的Activity
     * @param edit
     *            密码输入框EditText
     * @param isPassword
     *            是否密文
     * @param keyFlag
     *            1 默认字母 2 默认数字 3 默认符号
     * @param keypadType
     * @param length
     *            最大长度
     * @param inputValue
     *            输入框显示的内容
     * @param encryptValue
     *            输入框加密后的内容（如果是密文）
     */
    public RandomKeyBoardMgr(Context mContext, boolean isPassword, int keyFlag,
            String keypadType, int length, String inputValue,
            String encryptValue) {
        this.activity = (Activity) mContext;
        this.keypadType = keypadType;
        this.inputMaxLength = length;

        this.inputValue = inputValue;
        this.cryptValue = encryptValue;

        // 获取控件对象
        findKeyBoardView();
        // this.keyFlag = keyFlag;
        randomKeyNum();

    }

    /**
     * 初始化随机键盘的view
     */
    private void findKeyBoardView() {
        if (activity.getParent() != null
                && activity.getParent().getParent() != null) {

            bottomfirstview = activity.getParent().getParent()
                    .findViewById(EUExUtil.getResIdID(
                            "plugin_randomkeyboard_bottomfirstview"));
            btnNum_X = (Button) activity.getParent().getParent().findViewById(
                    EUExUtil.getResIdID("plugin_randomkeyboard_btnNum_X"));
            btnNum_X.setOnClickListener(keyBoardInputClickListener);
            btnNumBoardChangeAbc = (Button) activity.getParent().getParent()
                    .findViewById(EUExUtil.getResIdID(
                            "plugin_randomkeyboard_btnNumBoardChangeAbc"));
            btnNumBoardPoint = (Button) activity.getParent().getParent()
                    .findViewById(EUExUtil.getResIdID(
                            "plugin_randomkeyboard_btnNumBoardPoint"));

            relativeLayoutKeyBoard = (RelativeLayout) activity.getParent()
                    .getParent().findViewById(EUExUtil.getResIdID(
                            "plugin_randomkeyboard_linearLayoutKeyBoard"));

            keypad_abc = (LinearLayout) activity.getParent().getParent()
                    .findViewById(EUExUtil
                            .getResIdID("plugin_randomkeyboard_keypad_abc"));

            key_board_sign = (LinearLayout) activity.getParent().getParent()
                    .findViewById(EUExUtil.getResIdID(
                            "plugin_randomkeyboard_key_board_sign"));

            keypad_num = (LinearLayout) activity.getParent().getParent()
                    .findViewById(EUExUtil
                            .getResIdID("plugin_randomkeyboard_keypad_num"));

            btnSignBoardUp = (Button) activity.getParent().getParent()
                    .findViewById(EUExUtil.getResIdID(
                            "plugin_randomkeyboard_btnSignBoardUp"));
            btnSignBoardDown = (Button) activity.getParent().getParent()
                    .findViewById(EUExUtil.getResIdID(
                            "plugin_randomkeyboard_btnSignBoardDown"));
            linearLayoutSignBoardOne = (LinearLayout) activity.getParent()
                    .getParent().findViewById(EUExUtil.getResIdID(
                            "plugin_randomkeyboard_linearLayoutSignBoardOne"));
            linearLayoutSignBoardTwo = (LinearLayout) activity.getParent()
                    .getParent().findViewById(EUExUtil.getResIdID(
                            "plugin_randomkeyboard_linearLayoutSignBoardTwo"));

            // for (int i = 0; i < btnFunctions.length; i++) {
            // btnFunctions[i] = (Button) activity.getParent().getParent()
            // .findViewById(keyFunctionIds[i]);
            // btnFunctions[i]
            // .setOnClickListener(keyBoardFunctionClickListener);
            // }
            btnSignBoardUp.setBackgroundResource(EUExUtil
                    .getResDrawableID("plugin_randomkeyboard_key_up_bga"));
            btnSignBoardUp.setClickable(false);
            btnSignBoardDown.setBackgroundResource(EUExUtil
                    .getResDrawableID("plugin_randomkeyboard_key_down_bga3"));
            btnSignBoardDown.setClickable(true);

            for (int i = 0; i < btnNumbs.length; i++) {
                btnNumbs[i] = (Button) activity.getParent().getParent()
                        .findViewById(keyNumIds[i]);
                if (null != keypadType && keypadType.equals("11")) {
                    btnNumbs[i].setOnClickListener(digitPadClickListener);
                } else {
                    btnNumbs[i].setOnClickListener(keyBoardInputClickListener);
                }
            }

            // for (int i = 0; i < btnAbcs.length; i++) {
            // btnAbcs[i] = (Button) activity.getParent().getParent()
            // .findViewById(keyAbcIds[i]);
            // btnAbcs[i].setOnClickListener(keyBoardInputClickListener);
            // }
            // for (int i = 0; i < btnSigns.length; i++) {
            // btnSigns[i] = (Button) activity.getParent().getParent()
            // .findViewById(keySignIds[i]);
            // btnSigns[i].setOnClickListener(keyBoardInputClickListener);
            // }
        } else {
            bottomfirstview = activity.findViewById(EUExUtil
                    .getResIdID("plugin_randomkeyboard_bottomfirstview"));
            btnNum_X = (Button) activity.findViewById(
                    EUExUtil.getResIdID("plugin_randomkeyboard_btnNum_X"));
            btnNum_X.setOnClickListener(keyBoardInputClickListener);
            btnNumBoardChangeAbc = (Button) activity.findViewById(EUExUtil
                    .getResIdID("plugin_randomkeyboard_btnNumBoardChangeAbc"));
            relativeLayoutKeyBoard = (RelativeLayout) activity
                    .findViewById(EUExUtil.getResIdID(
                            "plugin_randomkeyboard_linearLayoutKeyBoard"));

            keypad_abc = (LinearLayout) activity.findViewById(
                    EUExUtil.getResIdID("plugin_randomkeyboard_keypad_abc"));

            key_board_sign = (LinearLayout) activity.findViewById(EUExUtil
                    .getResIdID("plugin_randomkeyboard_key_board_sign"));

            btnNumBoardPoint = (Button) activity.findViewById(EUExUtil
                    .getResIdID("plugin_randomkeyboard_btnNumBoardPoint"));

            keypad_num = (LinearLayout) activity.findViewById(
                    EUExUtil.getResIdID("plugin_randomkeyboard_keypad_num"));

            linearLayoutSignBoardOne = (LinearLayout) activity
                    .findViewById(EUExUtil.getResIdID(
                            "plugin_randomkeyboard_linearLayoutSignBoardOne"));
            linearLayoutSignBoardTwo = (LinearLayout) activity
                    .findViewById(EUExUtil.getResIdID(
                            "plugin_randomkeyboard_linearLayoutSignBoardTwo"));
            btnSignBoardUp = (Button) activity.findViewById(EUExUtil
                    .getResIdID("plugin_randomkeyboard_btnSignBoardUp"));
            btnSignBoardDown = (Button) activity.findViewById(EUExUtil
                    .getResIdID("plugin_randomkeyboard_btnSignBoardDown"));

            // for (int i = 0; i < btnFunctions.length; i++) {
            // btnFunctions[i] = (Button) activity
            // .findViewById(keyFunctionIds[i]);
            // if (btnFunctions[i] != null) {
            // btnFunctions[i]
            // .setOnClickListener(keyBoardFunctionClickListener);
            // }
            // }
            btnSignBoardUp.setBackgroundResource(EUExUtil
                    .getResDrawableID("plugin_randomkeyboard_key_up_bga"));
            btnSignBoardUp.setClickable(false);
            btnSignBoardDown.setBackgroundResource(EUExUtil
                    .getResDrawableID("plugin_randomkeyboard_key_down_bga3"));
            btnSignBoardDown.setClickable(true);

            for (int i = 0; i < btnNumbs.length; i++) {
                btnNumbs[i] = (Button) activity.findViewById(keyNumIds[i]);
                if (btnNumbs[i] != null) {
                    // 金额键盘的监听
                    if (null != keypadType && keypadType.equals("11")) {
                        btnNumbs[i].setOnClickListener(digitPadClickListener);
                    } else {
                        btnNumbs[i]
                                .setOnClickListener(keyBoardInputClickListener);
                    }
                }
            }

            // for (int i = 0; i < btnAbcs.length; i++) {
            // btnAbcs[i] = (Button) activity.findViewById(keyAbcIds[i]);
            // if (btnAbcs[i] != null) {
            // btnAbcs[i].setOnClickListener(keyBoardInputClickListener);
            // }
            // }
            // for (int i = 0; i < btnSigns.length; i++) {
            // btnSigns[i] = (Button) activity.findViewById(keySignIds[i]);
            // if (btnSigns[i] != null) {
            // btnSigns[i].setOnClickListener(keyBoardInputClickListener);
            // }
            // }
        }
    }

    /**
     * 更新输入的内容
     * 
     * @Description
     * @version 3.0 2014-8-1
     */
    private void setValue(String inputStr) {
        if (inputValue.length() >= inputMaxLength) {
            return;
        }

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
        if (inputValue.length() <= 0) {
            return;
        }
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

    // 金额键盘的监听
    private OnClickListener digitPadClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Button inputBtn = (Button) v;
            // 拼接录入值
            setValue(inputBtn.getText().toString());
        }
    };

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
                    .getResIdID("plugin_randomkeyboard_btn_num_del")) {
                delValue();
            } else if (v.getId() == EUExUtil
                    .getResIdID("plugin_randomkeyboard_btn_num_done")) {
            }
        }
    };

    /**
     * 显示键盘视图
     */
    public void showKeyboard() {
        if (isAnimRunning) {
            Log.i(TAG, "Animation is Running, cannot run any operations!");
            return;
        }
        randomKeyNum();
        // if (keyFlag == 1) {
        // randomKeyAbc();
        // } else if (keyFlag == 2) {
        // } else if (keyFlag == 3) {
        // randomKeySign();
        // } else {
        // randomKeyAbc();
        // }
        int visibility = relativeLayoutKeyBoard.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            // TODO 读取动画id的方法
            // Animation animation = AnimationUtils.loadAnimation(activity,
            // EUExUtil.getResAnimID("plugin_randomkeyboard_in"));
            Animation animation = AnimationUtils.loadAnimation(activity,
                    EUExUtil.getResAnimID(
                            "plugin_randomkeyboard_dialog_enter"));
            animation.setAnimationListener(new AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                    Log.i(TAG, "onAnimationStart");
                    isAnimRunning = true;
                    relativeLayoutKeyBoard.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Log.i(TAG, "onAnimationEnd");
                    int height = relativeLayoutKeyBoard.getHeight();
                    Log.i(TAG, "keyboard height" + height);
                    // EUExRandomKeyboard.getPluginInstance()
                    // .onShowKeyboard(height);
                    isAnimRunning = false;
                }
            });

            relativeLayoutKeyBoard.startAnimation(animation);
        }
    }

    /**
     * 隐藏键盘视图
     */
    public void hideKeyboard() {
        if (isAnimRunning) {
            Log.i(TAG, "Animation is Running, cannot run any operations!");
            return;
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int visibility = relativeLayoutKeyBoard.getVisibility();
                if (visibility == View.VISIBLE) {
                    Animation animation = AnimationUtils.loadAnimation(activity,
                            EUExUtil.getResAnimID(
                                    "plugin_randomkeyboard_dialog_exit"));
                    animation.setAnimationListener(new AnimationListener() {

                        @Override
                        public void onAnimationStart(Animation animation) {
                            isAnimRunning = true;
                            int height = relativeLayoutKeyBoard.getHeight();
                            Log.i(TAG, height + "");
                            // EUExRandomKeyboard.getPluginInstance()
                            // .onHideKeyboard(height);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            isAnimRunning = false;
                            relativeLayoutKeyBoard.setVisibility(View.GONE);
                            String result = getInputContent();
                            if (result == null) {
                                result = "";
                            }
                            Intent intent = new Intent();
                            intent.putExtra("content", result);
                            activity.setResult(0, intent);
                            activity.finish();
                        }
                    });

                    relativeLayoutKeyBoard.startAnimation(animation);
                }
            }
        });
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
        if (null != relativeLayoutKeyBoard) {
            int visibility = relativeLayoutKeyBoard.getVisibility();
            if (visibility == View.VISIBLE) {
                return true;
            }
        }
        return false;
    }

    /**
     * 加密和随机数字键盘
     * 
     * @Description
     * @author 孙靖
     * @version 1.0 2013年12月30日
     */
    private void randomKeyNum() {

        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // 非密码 不随机
                // if (!isPwd) {
                // if (activity.getParent() != null
                // && activity.getParent().getParent() != null) {
                // for (int i = 0; i < btnNumbs.length; i++) {
                // btnNumbs[i] = (Button) activity.getParent()
                // .getParent().findViewById(keyNumIds[i]);
                // btnNumbs[i].setText(i + "");
                // if (null != keypadType && keypadType.equals("11")) {
                // btnNumbs[i]
                // .setOnClickListener(digitPadClickListener);
                // } else {
                // btnNumbs[i]
                // .setOnClickListener(keyBoardInputClickListener);
                // }
                // }
                // } else {
                // for (int i = 0; i < btnNumbs.length; i++) {
                // btnNumbs[i] = (Button) activity
                // .findViewById(keyNumIds[i]);
                // btnNumbs[i].setText(i + "");
                // if (btnNumbs[i] != null) {
                // // 金额键盘的监听
                // if (null != keypadType
                // && keypadType.equals("11")) {
                // btnNumbs[i]
                // .setOnClickListener(digitPadClickListener);
                // } else {
                // btnNumbs[i]
                // .setOnClickListener(keyBoardInputClickListener);
                // }
                // }
                // }
                // }
                //
                // if (null != keypadType && keypadType.equals("9")) {
                // showBoardByType(9);
                // } else if (null != keypadType && keypadType.equals("10")) {
                // showBoardByType(10);
                // } else if (null != keypadType && keypadType.equals("1")) {
                // showBoardByType(9);
                // } else if (null != keypadType && keypadType.equals("7")) {
                // showBoardByType(7);
                // } else if (null != keypadType && keypadType.equals("11")) {
                // showBoardByType(11);
                // } else {
                // // 缺少加密方法
                // showBoardByType(0);
                // }
                // return;
                // } else {
                // 更新界面
                Random ran = new Random();
                java.util.List<Integer> list = new java.util.ArrayList<Integer>();
                while (list.size() < 10) {
                    int n = ran.nextInt(10);
                    if (!list.contains(n))
                        list.add(n);// 如果n不包涵在list中，才添加
                }
                for (int i = 0; i < keyNumIds.length; i++) {
                    if (activity.getParent() != null
                            && activity.getParent().getParent() != null) {
                        btnNumbs[i] = (Button) activity.getParent().getParent()
                                .findViewById(keyNumIds[i]);
                    } else {
                        btnNumbs[i] = (Button) activity
                                .findViewById(keyNumIds[i]);
                    }

                    btnNumbs[i].setText(list.get(i) + "");
                    if (null != keypadType && keypadType.equals("11")) {
                        btnNumbs[i].setOnClickListener(digitPadClickListener);
                    } else {
                        btnNumbs[i]
                                .setOnClickListener(keyBoardInputClickListener);
                    }
                }
                if (null != keypadType && keypadType.equals("2")) {
                    showBoardByType(2);
                } else {
                    showBoardByType(0);
                }
                // }
            }
        });
    }
    //
    // /**
    // *
    // * @Description
    // * @author 孙靖
    // * @version 1.0 2013年12月30日
    // */
    // private void randomKeyAbc() {
    // activity.runOnUiThread(new Runnable() {
    //
    // @Override
    // public void run() {
    // if (true) {
    // // 缺少加密方法
    // showBoardByType(1);
    // return;
    // }
    // // 随机暂不需要完成
    // }
    // });
    // }

    // /**
    // * 随机加密符号键盘
    // *
    // * @Description
    // * @author 孙靖
    // * @version 1.0 2013年12月30日
    // */
    // private void randomKeySign() {
    // activity.runOnUiThread(new Runnable() {
    // @Override
    // public void run() {
    // if (true) {
    // // 缺少加密方法
    // showBoardByType(3);
    // return;
    // }
    // // 随机暂不需要完成
    // }
    // });
    // }

    /**
     * 根据参数显示某种键盘
     * 
     * @Description
     * @param flag
     *            1=abc 2=数字 3=符号
     */
    private void showBoardByType(int flag) {
        keypad_abc.setVisibility(View.GONE);
        key_board_sign.setVisibility(View.GONE);
        keypad_num.setVisibility(View.GONE);
        if (flag == 0) {
            keypad_num.setVisibility(View.VISIBLE);
            bottomfirstview.setVisibility(View.VISIBLE);
            btnNumBoardPoint.setVisibility(View.VISIBLE);
            btnNum_X.setVisibility(View.GONE);
            btnNumBoardChangeAbc.setVisibility(View.VISIBLE);
        } else if (flag == 1) {
            keypad_abc.setVisibility(View.VISIBLE);
        } else if (flag == 2) {
            keypad_num.setVisibility(View.VISIBLE);
            bottomfirstview.setVisibility(View.GONE);
            btnNumBoardPoint.setVisibility(View.GONE);
            btnNum_X.setVisibility(View.VISIBLE);
            btnNumBoardChangeAbc.setVisibility(View.GONE);
            btnNum_X.setClickable(true);
            btnNum_X.setText("清 空");
            btnNum_X.setTextColor(activity.getResources()
                    .getColor(EUExUtil.getResColorID("white")));
            btnNum_X.setBackgroundResource(EUExUtil.getResDrawableID(
                    "plugin_randomkeyboard_key_changenum_bg_select"));
            btnNum_X.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    clear();
                }
            });
        } else if (flag == 3) {
            key_board_sign.setVisibility(View.VISIBLE);
        } else if (flag == 9 || flag == 7) {
            keypad_num.setVisibility(View.VISIBLE);
            bottomfirstview.setVisibility(View.GONE);
            btnNumBoardPoint.setVisibility(View.GONE);
            btnNum_X.setVisibility(View.VISIBLE);
            btnNum_X.setText("清 空");
            btnNum_X.setTextColor(activity.getResources()
                    .getColor(EUExUtil.getResColorID("white")));
            btnNum_X.setClickable(true);
            btnNum_X.setBackgroundResource(EUExUtil.getResDrawableID(
                    "plugin_randomkeyboard_key_changenum_bg_select"));
            btnNum_X.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    clear();
                }
            });
            btnNumBoardChangeAbc.setVisibility(View.GONE);
        } else if (flag == 11) {
            keypad_num.setVisibility(View.VISIBLE);
            bottomfirstview.setVisibility(View.GONE);
            btnNumBoardPoint.setVisibility(View.GONE);
            btnNumBoardChangeAbc.setVisibility(View.GONE);
            btnNum_X.setVisibility(View.VISIBLE);
            btnNum_X.setText(".");
            btnNum_X.setTextColor(activity.getResources()
                    .getColor(EUExUtil.getResColorID("black")));
            btnNum_X.setClickable(true);
            btnNum_X.setBackgroundResource(EUExUtil
                    .getResDrawableID("plugin_randomkeyboard_key_bg_select"));
            btnNum_X.setOnClickListener(digitPadClickListener);
        } else {
            keypad_abc.setVisibility(View.VISIBLE);
        }
    }
}