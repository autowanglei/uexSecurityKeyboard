package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.view;

import org.zywx.wbpalmstar.engine.universalex.EUExUtil;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.EUExSecurityKeyboard;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.keyboardMgr.KeyboardBaseMgr;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.util.ConstantUtil;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo.OpenDataVO;

import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BaseFrameLayout extends FrameLayout {
    protected EditText inputEditText;

    public BaseFrameLayout(Context context) {
        super(context);
    }

    protected RelativeLayout setContentView(Context context, View baseView,
            int keyboardParentLayoutId) {
        LayoutInflater.from(context).inflate(keyboardParentLayoutId, this,
                true);
        initEditText();
        return (RelativeLayout) this
                .findViewById(EUExUtil.getResIdID("keyboard_view_parent"));
    }

    private void initEditText() {
        inputEditText = (EditText) this
                .findViewById(EUExUtil.getResIdID("password_edit"));
    }

    protected void createKeyboard(Context context,
            EUExSecurityKeyboard mEUExKeyboard, View keyboardView,
            KeyboardBaseMgr keyboardMgr,
            OpenDataVO dataVO, RelativeLayout.LayoutParams inputEditLp) {
        setKeyboardDescription(keyboardView, dataVO.getKeyboardDescription());
        configInputEditText(context, mEUExKeyboard, keyboardView, keyboardMgr,
                dataVO, inputEditLp);
    }

    private void setKeyboardDescription(View keyboardView, String description) {
        TextView mDescription = (TextView) keyboardView
                .findViewById(EUExUtil.getResIdID("keyboard_description"));
        if (!TextUtils.isEmpty(description)) {
            mDescription.setText(description);
        }
    }

    private void configInputEditText(Context context,
            EUExSecurityKeyboard mEUExKeyboard, View keyboardView,
            KeyboardBaseMgr keyboardMgr, OpenDataVO dataVO,
            RelativeLayout.LayoutParams inputEditLp) {
        inputEditText.setLayoutParams(inputEditLp);
        if (dataVO.isShowInputBox()) {
            if (!dataVO.isShowClearText()) {
                inputEditText.setInputType(InputType.TYPE_CLASS_TEXT
                        | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            if (!TextUtils.isEmpty(dataVO.getBackgroundColor())) {
                inputEditText.setBackgroundColor(
                        Color.parseColor(dataVO.getBackgroundColor()));
            }
            if (!TextUtils.isEmpty(dataVO.getTextColor())) {
                inputEditText.setBackgroundColor(
                        Color.parseColor(dataVO.getTextColor()));
            }
            if (ConstantUtil.DEF_INPUT_TEXT_SIZE != dataVO.getTextSize()) {
                inputEditText.setTextSize(dataVO.getTextSize());
            }
            if (!TextUtils.isEmpty(dataVO.getHintTextColor())) {
                inputEditText.setHintTextColor(
                        Color.parseColor(dataVO.getHintTextColor()));
            }
            if (!TextUtils.isEmpty(dataVO.getHintText())) {
                inputEditText.setHint(dataVO.getHintText());
            }
        } else {
            inputEditText.setVisibility(View.INVISIBLE);
            keyboardMgr.showKeyboard(context, mEUExKeyboard, keyboardView,
                    dataVO.getId());
        }
    }

    public EditText getInputEditText() {
        return inputEditText;
    }

}
