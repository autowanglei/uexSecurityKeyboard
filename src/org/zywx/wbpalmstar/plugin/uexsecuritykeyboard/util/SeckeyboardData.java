package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.util;

import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.view.BaseFrameLayout;

public class SeckeyboardData {
    private BaseFrameLayout view;
    private boolean isScrollWithWeb;

    public SeckeyboardData(BaseFrameLayout view,
            boolean isScrollWithWeb) {
        this.view = view;
        this.isScrollWithWeb = isScrollWithWeb;
    }

    public BaseFrameLayout getView() {
        return view;
    }

    public void setView(BaseFrameLayout view) {
        this.view = view;
    }

    public boolean isScrollWithWeb() {
        return isScrollWithWeb;
    }

    public void setIsScrollWithWeb(boolean isScrollWithWeb) {
        this.isScrollWithWeb = isScrollWithWeb;
    }
}
