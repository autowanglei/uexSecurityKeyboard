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
    }

    @Override
    public void onKeyboardDismiss(ResultVO resultVO) {
        resultVO.setId(id);
    }
}
