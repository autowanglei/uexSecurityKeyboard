package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard;

import java.util.List;

import org.zywx.wbpalmstar.engine.universalex.EUExUtil;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.EUExSecurityKeyboard.OnInputStatusListener;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.util.ConstantUtil;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo.OpenDataVO;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo.ResultVO;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HandleKeyboard implements View.OnClickListener{

    private Activity mActivity;

    public boolean isUpper = false;// 是否大写

    private InputMethodManager mImm;
    private KeyboardView mKeyboardView;
    private Keyboard letters;// 字母键盘
    private Keyboard numbers;// 数字键盘
    private Keyboard symbols;//符号键盘

    private Keyboard onlyNumbers;//纯数字键盘

    private TextView mDone;//完成

    private EditText ed;
    private RelativeLayout mParentView;

    private OnInputStatusListener mListener;

    private boolean isCustom;
    private EUExSecurityKeyboard mEUExKeyboard;
    private OpenDataVO dataVO;

    public HandleKeyboard(Activity act, EUExSecurityKeyboard mKeyboard,
            final EditText editText, RelativeLayout view, OpenDataVO dataVO) {

        this.mActivity = act;
        this.mEUExKeyboard = mKeyboard;
        this.ed = editText;
        this.mParentView = view;
        this.dataVO = dataVO;
        this.mKeyboardView = (KeyboardView) view.findViewById(EUExUtil.getResIdID("keyboard_view"));
        this.mDone = (TextView)view.findViewById(EUExUtil.getResIdID("done"));

        mDone.setOnClickListener(this);

        mImm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);

        switch (dataVO.getKeyboardType()) {
            case JsConst.KEYBOARD_MODE_CUSTOM:
                isCustom = true;
                letters = new Keyboard(mActivity, EUExUtil
                        .getResXmlID("plugin_uexsecuritykeyboard_letters"));
                numbers = new Keyboard(mActivity, EUExUtil
                        .getResXmlID("plugin_uexsecuritykeyboard_numbers"));
                symbols = new Keyboard(mActivity, EUExUtil
                        .getResXmlID("plugin_uexsecuritykeyboard_symbols"));
                mKeyboardView.setKeyboard(letters);
                mDone.setVisibility(View.VISIBLE);
                break;
            case JsConst.KEYBOARD_MODE_NUMBER:
                isCustom = true;
                onlyNumbers = new Keyboard(mActivity, EUExUtil
                        .getResXmlID("plugin_uexsecuritykeyboard_only_numbers"));
                mKeyboardView.setKeyboard(onlyNumbers);
                mDone.setVisibility(View.GONE);
                break;
            case JsConst.KEYBOARD_MODE_DEFAULT:
                isCustom = false;
                break;
        }
        mKeyboardView.setEnabled(true);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setOnKeyboardActionListener(listener);

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b && isCustom) {
                    hideKeyboard(false);
                } else {
                    mImm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                }
            }
        });

        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isCustom){
                    int inputType = editText.getInputType();
                    hideSoftInputMethod(editText);
                    showKeyboard();
                    editText.setInputType(inputType);
                }
                return false;
            }
        });
    }

    public void setOnInputStatusListener(OnInputStatusListener listener) {
        this.mListener = listener;
    }

    /**
     * 隐藏系统键盘 Edittext不显示系统键盘；并且要有光标； 4.0以上TYPE_NULL，不显示系统键盘，但是光标也没了；
     */
    private void hideSoftInputMethod(EditText et) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        String methodName = null;
        if (currentVersion >= 16) {
            // 4.2
            methodName = "setShowSoftInputOnFocus";
            // 19 setShowSoftInputOnFocus
        } else if (currentVersion >= 14) {
            // 4.0
            methodName = "setSoftInputShownOnFocus";
        }

        if (methodName == null) {
            et.setInputType(InputType.TYPE_NULL);
        } else {
            Class<TextView> cls = TextView.class;
            java.lang.reflect.Method setShowSoftInputOnFocus;
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName, boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(this, false);
            } catch (Exception e) {
                et.setInputType(InputType.TYPE_NULL);
                e.printStackTrace();
            }
        }
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
            Editable editable = ed.getText();
            int start = ed.getSelectionStart();
            if (primaryCode == Keyboard.KEYCODE_DONE){
                onKeyDonePress();
            }else if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
                cbKeyPressToWeb(ConstantUtil.INPUT_TYPE_DEL);
            } else if (primaryCode == Keyboard.KEYCODE_SHIFT) {// 大小写切换
                changeKey();
                mKeyboardView.setKeyboard(letters);

            } else if (primaryCode == JsConst.KEYCODE_CHANGE_NUMBER) {// 数字键盘切换
                mKeyboardView.setKeyboard(numbers);
            } else if (primaryCode == JsConst.KEYCODE_CHANGE_SYMBOL) {// 符号切换
                mKeyboardView.setKeyboard(symbols);
            }else if (primaryCode == JsConst.KEYCODE_CHANGE_LETTER) {// 字母切换
                mKeyboardView.setKeyboard(letters);
            } else {
                editable.insert(start, Character.toString((char) primaryCode));
                cbKeyPressToWeb(ConstantUtil.INPUT_TYPE_TEXT);
            }
        }
    };

    /**
     * 键盘大小写切换
     */
    private void changeKey() {
        List<Key> keyList = letters.getKeys();
        if (isUpper) {//大写切换小写
            isUpper = false;
            for (Key key : keyList) {
                if (key.label != null && isWord(key.label.toString())) {
                    key.label = key.label.toString().toLowerCase();
                    key.codes[0] = key.codes[0] + 32;
                }
                if (key.codes[0] == -1){
                    key.icon = mActivity.getResources().getDrawable(EUExUtil
                            .getResDrawableID("plugin_uexsecuritykeyboard_key_icon_shift_normal"));
                }
            }
        } else {//小写切换大写
            isUpper = true;
            for (Key key : keyList) {
                if (key.label != null && isWord(key.label.toString())) {
                    key.label = key.label.toString().toUpperCase();
                    key.codes[0] = key.codes[0] - 32;
                }
                if (key.codes[0] == -1){
                    key.icon = mActivity.getResources().getDrawable(EUExUtil
                            .getResDrawableID("plugin_uexsecuritykeyboard_key_icon_shift_highlighted"));
                }
            }
        }
    }

    public void showKeyboard() {
        int visibility = mParentView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            mParentView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * INPUT_TYPE_DONE，输入完成时，回调处理
     */
    private void onKeyDonePress() {
        hideKeyboard(true);
        if (!dataVO.isShowInputBox() && mEUExKeyboard != null) {
            mEUExKeyboard.onKeyPress(ConstantUtil.INPUT_TYPE_DONE);
        }
    }

    /**
     * INPUT_TYPE_TEXT = 0; INPUT_TYPE_DEL = 1时，回调处理
     * 
     * @param inputType
     */
    private void cbKeyPressToWeb(int inputType) {
        if (!dataVO.isShowInputBox() && mEUExKeyboard != null) {
            mEUExKeyboard.onKeyPress(inputType);
        }
    }

    public void hideKeyboard(boolean isDone) {
        int visibility = mParentView.getVisibility();
        if (visibility == View.VISIBLE) {
            ResultVO resultVO = new ResultVO();
            resultVO.setContent(ed.getText().toString());
            if (mListener != null){
                if (isDone){
                    mListener.onInputCompleted(resultVO);
                }else{
                    mListener.onKeyboardDismiss(resultVO);
                }
            }
            mParentView.setVisibility(View.INVISIBLE);
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
        if (v == mDone){
            onKeyDonePress();
        }
    }
}
