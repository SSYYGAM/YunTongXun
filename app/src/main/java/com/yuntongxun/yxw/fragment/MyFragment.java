package com.yuntongxun.yxw.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yuntongxun.ecdemo.ui.SDKCoreHelper;
import com.yuntongxun.yxw.R;
import com.yuntongxun.yxw.SettingActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment implements View.OnClickListener{


    private Button loginoutBtn;
    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_setting, container, false);
        loginoutBtn = (Button) view.findViewById(R.id.settingBtn);
        loginoutBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settingBtn:
//                handleLogout(false);
                context.startActivity(new Intent(context, SettingActivity.class));
                break;
        }
    }

    /**
     * 处理退出操作
     */
    private void handleLogout(boolean isNotice) {

        SDKCoreHelper.logout(isNotice);
    }
}
