package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.keyboardMgr;

import java.util.List;

import org.zywx.wbpalmstar.engine.universalex.EUExUtil;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.EUExSecurityKeyboard;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.InputStatusListener;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.util.ConstantUtil;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo.OpenDataVO;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SecKeyboardMgr extends KeyboardBaseMgr
        implements View.OnClickListener {

    private Context mContext;
    public boolean isUpper = false;// 是否大写
    private KeyboardView mKeyboardView;
    private Keyboard letters;// 字母键盘
    private Keyboard numbers;// 数字键盘
    private Keyboard symbols;// 符号键盘
    private Keyboard onlyNumbers;// 纯数字键盘
    private TextView mDone;// 完成

    public SecKeyboardMgr(final Context context,
            final EUExSecurityKeyboard mEUExKeyboard,
            final RelativeLayout mParentView, final EditText editText,
            InputStatusListener mInputStatusListener, OpenDataVO dataVO) {
        super(context, mEUExKeyboard, mParentView, editText,
                mInputStatusListener, dataVO);
        this.mContext = context;
        this.mEUExKeyboard = mEUExKeyboard;
        this.dataVO = dataVO;
        this.mKeyboardView = (KeyboardView) mParentView
                .findViewById(EUExUtil.getResIdID("keyboard_view"));
        this.mDone = (TextView) mParentView
                .findViewById(EUExUtil.getResIdID("done"));

        mDone.setOnClickListener(this);
        switch (dataVO.getKeyboardType()) {
        case ConstantUtil.KEYBOARD_MODE_CUSTOM:
            isCustom = true;
            letters = new Keyboard(mContext,
                    EUExUtil.getResXmlID("plugin_uexsecuritykeyboard_letters"));
            numbers = new Keyboard(mContext,
                    EUExUtil.getResXmlID("plugin_uexsecuritykeyboard_numbers"));
            symbols = new Keyboard(mContext,
                    EUExUtil.getResXmlID("plugin_uexsecuritykeyboard_symbols"));
            mKeyboardView.setKeyboard(letters);
            mDone.setVisibility(View.VISIBLE);
            break;
        case ConstantUtil.KEYBOARD_MODE_NUMBER:
            isCustom = true;
            onlyNumbers = new Keyboard(mContext, EUExUtil
                    .getResXmlID("plugin_uexsecuritykeyboard_only_numbers"));
            mKeyboardView.setKeyboard(onlyNumbers);
            mDone.setVisibility(View.GONE);
            break;
        case ConstantUtil.KEYBOARD_MODE_DEFAULT:
            isCustom = false;
            break;
        }
        mKeyboardView.setEnabled(true);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setOnKeyboardActionListener(listener);
        setEditTextListener(context, mEUExKeyboard, mParentView,
                dataVO.getId());
    }

    private OnKeyboardActionListener listener = new OnKeyboardActionListener() {
        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onPress(int primaryCode) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            // Editable editable = mEditText.getText();
            // int start = mEditText.getSelectionStart();
            if (primaryCode == Keyboard.KEYCODE_DONE) {
                onKeyDonePress(mEUExKeyboard, keyboardViewParent);
            } else if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                delValue();
                // if (editable != null && editable.length() > 0) {
                // if (start > 0) {
                // editable.delete(start - 1, start);
                // }
                // }
                // cbKeyPressToWeb(ConstantUtil.INPUT_TYPE_DEL);
            } else if (primaryCode == Keyboard.KEYCODE_SHIFT) {// 大小写切换
                changeKey();
                mKeyboardView.setKeyboard(letters);

            } else if (primaryCode == ConstantUtil.KEYCODE_CHANGE_NUMBER) {// 数字键盘切换
                mKeyboardView.setKeyboard(numbers);
            } else if (primaryCode == ConstantUtil.KEYCODE_CHANGE_SYMBOL) {// 符号切换
                mKeyboardView.setKeyboard(symbols);
            } else if (primaryCode == ConstantUtil.KEYCODE_CHANGE_LETTER) {// 字母切换
                mKeyboardView.setKeyboard(letters);
            } else {
                insertValue(Character.toString((char) primaryCode));
            }
        }
    };

    /**
     * 键盘大小写切换
     */
    private void changeKey() {
        List<Key> keyList = letters.getKeys();
        if (isUpper) {// 大写切换小写
            isUpper = false;
            for (Key key : keyList) {
                if (key.label != null && isWord(key.label.toString())) {
                    key.label = key.label.toString().toLowerCase();
                    key.codes[0] = key.codes[0] + 32;
                }
                if (key.codes[0] == -1) {
                    key.icon = mContext.getResources()
                            .getDrawable(EUExUtil.getResDrawableID(
                                    "plugin_uexsecuritykeyboard_key_icon_shift_normal"));
                }
            }
        } else {// 小写切换大写
            isUpper = true;
            for (Key key : keyList) {
                if (key.label != null && isWord(key.label.toString())) {
                    key.label = key.label.toString().toUpperCase();
                    key.codes[0] = key.codes[0] - 32;
                }
                if (key.codes[0] == -1) {
                    key.icon = mContext.getResources()
                            .getDrawable(EUExUtil.getResDrawableID(
                                    "plugin_uexsecuritykeyboard_key_icon_shift_highlighted"));
                }
            }
        }
    }

    private boolean isWord(String str) {
        String wordStr = "abcdefghijklmnopqrstuvwxyz";
        if (wordStr.contains(str.toLowerCase())) {
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v == mDone) {
            onKeyDonePress(mEUExKeyboard, keyboardViewParent);
        }
    }
}
