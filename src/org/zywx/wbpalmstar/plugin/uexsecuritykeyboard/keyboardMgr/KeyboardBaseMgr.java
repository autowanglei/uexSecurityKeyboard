package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.keyboardMgr;

import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.EUExSecurityKeyboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class KeyboardBaseMgr {

    protected EditText mEditText;
    protected boolean isCustom;
    protected InputMethodManager mImm;
    public KeyboardStatuController mController;

    public KeyboardBaseMgr(Context context) {
        mImm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    protected void setEditTextListener(final EUExSecurityKeyboard mEUExKeyboard,
            final View keyboardView,
            final KeyboardStatuController mController) {
        this.mController = mController;
        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b && isCustom) {
                    mController.hide(mEUExKeyboard, keyboardView,
                            false);
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
                    mController.show(mEUExKeyboard, keyboardView);
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
        String methodName = null;
        /** 4.0及以下 */
        if (currentVersion <= 14) {
            et.setInputType(InputType.TYPE_NULL);
        } else {
            et.setShowSoftInputOnFocus(false);
        }
    }
    
    /**
     * 键盘弹出、隐藏控制器
     * 
     * @author admin
     *
     */
    public interface KeyboardStatuController {
        public void hide(EUExSecurityKeyboard mEUExKeyboard,
                View keyboardView, boolean isDone);

        public void show(EUExSecurityKeyboard mEUExKeyboard,
                View keyboardView);
    }

}
