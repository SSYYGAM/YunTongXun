package com.yuntongxun.yxw.base;

import android.app.Activity;
import android.os.Bundle;

import com.yuntongxun.yxw.utils.AppSharedPreferences;

public class BaseActivity extends Activity {

    public AppSharedPreferences asp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base);
        asp = new AppSharedPreferences(this, "user");
    }
}
