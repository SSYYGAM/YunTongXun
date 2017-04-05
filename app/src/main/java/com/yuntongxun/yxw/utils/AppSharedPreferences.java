package com.yuntongxun.yxw.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * TODO
 * Created by yxw on 2017/2/20.
 */

public class AppSharedPreferences {
    SharedPreferences sp;

    public AppSharedPreferences(Context context, String name) {
        sp = context.getSharedPreferences(name,Context.MODE_PRIVATE);
    }

    public void setPhone(String phoneStr) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("phoneStr", phoneStr);
        edit.commit();
    }
    public String getPhone() {
        String phoneStr = sp.getString("phoneStr", "");
        return phoneStr;
    }
}
