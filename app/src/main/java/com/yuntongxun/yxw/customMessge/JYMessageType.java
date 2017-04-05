package com.yuntongxun.yxw.customMessge;

/**
 * TODO
 * Created by yxw on 2017/3/9.
 */

public enum JYMessageType {

    product("product"),card("card");
    String value;

    JYMessageType(String value){
        this.value=value;

    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
