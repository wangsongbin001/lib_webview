package com.wang.webview;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wang.base.BaseActivity;

import androidx.annotation.Nullable;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initContentView());
    }

    private View initContentView() {
        TextView textView = new TextView(this);
        textView.setText("activity in main");
        return textView;
    }
}
