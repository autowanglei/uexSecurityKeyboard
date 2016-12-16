package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.keyboardMgr;

import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.EUExSecurityKeyboard;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.InputStatusListener;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.util.ConstantUtil;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo.OpenDataVO;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo.ResultVO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class KeyboardBaseMgr {

    protected EUExSecurityKeyboard mEUExKeyboard;
    protected RelativeLayout keyboardViewParent;
    protected EditText mEditText;
    protected boolean isCustom;
    protected InputMethodManager mImm;
    protected InputStatusListener mInputStatusListener;
    protected KeyboardStatusListener mKeyboardStatusListener = null;
    protected OpenDataVO dataVO;
    protected String inputValue = "";
    protected String editTextShowValue = "";

    public KeyboardBaseMgr(Context context, EUExSecurityKeyboard mEUExKeyboard,
            RelativeLayout keyboardViewParent, EditText mEditText,
            InputStatusListener mInputStatusListener,
            KeyboardStatusListener mKeyboardStatusListener, OpenDataVO dataVO) {
        initKeyboardBaseMgr(context, mEUExKeyboard, keyboardViewParent,
                mEditText, mInputStatusListener, dataVO);
        this.mKeyboardStatusListener = mKeyboardStatusListener;
    }

    public KeyboardBaseMgr(Context context, EUExSecurityKeyboard mEUExKeyboard,
            RelativeLayout keyboardViewParent, EditText mEditText,
            InputStatusListener mInputStatusListener, OpenDataVO dataVO) {
        initKeyboardBaseMgr(context, mEUExKeyboard, keyboardViewParent,
                mEditText, mInputStatusListener, dataVO);
    }

    private void initKeyboardBaseMgr(Context context,
            EUExSecurityKeyboard mEUExKeyboard,
            RelativeLayout keyboardViewParent, EditText mEditText,
            InputStatusListener mInputStatusListener, OpenDataVO dataVO) {
        mImm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        this.mEUExKeyboard = mEUExKeyboard;
        this.keyboardViewParent = keyboardViewParent;
        this.mEditText = mEditText;
        this.mInputStatusListener = mInputStatusListener;
        this.dataVO = dataVO;
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
        int visibility = keyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            if (mKeyboardStatusListener != null) {
                mKeyboardStatusListener.onKeyboardShow(context, keyboardView);
            }
            keyboardView.setVisibility(View.VISIBLE);
            mEUExKeyboard.onKeyboardVisibilityChange(keyboardId,
                    ConstantUtil.KEY_BORAD_VISIBLE);
        }
    }

    protected void hideKeyboard(EUExSecurityKeyboard mEUExKeyboard,
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
     * 更新输入的内容
     * 
     * @Description
     * @version 3.0 2014-8-1
     */
    protected void insertValue(String inputStr) {
        if ((dataVO.getMaxInputLength() < 0)
                || (mEditText.length() < dataVO.getMaxInputLength())) {
            inputValue = inputValue + inputStr;
            if (!dataVO.isShowClearText()) {
                inputStr = ConstantUtil.PASSWORD_STR;
            }
            editTextShowValue = editTextShowValue + inputStr;
            cbKeyPressToWeb(ConstantUtil.INPUT_TYPE_TEXT);
            updateEditText(editTextShowValue);
        }
    }

    /**
     * 删除输入的内容
     * 
     * @Description
     * @version 3.0 2014-8-1
     */
    protected void delValue() {
        if (inputValue.length() > 0) {
            inputValue = inputValue.substring(0, inputValue.length() - 1);
            editTextShowValue = editTextShowValue.substring(0,
                    editTextShowValue.length() - 1);
            updateEditText(editTextShowValue);
        }
    }

    /**
     * 清空内容
     */
    protected void clear() {
        if (!TextUtils.isEmpty(inputValue)) {
            inputValue = "";
            editTextShowValue = "";
            updateEditText(editTextShowValue);
        }
    }

    /**
     * 获取输入内容
     * 
     * @return
     */
    public String getInputContent() {
        return inputValue;
    }

    private void updateEditText(String text) {
        mEditText.setText(text);
        Selection.setSelection(mEditText.getText(),
                mEditText.getText().length());// 移动光标到最右
    }

    /**
     * INPUT_TYPE_TEXT = 0; INPUT_TYPE_DEL = 1时，回调处理
     * 
     * @param inputType
     */
    protected void cbKeyPressToWeb(int inputType) {
        if (!dataVO.isShowInputBox() && mEUExKeyboard != null) {
            mEUExKeyboard.onKeyPress(inputType);
        }
    }

    public void onResume() {
        if (dataVO.isCleanPassword()) {
            inputValue = "";
            editTextShowValue = "";
            mEditText.setText(editTextShowValue);
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
