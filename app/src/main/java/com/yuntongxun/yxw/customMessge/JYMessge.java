package com.yuntongxun.yxw.customMessge;

/**
 * TODO
 * Created by yxw on 2017/3/9.
 */

public class JYMessge {

    JYMessageType type;

    String json;
    public String getType() {
        return type.getValue();
    }

    public void setType(JYMessageType type) {
        this.type = type;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
