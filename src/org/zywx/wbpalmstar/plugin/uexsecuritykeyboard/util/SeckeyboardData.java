package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.util;

import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.view.SecKeyboardView;

public class SeckeyboardData {
    private SecKeyboardView view;
    private boolean isScrollWithWeb;

    public SeckeyboardData(SecKeyboardView view, boolean isScrollWithWeb) {
        this.view = view;
        this.isScrollWithWeb = isScrollWithWeb;
    }

    public SecKeyboardView getView() {
        return view;
    }

    public void setView(SecKeyboardView view) {
        this.view = view;
    }

    public boolean isScrollWithWeb() {
        return isScrollWithWeb;
    }

    public void setIsScrollWithWeb(boolean isScrollWithWeb) {
        this.isScrollWithWeb = isScrollWithWeb;
    }
}
