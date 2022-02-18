package com.wang.lib.webview.webviewprocessor;

import android.graphics.Bitmap;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MWebViewClient extends WebViewClient {

    WebCallback mWebCallback;

    public MWebViewClient(WebCallback mWebCallback){
        this.mWebCallback = mWebCallback;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if(mWebCallback != null)
            mWebCallback.onPageStarted(url);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if(mWebCallback != null)
            mWebCallback.onPageFinished(url);
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        if(mWebCallback != null)
            mWebCallback.onReceivedError();
    }
}
