package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo;

import java.io.Serializable;

public class ResultVO extends DataBaseVO implements Serializable {
    private static final long serialVersionUID = -2763983750395243320L;

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
