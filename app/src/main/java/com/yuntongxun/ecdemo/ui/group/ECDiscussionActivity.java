package com.yuntongxun.ecdemo.ui.group;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.yuntongxun.yxw.R;
import com.yuntongxun.ecdemo.ui.ECSuperActivity;
import com.yuntongxun.ecdemo.ui.GroupListFragment;

public class ECDiscussionActivity extends ECSuperActivity implements OnClickListener {
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getTopBarView().setTopBarToStatus(1, R.drawable.topbar_back_bt, -1, "讨论组列表", this);
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.discussion_activity;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
        case R.id.btn_left:
            hideSoftKeyboard();
            finish();
            GroupListFragment.sync=false;
            break;
        
        default:
            break;
    }
		
	}

	

}
