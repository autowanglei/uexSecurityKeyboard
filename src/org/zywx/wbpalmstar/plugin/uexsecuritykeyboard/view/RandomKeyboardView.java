package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.view;

import org.zywx.wbpalmstar.engine.universalex.EUExUtil;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.EUExSecurityKeyboard;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.InputStatusListener;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.keyboardMgr.KeyboardBaseMgr.KeyboardStatusListener;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.keyboardMgr.RandomKeyBoardMgr;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo.OpenDataVO;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

public class RandomKeyboardView extends BaseFrameLayout {

    public static final String TAG = "RandomKeyboardActivity";
    private RandomKeyBoardMgr mRandomKeyBoardMgr;

    public RandomKeyboardView(Context context, EUExSecurityKeyboard mKeyboard,
            InputStatusListener mInputStatusListener,
            RelativeLayout.LayoutParams lp, OpenDataVO dataVO) {
        super(context);
        initView(context, mKeyboard, mInputStatusListener, lp, dataVO);
    }

    private void initView(Context context, EUExSecurityKeyboard mEUExKeyboard,
            InputStatusListener mInputStatusListener,
            RelativeLayout.LayoutParams inputEditLp, OpenDataVO dataVO) {
        RelativeLayout keyboardViewParent = setContentView(context, this,
                EUExUtil.getResLayoutID(
                        "plugin_uexsecuritykeyboard_keyboard_layout_random_num"));
        mRandomKeyBoardMgr = new RandomKeyBoardMgr(context, mEUExKeyboard,
                keyboardViewParent, inputEditText, mInputStatusListener,
                new KeyboardStatusListener() {
                    @Override
                    public void onKeyboardShow(Context context,
                            View keyboardView) {
                        mRandomKeyBoardMgr.randomKeyNum(context, keyboardView);
                    }
                }, dataVO);
        createKeyboard(context, mEUExKeyboard, this, mRandomKeyBoardMgr, dataVO,
                inputEditLp);
    }
}
