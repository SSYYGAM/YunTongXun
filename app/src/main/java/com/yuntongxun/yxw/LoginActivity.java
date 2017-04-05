package com.yuntongxun.yxw;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yuntongxun.ecdemo.common.CCPAppManager;
import com.yuntongxun.ecdemo.common.dialog.ECProgressDialog;
import com.yuntongxun.ecdemo.common.utils.ECPreferenceSettings;
import com.yuntongxun.ecdemo.common.utils.ECPreferences;
import com.yuntongxun.ecdemo.common.utils.ToastUtil;
import com.yuntongxun.ecdemo.core.ClientUser;
import com.yuntongxun.ecdemo.core.ContactsCache;
import com.yuntongxun.ecdemo.storage.ContactSqlManager;
import com.yuntongxun.ecdemo.ui.SDKCoreHelper;
import com.yuntongxun.ecdemo.ui.contact.ContactLogic;
import com.yuntongxun.ecdemo.ui.contact.ECContacts;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECInitParams;
import com.yuntongxun.ecsdk.SdkErrorCode;
import com.yuntongxun.yxw.base.BaseActivity;
import com.yuntongxun.yxw.params.SDKParams;

import java.io.InvalidClassException;
import java.util.ArrayList;


public class LoginActivity extends BaseActivity {
    EditText phoneEdt;
    String phoneStr;
    /**
     * 初始化广播接收器
     */
    private InternalReceiver internalReceiver;
    //dialog提示框,正在登录
    private ECProgressDialog mPostingdialog;
    //登录模式
    ECInitParams.LoginAuthType mLoginAuthType = ECInitParams.LoginAuthType.NORMAL_AUTH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        findViews();

        checkCanAutoLogin();

        //注册广播
        registerReceiver(new String[]{SDKCoreHelper.ACTION_SDK_CONNECT});

    }

    /**
     * 初始化控件
     */
    private void findViews() {
        phoneEdt = (EditText) findViewById(R.id.phoneEdt);
    }

    /**
     * 是否可以自动登录
     */
    private void checkCanAutoLogin() {
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra("nofification_type");
        if (TextUtils.isEmpty(stringExtra)) {
            //正常登录,自动登录
            if (!TextUtils.isEmpty(asp.getPhone())) {
                Log.e("====", "===自动登录===");
                Toast.makeText(this, "自动登录", Toast.LENGTH_SHORT).show();
                imLogin();
            }
        }else {
            //异常登录,跳转到LoginActivity
        }
    }

    /**
     * 注册广播
     *
     * @param actionArray
     */
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

    /**
     * 点击事件
     *
     * @param view
     */
    public void btnClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.loginBtn:
                phoneStr = phoneEdt.getText().toString();
                asp.setPhone(phoneStr);
                Log.e("====", "===btnClick===" + asp.getPhone());
                imLogin();
                break;
        }
    }

    private void imLogin() {
        ClientUser clientUser = new ClientUser(asp.getPhone());
        clientUser.setAppKey(SDKParams.IM_APPKEY);
        clientUser.setAppToken(SDKParams.IM_TOKEN);
        clientUser.setLoginAuthType(ECInitParams.LoginAuthType.NORMAL_AUTH);
        clientUser.setPassword("");
        CCPAppManager.setClientUser(clientUser);
        mPostingdialog = new ECProgressDialog(this, "正在登录,请稍等");
        mPostingdialog.show();
        SDKCoreHelper.init(this, ECInitParams.LoginMode.FORCE_LOGIN);
    }

    /**
     * 广播
     */
    private class InternalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getAction() == null) {
                return;
            }
            handleReceiver(context, intent);
        }
    }

    /**
     * 如果子界面需要拦截处理注册的广播
     * 需要实现该方法
     *
     * @param context
     * @param intent
     */
    protected void handleReceiver(Context context, Intent intent) {
        // super.handleReceiver(context, intent);
        int error = intent.getIntExtra("error", -1);
        if (SDKCoreHelper.ACTION_SDK_CONNECT.equals(intent.getAction())) {
            // 初始注册结果，成功或者失败
            if (SDKCoreHelper.getConnectState() == ECDevice.ECConnectState.CONNECT_SUCCESS
                    && error == SdkErrorCode.REQUEST_SUCCESS) {
                Log.e("====", "===登陆成功===");
                dismissPostingDialog();
                try {
                    //保存账户信息
                    saveAccount();
                } catch (InvalidClassException e) {
                    e.printStackTrace();
                }
                ContactsCache.getInstance().load();

                //跳转到MainActivity
                skipToMainActivity();
                return;
            }
            if (intent.hasExtra("error")) {
                if (SdkErrorCode.CONNECTTING == error) {
                    return;
                }
                if (error == -1) {
                    ToastUtil.showMessage("请检查登陆参数是否正确[" + error + "]");
                } else {
                    dismissPostingDialog();
                }
                ToastUtil.showMessage("登录失败，请稍后重试[" + error + "]");
            }
            dismissPostingDialog();
        }


    }

    /**
     * 跳转到MainActivity中
     */
    private void skipToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 保存账户信息(不是太明白为什么有保存一次)
     *
     * @throws InvalidClassException
     */
    private void saveAccount() throws InvalidClassException {
//        String appKey = appkeyEt.getText().toString().trim();
//        String token = tokenEt.getText().toString().trim();
//        String mobile = mobileEt.getText().toString().trim();
//        String voippass = mVoipEt.getText().toString().trim();
        ClientUser user = CCPAppManager.getClientUser();
        if (user == null) {
            user = new ClientUser(asp.getPhone());
        } else {
            user.setUserId(asp.getPhone());
        }
        user.setAppToken(SDKParams.IM_APPKEY);
        user.setAppKey(SDKParams.IM_APPKEY);
        user.setPassword("");
        user.setLoginAuthType(mLoginAuthType);
        CCPAppManager.setClientUser(user);
        ECPreferences.savePreference(ECPreferenceSettings.SETTINGS_REGIST_AUTO, user.toString(), true);
        // ContactSqlManager.insertContacts(contacts);
        ArrayList<ECContacts> objects = ContactLogic.initContacts();
        objects = ContactLogic.converContacts(objects);
        ContactSqlManager.insertContacts(objects);
    }

    /**
     * 关闭对话框
     */
    private void dismissPostingDialog() {
        if (mPostingdialog == null || !mPostingdialog.isShowing()) {
            return;
        }
        mPostingdialog.dismiss();
        mPostingdialog = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(internalReceiver);
    }
}
