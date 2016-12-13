package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard;

import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo.ResultVO;

public class InputStatusListener implements OnInputStatusListener {

    private String id;

    public InputStatusListener(String id) {
        this.id = id;
    }

    @Override
    public void onInputCompleted(ResultVO resultVO) {
        resultVO.setId(id);
        // callBackPluginJs(JsConst.ON_INPUT_COMPLETED,
        // DataHelper.gson.toJson(resultVO));
    }

    @Override
    public void onKeyboardDismiss(ResultVO resultVO) {
        resultVO.setId(id);
        // callBackPluginJs(JsConst.ON_KEYBOARD_DISMISS,
        // DataHelper.gson.toJson(resultVO));
    }
}
