package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.view;

import org.zywx.wbpalmstar.engine.universalex.EUExUtil;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.EUExSecurityKeyboard;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.InputStatusListener;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.OnInputStatusListener;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.keyboardMgr.SecKeyboardMgr;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo.OpenDataVO;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

public class SecKeyboardView extends BaseFrameLayout {

    private SecKeyboardMgr mKey;

    public SecKeyboardView(Context context) {
        super(context);
    }

    public SecKeyboardView(Context context, EUExSecurityKeyboard mEUExKeyboard,
            InputStatusListener mInputStatusListener,
            RelativeLayout.LayoutParams inputEditLp, OpenDataVO dataVO) {
        super(context);
        this.setOnInputStatusListener(mInputStatusListener);
        initView(context, mEUExKeyboard, inputEditLp, dataVO);
    }

    private void initView(Context context, EUExSecurityKeyboard mEUExKeyboard,
            RelativeLayout.LayoutParams inputEditLp, OpenDataVO dataVO) {
        String resLayoutId = "plugin_uexsecuritykeyboard_keyboard_layout";
        if (!dataVO.isHighlight()) {
            resLayoutId = "plugin_uexsecuritykeyboard_keyboard_layout_no_click_effect";
        }
        LayoutInflater.from(context)
                .inflate(EUExUtil.getResLayoutID(resLayoutId), this, true);
        RelativeLayout keyboardRl = (RelativeLayout) this
                .findViewById(EUExUtil.getResIdID("parentView"));
        initEditText();
        mKey = new SecKeyboardMgr((Activity) context, mEUExKeyboard, inputEditText,
                keyboardRl, dataVO);
        initBaseView(context, mEUExKeyboard, this, mKey, dataVO, inputEditLp);

    }

    public void setOnInputStatusListener(OnInputStatusListener listener) {
        if (mKey != null) {
            mKey.setOnInputStatusListener(listener);
        }
    }
}
