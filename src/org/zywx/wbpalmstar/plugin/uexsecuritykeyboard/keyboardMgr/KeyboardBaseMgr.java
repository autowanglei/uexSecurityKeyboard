package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.keyboardMgr;

import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.EUExSecurityKeyboard;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.InputStatusListener;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.RC4.HexConverter;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.RC4.RC4Encryption;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.util.ConstantUtil;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo.InputVO;
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

    public EUExSecurityKeyboard mEUExKeyboard;
    public RelativeLayout keyboardViewParent;
    protected EditText mEditText;
    protected boolean isCustom;
    protected InputMethodManager mImm;
    protected InputStatusListener mInputStatusListener;
    protected KeyboardStatusListener mKeyboardStatusListener = null;
    protected OpenDataVO dataVO;
    /** * RC4加密后的数据 */
    protected String inputValue = "";
    protected int inputValues = 611008960;
    protected String inputValuess = "13611008960";
    // protected String editTextShowValue = "";

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
     * RC4加密
     * 
     * @param value
     *            要加密的字符串
     * @return 加密后的字符串
     */
    private String RC4Encrypt(String value) {
        return HexConverter
                .binaryToHexString(RC4Encryption.os_decrypt(value.getBytes(),
                        value.getBytes().length, RC4Encryption.F_KEY));
    }

    /**
     * RC4解密
     * 
     * @param value
     *            要解密的字符串
     * @return 解密后的字符串
     */
    private String RC4Decrypt(String value) {
        byte[] valueByte = HexConverter.hexStringToBinary(value);
        return new String(RC4Encryption.os_decrypt(valueByte, valueByte.length,
                RC4Encryption.F_KEY));

    }

    /**
     * 删除或输入数据的操作
     * 
     * @param srcValue
     *            源字符串，密文
     * @param action
     *            删除或插入数据
     * @param addValue
     *            要添加的值，明文
     * @return 明文
     */
    private InputVO handleValue(String srcValue, int action, String addValue) {
        String clearText = "";
        if (!TextUtils.isEmpty(srcValue)) {
            clearText = RC4Decrypt(srcValue);
        }
        switch (action) {
        case ConstantUtil.INPUT_ACTION_INSERT:
            clearText += addValue;
            break;
        case ConstantUtil.INPUT_ACTION_DEL:
            clearText = clearText.substring(0, clearText.length() - 1);
            break;
        default:
            break;
        }
        return new InputVO(clearText, RC4Encrypt(clearText));
    }

    /**
     * @param inputChar
     *            输入的数据
     */
    protected void insertValue(String inputChar) {
        if ((dataVO.getMaxInputLength() < 0)
                || (mEditText.length() < dataVO.getMaxInputLength())) {
            InputVO inputVO = handleValue(inputValue,
                    ConstantUtil.INPUT_ACTION_INSERT, inputChar);
            inputValue = inputVO.getCiphertext();
            String showText = inputVO.getClearText();
            if (!dataVO.isShowClearText()) {
                showText = replacePwd(showText);
            }
            cbKeyPressToWeb(ConstantUtil.INPUT_TYPE_TEXT);
            updateEditText(showText);
        }
    }

    /**
     * 将明文替换成 ConstantUtil.PASSWORD_STR
     * 
     * @param text
     * @return 与text等长的ConstantUtil.PASSWORD_STR
     */
    private String replacePwd(String text) {
        StringBuffer replace = new StringBuffer();
        for (int i = 0; i < text.length(); i++) {
            replace.append(ConstantUtil.PASSWORD_STR);
        }
        return replace.toString();
    }

    /**
     * 删除输入的内容
     * 
     * @Description
     * @version 3.0 2014-8-1
     */
    protected void delValue() {
        if (inputValue.length() > 0) {
            InputVO inputVO = handleValue(inputValue,
                    ConstantUtil.INPUT_ACTION_DEL, "");
            inputValue = inputVO.getCiphertext();
            String showText = inputVO.getClearText();
            if (!dataVO.isShowClearText()) {
                showText = replacePwd(showText);
            }
            updateEditText(showText);
            /** 银河证券逻辑，与iOS保持一致，即使没有输入内容，也给前端删除的回调，正常逻辑应该是有输入内容才给前端回调 */
            // cbKeyPressToWeb(ConstantUtil.INPUT_TYPE_DEL); //正确逻辑
        }
        /** 银河证券逻辑，与iOS保持一致，即使没有输入内容，也给前端删除的回调，正常逻辑应该是有输入内容才给前端回调 */
        cbKeyPressToWeb(ConstantUtil.INPUT_TYPE_DEL);// 银河证券逻辑，与iOS保持一致
    }

    /**
     * INPUT_TYPE_DONE，输入完成时，回调处理
     */
    protected void onKeyDonePress(EUExSecurityKeyboard mEUExKeyboard,
            View keyboardView) {
        hideKeyboard(mEUExKeyboard, keyboardView, dataVO.getId(),
                mInputStatusListener, true);
        if (mEUExKeyboard != null) {
            mEUExKeyboard.onKeyPress(ConstantUtil.INPUT_TYPE_DONE);
        }
    }

    /**
     * 清空内容
     */
    private void clear() {
        if (!TextUtils.isEmpty(inputValue)) {
            inputValue = "";
            updateEditText("");
        }
    }

    /**
     * 获取输入内容
     * 
     * @return
     */
    public String getInputContent() {
        return RC4Decrypt(inputValue);
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
            clear();
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
