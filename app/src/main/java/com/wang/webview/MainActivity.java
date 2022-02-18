package com.wang.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.wang.lib.webview.api.IWebViewService;

import java.util.ServiceLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.helloworld).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, WebActivity.class));
//                ServiceLoader.load(IWebViewService.class).iterator().next().openWebActivity(MainActivity.this,
//                        "https://www.baidu.com", "");
                ServiceLoader.load(IWebViewService.class).iterator().next()
                        .openLocalHtml(MainActivity.this);
            }
        });
    }
}