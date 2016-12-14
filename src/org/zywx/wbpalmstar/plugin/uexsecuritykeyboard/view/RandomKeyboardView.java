package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.view;

import org.zywx.wbpalmstar.engine.universalex.EUExUtil;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.EUExSecurityKeyboard;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.InputStatusListener;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.keyboardMgr.RandomKeyBoardMgr;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo.OpenDataVO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

public class RandomKeyboardView extends BaseFrameLayout {

    public static final String TAG = "RandomKeyboardActivity";
    private RandomKeyBoardMgr mRandomKeyBoardMgr;

    public RandomKeyboardView(Context context, EUExSecurityKeyboard mKeyboard,
            InputStatusListener mInputStatusListener,
            RelativeLayout.LayoutParams lp, OpenDataVO dataVO) {
        super(context);
        initView(context, mKeyboard, lp, dataVO);
    }

    private void initView(Context context, EUExSecurityKeyboard mEUExKeyboard,
            RelativeLayout.LayoutParams inputEditLp, OpenDataVO dataVO) {
        LayoutInflater.from(context).inflate(
                EUExUtil.getResLayoutID(
                        "plugin_uexsecuritykeyboard_keyboard_layout_random_num"),
                this, true);
        initEditText();
        mRandomKeyBoardMgr = new RandomKeyBoardMgr(context, mEUExKeyboard, this,
                inputEditText, dataVO);
        initBaseView(context, mEUExKeyboard, this, mRandomKeyBoardMgr, dataVO,
                inputEditLp);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRandomKeyBoardMgr != null) {
                    mRandomKeyBoardMgr.hideKeyboard();
                }
            }
        });
    }
}
