package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.view;

import org.zywx.wbpalmstar.engine.universalex.EUExUtil;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.EUExSecurityKeyboard;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.InputStatusListener;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.RandomKeyBoardMgr;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo.KeyboardConfig;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo.OpenDataVO;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

public class RandomKeyboardView extends SecurityKeyBoardBaseView {

    public static final String TAG = "RandomKeyboardActivity";
    private static final int IS_PASSWORD = 1;
    private RandomKeyBoardMgr mAllKeyBoard;
    private KeyboardConfig mConfig;
    private boolean isPassword = false;

    public RandomKeyboardView(Context context, EUExSecurityKeyboard mKeyboard,
            InputStatusListener mInputStatusListener,
            RelativeLayout.LayoutParams lp, OpenDataVO dataVO) {
        super(context);
        // this.setOnInputStatusListener(mInputStatusListener);
        initView(context, mKeyboard, lp, dataVO);
    }


    private void initView(Context context, EUExSecurityKeyboard mKeyboard,
            RelativeLayout.LayoutParams lp, OpenDataVO dataVO) {
        // Log.i(TAG, "onCreate");
        // RelativeLayout contentLayout = new RelativeLayout(context);
        // contentLayout.setBackgroundColor(Color.TRANSPARENT);

        View keyboardPad = View.inflate(context,
                EUExUtil.getResLayoutID(
                        "plugin_uexsecuritykeyboard_keyboard_layout_random_num"),
				null);
        // DisplayMetrics dm = new DisplayMetrics();
        // ((Object) context).getWindowManager().getDefaultDisplay()
        // .getMetrics(dm);
        //
        // Log.i(TAG, "ScreenWidth== " + dm.widthPixels + " ScreenHeight== "
        // + dm.heightPixels);
        //
        // int viewWidth = dm.widthPixels;
        // int viewHeight = 4 * viewWidth / 5;
        // if (viewHeight > dm.heightPixels / 2) {
        // viewHeight = dm.heightPixels / 2;
        // }
        // RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
        // viewWidth, viewHeight);
        // lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        // contentLayout.addView(keyboardPad, lp);
        //
        // setContentView(contentLayout);

        this.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mAllKeyBoard != null) {
					mAllKeyBoard.hideKeyboard();
				}
			}
		});

        // mConfig = (KeyboardConfig)
        // getIntent().getSerializableExtra("config");
        // if (mConfig.getSecureTextEntry() == IS_PASSWORD) {
        // isPassword = true;
        // }
        // String inputValue = getIntent().getStringExtra("inputValue");
        // String encryptValue = getIntent().getStringExtra("encryptValue");
        //
        // mAllKeyBoard = new RandomKeyBoardMgr(this, isPassword, 2, "100", 16,
        // inputValue, encryptValue);
        //
        // mAllKeyBoard.showKeyboard();
	}

    // @Override
    // public void onBackPressed() {
    // if (mAllKeyBoard != null) {
    // mAllKeyBoard.hideKeyboard();
    // }
    // }


}
