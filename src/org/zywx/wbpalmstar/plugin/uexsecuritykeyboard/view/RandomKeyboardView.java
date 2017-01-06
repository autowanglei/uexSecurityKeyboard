package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.view;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import org.zywx.wbpalmstar.engine.universalex.EUExUtil;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.EUExSecurityKeyboard;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.InputStatusListener;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.keyboardMgr.KeyboardBaseMgr.KeyboardStatusListener;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.keyboardMgr.RandomKeyBoardMgr;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo.OpenDataVO;

public class RandomKeyboardView extends KeyboardBaseView {

    public static final String TAG = "RandomKeyboardActivity";

    public RandomKeyboardView(Context context, EUExSecurityKeyboard mEUExKeyboard,
                              InputStatusListener mInputStatusListener, RelativeLayout.LayoutParams lp,
                              OpenDataVO dataVO) {
        super(context, mEUExKeyboard);
        initView(context, mEUExKeyboard, mInputStatusListener, lp, dataVO);
    }

    private void initView(Context context, EUExSecurityKeyboard mEUExKeyboard,
                          InputStatusListener mInputStatusListener,
                          RelativeLayout.LayoutParams inputEditLp, OpenDataVO dataVO) {
        RelativeLayout keyboardViewParent = setContentView(context, this,
                EUExUtil.getResLayoutID(
                        "plugin_uexsecuritykeyboard_keyboard_layout_random_num"));
        mKeyboardBaseMgr = new RandomKeyBoardMgr(context, mEUExKeyboard,
                keyboardViewParent, inputEditText, mInputStatusListener,
                new KeyboardStatusListener() {
                    @Override
                    public void onKeyboardShow(Context context,
                                               View keyboardView) {
                        ((RandomKeyBoardMgr) mKeyboardBaseMgr)
                                .randomKeyNum(context, keyboardView);
                    }
                }, dataVO);
        createKeyboard(context, mEUExKeyboard, keyboardViewParent,
                mKeyboardBaseMgr, dataVO, inputEditLp);
    }
}
