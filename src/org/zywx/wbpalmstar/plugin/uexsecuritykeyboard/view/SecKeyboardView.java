package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.view;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.zywx.wbpalmstar.engine.universalex.EUExUtil;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.EUExSecurityKeyboard.OnInputStatusListener;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.HandleKeyboard;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.JsConst;

public class SecKeyboardView extends FrameLayout {

    private Context mContext;
    private HandleKeyboard mKey;
    private EditText inputEditText;
    private TextView mDescription;

    public SecKeyboardView(Context context) {
        super(context);
    }

    public SecKeyboardView(Context context, RelativeLayout.LayoutParams lp, int mode) {
        super(context);
        this.mContext = context;
        initView(lp, mode);
    }

    private void initView(RelativeLayout.LayoutParams lp, int mode) {
        LayoutInflater.from(mContext).
                inflate(EUExUtil
                        .getResLayoutID("plugin_uexsecuritykeyboard_keyboard_layout"), this, true);
        RelativeLayout view = (RelativeLayout) this.findViewById(EUExUtil.getResIdID("parentView"));
        inputEditText = (EditText) this.findViewById(EUExUtil.getResIdID("password_edit"));
        mDescription = (TextView) this.findViewById(EUExUtil.getResIdID("keyboard_description"));
        inputEditText.setLayoutParams(lp);
        mKey = new HandleKeyboard((Activity) mContext, inputEditText, view, mode);
    }

    public void setOnInputStatusListener(OnInputStatusListener listener){
        if (mKey != null){
            mKey.setOnInputStatusListener(listener);
        }
    }

    public EditText getInputEditText() {
        return inputEditText;
    }

    public void setDescription(String description){
        if (mDescription != null){
            mDescription.setText(description);
        }
    }
}
