package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.util;

import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.view.SecurityKeyBoardBaseView;

public class SeckeyboardData {
    private SecurityKeyBoardBaseView view;
    private boolean isScrollWithWeb;

    public SeckeyboardData(SecurityKeyBoardBaseView view,
            boolean isScrollWithWeb) {
        this.view = view;
        this.isScrollWithWeb = isScrollWithWeb;
    }

    public SecurityKeyBoardBaseView getView() {
        return view;
    }

    public void setView(SecurityKeyBoardBaseView view) {
        this.view = view;
    }

    public boolean isScrollWithWeb() {
        return isScrollWithWeb;
    }

    public void setIsScrollWithWeb(boolean isScrollWithWeb) {
        this.isScrollWithWeb = isScrollWithWeb;
    }
}
