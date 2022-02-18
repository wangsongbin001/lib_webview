package com.wang.lib.webview.api;

import android.content.Context;

import androidx.fragment.app.Fragment;

public interface IWebViewService {

    void openWebActivity(Context context, String url, String title);

    Fragment getWebFragment(String url, boolean isEnableRefresh);

    void openLocalHtml(Context context);
}
