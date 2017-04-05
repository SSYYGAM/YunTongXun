package com.yuntongxun.yxw.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.yuntongxun.ecdemo.common.CCPAppManager;
import com.yuntongxun.yxw.R;
import com.yuntongxun.yxw.params.SDKParams;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    private EditText phoneEdt;
    Context context;
    private Button searchBtn;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        phoneEdt = ((EditText) view.findViewById(R.id.phoneEdt));
        searchBtn = ((Button) view.findViewById(R.id.searchBtn));
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SDKParams.PHONE = phoneEdt.getText().toString();
                CCPAppManager.startChattingAction(context, SDKParams.PHONE, SDKParams.PHONE, true);
            }
        });
        return view;
    }

//    public void btnClick(View view) {
//        switch (view.getId()) {
//            case R.id.searchBtn:
//                String phoneStr = phoneEdt.getText().toString();
//                CCPAppManager.startChattingAction(context, phoneStr, phoneStr, true);
//                break;
//        }
//    }


}
