package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.util;

import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.view.KeyboardBaseView;

public class SeckeyboardData {
    private KeyboardBaseView view;
    private boolean isScrollWithWeb;

    public SeckeyboardData(KeyboardBaseView view, boolean isScrollWithWeb) {
        this.view = view;
        this.isScrollWithWeb = isScrollWithWeb;
    }

    public KeyboardBaseView getView() {
        return view;
    }

    public void setView(KeyboardBaseView view) {
        this.view = view;
    }

    public boolean isScrollWithWeb() {
        return isScrollWithWeb;
    }

    public void setIsScrollWithWeb(boolean isScrollWithWeb) {
        this.isScrollWithWeb = isScrollWithWeb;
    }
}
