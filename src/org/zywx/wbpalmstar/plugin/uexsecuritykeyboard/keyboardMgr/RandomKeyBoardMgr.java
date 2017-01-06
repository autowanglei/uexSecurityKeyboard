package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.keyboardMgr;

import java.util.Random;

import org.zywx.wbpalmstar.base.ResoureFinder;
import org.zywx.wbpalmstar.engine.universalex.EUExUtil;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.EUExSecurityKeyboard;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.InputStatusListener;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo.OpenDataVO;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
public class RandomKeyBoardMgr extends KeyboardBaseMgr {

    private ImageButton btnFunDel;
    private Button btnFunDone;
    private Button[] btnNumbs = new Button[10];

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
            final RelativeLayout mParentView, final EditText editText,
            InputStatusListener mInputStatusListener,
            KeyboardStatusListener listener, OpenDataVO dataVO) {
        super(context, mEUExKeyboard, mParentView, editText,
                mInputStatusListener, listener, dataVO);
        // 获取控件对象
        isCustom = true;
        findKeyBoardView(null, mParentView);
        setEditTextListener(context, mEUExKeyboard, mParentView,
                dataVO.getId());
    }

    /**
     * 初始化随机键盘的view
     * 
     * @param context
     *            TODO
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private void findKeyBoardView(Context context, View keyboardView) {
        View tempView = keyboardView;
        if (keyboardView.getParent() != null
                && keyboardView.getParent().getParent() != null) {
            tempView = (View) keyboardView.getParent().getParent();
        }
        for (int i = 0; i < btnNumbs.length; i++) {
            btnNumbs[i] = (Button) tempView.findViewById(keyNumIds[i]);
            if (btnNumbs[i] != null) {
                btnNumbs[i].setOnClickListener(keyBoardInputClickListener);
            }
        }
        tempView.findViewById(EUExUtil.getResIdID("done"))
                .setVisibility(View.GONE);
        btnFunDel = (ImageButton) tempView.findViewById(
                EUExUtil.getResIdID("plugin_uexsecuritykeyboard_btn_num_del"));
        btnFunDel.setOnClickListener(keyBoardFunctionClickListener);
        btnFunDone = (Button) tempView.findViewById(
                EUExUtil.getResIdID("plugin_uexsecuritykeyboard_btn_num_done"));
        btnFunDone.setOnClickListener(keyBoardFunctionClickListener);
        if (!dataVO.isHighlight()) {
            Drawable drawable = ResoureFinder.getInstance(context)
                    .getDrawable("plugin_uexsecuritykeyboard_kb_btn_normal");
            for (int i = 0; i < btnNumbs.length; i++) {
                btnNumbs[i] = (Button) tempView.findViewById(keyNumIds[i]);
                if (btnNumbs[i] != null) {
                    btnNumbs[i].setBackground(drawable);
                }
            }
            btnFunDel.setBackground(drawable);
            btnFunDone.setBackground(drawable);
        }
    }

    // 输入监听
    private OnClickListener keyBoardInputClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Button inputBtn = (Button) v;
            // 拼接录入值
            insertValue(inputBtn.getText().toString());
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
                onKeyDonePress(mEUExKeyboard, keyboardViewParent);
            }
        }
    };

    /**
     * 显示键盘视图
     */
    public void showKeyboard(Context context,
            EUExSecurityKeyboard mEUExKeyboard, View keyboardView) {
        randomKeyNum(context, keyboardView);
        keyboardView.setVisibility(View.VISIBLE);
    }

    /**
     * 加密和随机数字键盘
     * 
     * @Description
     * @author 孙靖
     * @version 1.0 2013年12月30日
     */
    public void randomKeyNum(Context context, final View keyboardView) {
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
                        btnNumbs[i] = (Button) keyboardView
                                .findViewById(keyNumIds[i]);
                    }
                    btnNumbs[i].setText(list.get(i) + "");
                    btnNumbs[i].setOnClickListener(keyBoardInputClickListener);
                }
            }
        });
    }

}