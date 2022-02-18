package com.wang.lib.webview;

import android.content.Context;
import android.content.Intent;

import com.google.auto.service.AutoService;
import com.wang.lib.webview.api.IWebViewService;
import com.wang.lib.webview.utils.Constant;

import androidx.fragment.app.Fragment;

@AutoService(value = {IWebViewService.class})
public class IWebViewApiImp implements IWebViewService {

    @Override
    public void openWebActivity(Context context, String url, String title) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(Constant.TITLE, title);
        intent.putExtra(Constant.URL, url);
        context.startActivity(intent);
    }

    @Override
    public Fragment getWebFragment(String url, boolean isEnableRefresh) {
        return WebFragment.Companion.newInstance(url, isEnableRefresh);
    }

    @Override
    public void openLocalHtml(Context context) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(Constant.TITLE, "本地Demo测试页");
        intent.putExtra(Constant.URL, "file:///android_asset/demo.html");
        context.startActivity(intent);
    }
}
