package com.yuntongxun.yxw.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.yuntongxun.yxw.utils.AppSharedPreferences;

public class BaseFragmentActivity extends FragmentActivity {

    AppSharedPreferences asp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        asp = new AppSharedPreferences(this, "user");
    }
}
