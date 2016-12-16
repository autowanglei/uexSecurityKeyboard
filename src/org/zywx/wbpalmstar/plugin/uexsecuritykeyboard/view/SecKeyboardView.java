package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.view;

import org.zywx.wbpalmstar.engine.universalex.EUExUtil;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.EUExSecurityKeyboard;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.InputStatusListener;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.keyboardMgr.SecKeyboardMgr;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo.OpenDataVO;

import android.content.Context;
import android.widget.RelativeLayout;

public class SecKeyboardView extends KeyboardBaseView {

    public SecKeyboardView(Context context, EUExSecurityKeyboard mEUExKeyboard,
            InputStatusListener mInputStatusListener,
            RelativeLayout.LayoutParams inputEditLp, OpenDataVO dataVO) {
        super(context, mEUExKeyboard);
        onCreate(context, mEUExKeyboard, mInputStatusListener, inputEditLp,
                dataVO);
    }

    private void onCreate(Context context, EUExSecurityKeyboard mEUExKeyboard,
            InputStatusListener mInputStatusListener,
            RelativeLayout.LayoutParams inputEditLp, OpenDataVO dataVO) {
        String resLayoutId = "plugin_uexsecuritykeyboard_keyboard_layout";
        if (!dataVO.isHighlight()) {
            resLayoutId = "plugin_uexsecuritykeyboard_keyboard_layout_no_click_effect";
        }

        RelativeLayout keyboardViewParent = setContentView(context, this,
                EUExUtil.getResLayoutID(resLayoutId));
        mKeyboardBaseMgr = new SecKeyboardMgr(context, mEUExKeyboard,
                keyboardViewParent, inputEditText, mInputStatusListener,
                dataVO);
        createKeyboard(context, mEUExKeyboard, keyboardViewParent,
                mKeyboardBaseMgr, dataVO, inputEditLp);

    }

}
