package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.view;

import org.zywx.wbpalmstar.engine.universalex.EUExUtil;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.EUExSecurityKeyboard;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.EUExSecurityKeyboard.OnInputStatusListener;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.HandleKeyboard;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.util.ConstantUtil;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo.OpenDataVO;

import android.app.Activity;
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

public class SecKeyboardView extends FrameLayout {

    private Context mContext;
    private HandleKeyboard mKey;
    private EditText inputEditText;
    private TextView mDescription;

    public SecKeyboardView(Context context) {
        super(context);
    }

    public SecKeyboardView(Context context, EUExSecurityKeyboard mKeyboard,
            RelativeLayout.LayoutParams lp, OpenDataVO dataVO) {
        super(context);
        this.mContext = context;
        initView(context, mKeyboard, lp, dataVO);
    }

    private void initView(Context context, EUExSecurityKeyboard mKeyboard,
            RelativeLayout.LayoutParams lp, OpenDataVO dataVO) {
        LayoutInflater.from(mContext).inflate(
                EUExUtil.getResLayoutID(
                        "plugin_uexsecuritykeyboard_keyboard_layout"),
                this, true);
        RelativeLayout view = (RelativeLayout) this
                .findViewById(EUExUtil.getResIdID("parentView"));
        inputEditText = (EditText) this
                .findViewById(EUExUtil.getResIdID("password_edit"));
        mDescription = (TextView) this
                .findViewById(EUExUtil.getResIdID("keyboard_description"));
        inputEditText.setLayoutParams(lp);
        mKey = new HandleKeyboard((Activity) context, mKeyboard, inputEditText,
                view, dataVO);
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
            mKey.showKeyboard();
        }
    }

    public void setOnInputStatusListener(OnInputStatusListener listener) {
        if (mKey != null) {
            mKey.setOnInputStatusListener(listener);
        }
    }

    public EditText getInputEditText() {
        return inputEditText;
    }

    public void setDescription(String description) {
        if (mDescription != null) {
            mDescription.setText(description);
        }
    }
}
