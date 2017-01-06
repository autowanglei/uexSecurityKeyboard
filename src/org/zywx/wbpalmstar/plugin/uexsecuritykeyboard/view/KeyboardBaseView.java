package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.view;

import java.io.IOException;
import java.io.InputStream;

import org.zywx.wbpalmstar.base.BUtility;
import org.zywx.wbpalmstar.engine.universalex.EUExUtil;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.EUExSecurityKeyboard;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.keyboardMgr.KeyboardBaseMgr;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.util.ConstantUtil;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo.OpenDataVO;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class KeyboardBaseView extends FrameLayout {
    protected EditText inputEditText;
    public KeyboardBaseMgr mKeyboardBaseMgr;
    protected EUExSecurityKeyboard mEUExKeyboard;

    public KeyboardBaseView(Context context,
            EUExSecurityKeyboard mEUExKeyboard) {
        super(context);
        this.mEUExKeyboard = mEUExKeyboard;
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
            KeyboardBaseMgr keyboardMgr, OpenDataVO dataVO,
            RelativeLayout.LayoutParams inputEditLp) {
        configKeyboardDescription(context, keyboardView, dataVO);
        configInputEditText(context, mEUExKeyboard, keyboardView, keyboardMgr,
                dataVO, inputEditLp);
    }

    private void configKeyboardDescription(Context context, View keyboardView,
            OpenDataVO dataVO) {
        View descriptionLayout = keyboardView.findViewById(
                EUExUtil.getResIdID("keyboard_description_layout"));
        descriptionLayout.setClickable(false);
        descriptionLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        ImageView keyboardLogo = (ImageView) keyboardView
                .findViewById(EUExUtil.getResIdID("keyboard_logo"));
        TextView mDescription = (TextView) keyboardView
                .findViewById(EUExUtil.getResIdID("keyboard_description"));
        if (!TextUtils.isEmpty(dataVO.getLogoPath())) {
            String logoPath = BUtility.makeRealPath(dataVO.getLogoPath(),
                    mEUExKeyboard.mBrwView.getCurrentWidget().m_widgetPath,
                    mEUExKeyboard.mBrwView.getCurrentWidget().m_wgtType);
            keyboardLogo.setImageBitmap(getLocalImage(context, logoPath));
        } else {
            keyboardLogo.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(dataVO.getKeyboardDescription())) {
            mDescription.setText(dataVO.getKeyboardDescription());
        }
    }

    private void configInputEditText(Context context,
            EUExSecurityKeyboard mEUExKeyboard, View keyboardView,
            KeyboardBaseMgr keyboardMgr, OpenDataVO dataVO,
            RelativeLayout.LayoutParams inputEditLp) {
        inputEditText.setLayoutParams(inputEditLp);
        if (dataVO.isShowInputBox()) {
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
            keyboardView.setVisibility(View.INVISIBLE);
            inputEditText.setVisibility(View.INVISIBLE);
            keyboardMgr.showKeyboard(context, mEUExKeyboard, keyboardView,
                    dataVO.getId());
        }
    }

    public void onResume() {
        mKeyboardBaseMgr.onResume();
    }

    public String getInputValue() {
        return mKeyboardBaseMgr.getInputContent();
    }

    /**
     * 根据传入的路径加载图片，imgUrl只能是 widget/wgtRes/ 或 / 开头
     * 
     * @param ctx
     * @param imgUrl
     * @return
     */
    private static Bitmap getLocalImage(Context ctx, String imgUrl) {
        if (imgUrl == null || imgUrl.length() == 0) {
            return null;
        }

        Bitmap bitmap = null;
        InputStream is = null;
        try {
            if (imgUrl.startsWith(BUtility.F_Widget_RES_path)) {
                try {
                    is = ctx.getAssets().open(imgUrl);
                    if (is != null) {
                        bitmap = BitmapFactory.decodeStream(is);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (imgUrl.startsWith("/")) {
                bitmap = BitmapFactory.decodeFile(imgUrl);
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

}
