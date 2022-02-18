package com.wang.lib.webview.webviewprocessor;

import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class MWebChromeClient extends WebChromeClient {
    public static final String tag = "MWebView";
    WebCallback webCallback;

    public MWebChromeClient(WebCallback webCallback) {
        this.webCallback = webCallback;
    }

    /**
     * JSlog 打印到AS
     *
     * @param consoleMessage
     * @return
     */
    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        Log.i(tag, consoleMessage.message());
        return super.onConsoleMessage(consoleMessage);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        if (webCallback != null) {
            webCallback.onReceivedTitle(title);
        }
    }
}
