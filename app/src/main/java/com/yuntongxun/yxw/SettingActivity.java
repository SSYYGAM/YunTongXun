package com.yuntongxun.yxw;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.yuntongxun.ecdemo.common.utils.ECPreferenceSettings;
import com.yuntongxun.ecdemo.common.utils.ECPreferences;
import com.yuntongxun.ecdemo.ui.SDKCoreHelper;
import com.yuntongxun.yxw.base.BaseActivity;
import com.yuntongxun.yxw.params.SDKParams;

import java.io.InvalidClassException;

public class SettingActivity extends BaseActivity {

    InternalReceiver internalReceiver;
    private int mExitType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        registerReceiver(new String[]{SDKCoreHelper.ACTION_LOGOUT});
    }

    public void btnClick(View view) {
        SDKParams.PHONE = "";
        handleLogout(false);
    }


    protected final void registerReceiver(String[] actionArray) {
        if (actionArray == null) {
            return;
        }
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction(SDKCoreHelper.ACTION_KICK_OFF);
        for (String action : actionArray) {
            intentfilter.addAction(action);
        }
        if (internalReceiver == null) {
            internalReceiver = new InternalReceiver();
        }
        registerReceiver(internalReceiver, intentfilter);
    }

    private class InternalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent == null || intent.getAction() == null ) {
                return ;
            }
            handleReceiver(context, intent);
        }
    }

    private void handleReceiver(Context context, Intent intent) {
        if(SDKCoreHelper.ACTION_LOGOUT.equals(intent.getAction())) {

            try {
                Intent outIntent = new Intent(getApplicationContext(), LoginActivity.class);
                outIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if(mExitType == 1) {
                    ECPreferences.savePreference(ECPreferenceSettings.SETTINGS_FULLY_EXIT, true, true);
                    startActivity(outIntent);
                    finish();
                    return ;
                }
                ECPreferences.savePreference(ECPreferenceSettings.SETTINGS_REGIST_AUTO, "", true);
                startActivity(outIntent);
                finish();
            } catch (InvalidClassException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理退出操作
     */
    private void handleLogout(boolean isNotice) {
        asp.setPhone("");
        SDKCoreHelper.logout(isNotice);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(internalReceiver);
    }
}
