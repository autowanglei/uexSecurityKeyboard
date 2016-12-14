package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.keyboardMgr;

import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.EUExSecurityKeyboard;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.InputStatusListener;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.util.ConstantUtil;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo.ResultVO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class KeyboardBaseMgr {

    protected RelativeLayout keyboardViewParent;
    protected EditText mEditText;
    protected boolean isCustom;
    protected InputMethodManager mImm;
    protected InputStatusListener mInputStatusListener;
    protected KeyboardStatusListener mKeyboardStatusListener = null;

    public KeyboardBaseMgr(Context context, RelativeLayout keyboardViewParent,
            EditText mEditText, InputStatusListener mInputStatusListener,
            KeyboardStatusListener mKeyboardStatusListener) {
        initKeyboardBaseMgr(context, keyboardViewParent, mEditText,
                mInputStatusListener);
        this.mKeyboardStatusListener = mKeyboardStatusListener;
    }

    public KeyboardBaseMgr(Context context, RelativeLayout keyboardViewParent,
            EditText mEditText, InputStatusListener mInputStatusListener) {
        initKeyboardBaseMgr(context, keyboardViewParent, mEditText,
                mInputStatusListener);
    }

    private void initKeyboardBaseMgr(Context context,
            RelativeLayout keyboardViewParent, EditText mEditText,
            InputStatusListener mInputStatusListener) {
        mImm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        this.keyboardViewParent = keyboardViewParent;
        this.mEditText = mEditText;
        this.mInputStatusListener = mInputStatusListener;

    }

    protected void setEditTextListener(final Context context,
            final EUExSecurityKeyboard mEUExKeyboard, final View keyboardView,
            final String keyboardId) {
        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b && isCustom) {
                    hideKeyboard(mEUExKeyboard, keyboardView, keyboardId,
                            mInputStatusListener, false);
                } else {
                    mImm.hideSoftInputFromWindow(view.getWindowToken(), 0); // 强制隐藏键盘
                }
            }
        });

        mEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isCustom) {
                    int inputType = mEditText.getInputType();
                    hideSoftInputMethod(mEditText);
                    showKeyboard(context, mEUExKeyboard, keyboardView,
                            keyboardId);
                    mEditText.setInputType(inputType);
                }
                return false;
            }
        });
    }

    /**
     * 隐藏系统键盘 Edittext不显示系统键盘；并且要有光标； 4.0以上TYPE_NULL，不显示系统键盘，但是光标也没了；
     */
    @SuppressLint("NewApi")
    private void hideSoftInputMethod(EditText et) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        /** 4.0及以下 */
        if (currentVersion <= 14) {
            et.setInputType(InputType.TYPE_NULL);
        } else {
            et.setShowSoftInputOnFocus(false);
        }
    }

    public void showKeyboard(Context context,
            EUExSecurityKeyboard mEUExKeyboard, View keyboardView,
            String keyboardId) {
        if (mKeyboardStatusListener != null) {
            mKeyboardStatusListener.onKeyboardShow(context, keyboardView);
        }
        int visibility = keyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            keyboardView.setVisibility(View.VISIBLE);
            mEUExKeyboard.onKeyboardVisibilityChange(keyboardId,
                    ConstantUtil.KEY_BORAD_VISIBLE);
        }
    }

    public void hideKeyboard(EUExSecurityKeyboard mEUExKeyboard,
            View keyboardView, String keyboardId,
            InputStatusListener mInputStatusListener, boolean isDone) {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            ResultVO resultVO = new ResultVO();
            resultVO.setContent(mEditText.getText().toString());
            if (mInputStatusListener != null) {
                if (isDone) {
                    mInputStatusListener.onInputCompleted(resultVO);
                } else {
                    mInputStatusListener.onKeyboardDismiss(resultVO);
                }
            }
            keyboardView.setVisibility(View.INVISIBLE);
            mEUExKeyboard.onKeyboardVisibilityChange(keyboardId,
                    ConstantUtil.KEY_BORAD_INVISIBLE);
        }
    }

    /**
     * 键盘弹出、隐藏控制器
     *
     * @author admin
     *
     */
    public interface KeyboardStatusListener {
        public void onKeyboardShow(Context context, View keyboardView);
    }

}
