package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard;

import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo.ResultVO;

public interface OnInputStatusListener {
    public void onInputCompleted(ResultVO resultVO);

    public void onKeyboardDismiss(ResultVO resultVO);
}
