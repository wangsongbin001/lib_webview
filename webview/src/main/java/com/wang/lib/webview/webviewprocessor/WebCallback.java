package com.wang.lib.webview.webviewprocessor;

public interface WebCallback {

    void onReceivedTitle(String title);

    void onPageStarted(String url);

    void onPageFinished(String url);

    void onReceivedError();

}
