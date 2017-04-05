package com.yuntongxun.yxw;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuntongxun.ecdemo.storage.IMessageSqlManager;
import com.yuntongxun.ecdemo.ui.ConversationListFragment;
import com.yuntongxun.yxw.fragment.BaseFragmentActivity;
import com.yuntongxun.yxw.fragment.MyFragment;
import com.yuntongxun.yxw.fragment.SearchFragment;

public class MainActivity extends BaseFragmentActivity implements ConversationListFragment.OnUpdateMsgUnreadCountsListener {

    private RelativeLayout container;
    private TextView searchTv;
    private TextView chatTv;
    private TextView unReadTV;
    private TextView settingTV;
    private RelativeLayout chatRl;

    SearchFragment searchFragment = new SearchFragment();
    ConversationListFragment conversationListFragment = new ConversationListFragment();
    MyFragment mySettingFragment = new MyFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        showFragment(0);
        OnUpdateMsgUnreadCounts();

    }

    /**
     * 查找控件
     */
    private void findViews() {
        container = (RelativeLayout) findViewById(R.id.container);
        searchTv = (TextView) findViewById(R.id.searchTv);
        chatTv = (TextView) findViewById(R.id.chatTv);
        unReadTV = (TextView) findViewById(R.id.unReadTV);
        chatRl = (RelativeLayout) findViewById(R.id.chatRl);
        settingTV = (TextView) findViewById(R.id.settingTV);
    }

    /**
     * 切换fragment
     *
     * @param type
     */
    private void showFragment(int type) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (searchFragment != null && searchFragment.isAdded()) {
            transaction.hide(searchFragment);
        }

        if (conversationListFragment != null && conversationListFragment.isAdded()) {
            transaction.hide(conversationListFragment);
        }
        if (mySettingFragment != null && mySettingFragment.isAdded()) {
            transaction.hide(mySettingFragment);
        }

        searchTv.setTextColor(Color.BLACK);
        chatTv.setTextColor(Color.BLACK);
        settingTV.setTextColor(Color.BLACK);

        switch (type) {
            case 0:
                if (searchFragment == null) {
                    searchFragment = new SearchFragment();
                } else {
                    if (searchFragment.isAdded()) {
                        transaction.show(searchFragment);
                    } else {
                        transaction.add(R.id.container, searchFragment);
                    }
                }
                searchTv.setTextColor(Color.RED);
                break;
            case 1:
                if (conversationListFragment == null) {
                    conversationListFragment = new ConversationListFragment();
                } else {
                    if (conversationListFragment.isAdded()) {
                        transaction.show(conversationListFragment);
                    } else {
                        transaction.add(R.id.container, conversationListFragment);
                    }
                }
                chatTv.setTextColor(Color.RED);
                break;
            case 2:
                if (mySettingFragment == null) {
                    mySettingFragment = new MyFragment();
                } else {
                    if (mySettingFragment.isAdded()) {
                        transaction.show(mySettingFragment);
                    } else {
                        transaction.add(R.id.container, mySettingFragment);
                    }
                }
                settingTV.setTextColor(Color.RED);
                break;
        }
        transaction.commit();

    }

    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.searchTv:
                showFragment(0);
                Log.e("====", "===btnClick===111111111");
                break;
            case R.id.chatRl:
                Log.e("====", "===btnClick===222222222");
                showFragment(1);
                break;
            case R.id.settingTV:
                Log.e("====", "===btnClick===3333333333");
                showFragment(2);
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int type = intent.getIntExtra("type", -1);
        Log.e("====", "===onNewIntent===" + type);
        switch (type) {
            case -1:
                break;
            case 1:
                showFragment(type);
                break;
        }
    }

    @Override
    public void OnUpdateMsgUnreadCounts() {
        int unreadCount = IMessageSqlManager.qureyAllSessionUnreadCount();
        int notifyUnreadCount = IMessageSqlManager.getUnNotifyUnreadCount();
        int count = unreadCount;
        if (unreadCount >= notifyUnreadCount) {
            count = unreadCount - notifyUnreadCount;
        }

        Log.e("====", "===OnUpdateMsgUnreadCounts==="+count);
        if (count > 0) {
            if (count > 99) {
                unReadTV.setText("...");
            } else {
                unReadTV.setText(String.valueOf(count));
            }
            unReadTV.setVisibility(View.VISIBLE);
            return;
        }
        unReadTV.setVisibility(View.GONE);

    }

}
