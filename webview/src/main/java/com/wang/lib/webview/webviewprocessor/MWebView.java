package com.wang.lib.webview.webviewprocessor;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wang.lib.webview.bean.JsParam;
import com.wang.lib.webview.mainprocessor.MainProcessCommandManager;

import java.util.Map;

public class MWebView extends WebView {

    public static final String TAG = "MWebView";

    public MWebView(Context context) {
        super(context);
        init();
    }

    public MWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        MWebSettings.initSettings(this);
        addJavascriptInterface(this, "NativeWebView");
        WebProcessCommandDispatcher.getInstance();
    }

    public void registerWebCallback(WebCallback webCallback) {
        setWebChromeClient(new MWebChromeClient(webCallback));
        setWebViewClient(new MWebViewClient(webCallback));
    }

    @JavascriptInterface
    public void takeNativeAction(final String jsParam) {
        Log.i(TAG, "takeNativeAction:" + jsParam);
        if (!TextUtils.isEmpty(jsParam)) {
            final JsParam jsParamObject = new Gson().fromJson(jsParam, JsParam.class);
            if (jsParamObject != null) {
                if ("showToast".equalsIgnoreCase(jsParamObject.command)) {
                    Toast.makeText(getContext(), String.valueOf(new Gson().fromJson(jsParamObject.param, Map.class).get("message")), Toast.LENGTH_LONG).show();
                } else {
                    try {
                        WebProcessCommandDispatcher.getInstance().executeCommand(jsParamObject, this);
                    } catch (Exception e) {
                        Log.e(TAG, "MWebView takeNativeAction" + e.getMessage());
                    }
                }
            }
        }
    }

    public void handleCallback(final String callbackname, final String response){
        if(!TextUtils.isEmpty(callbackname) && !TextUtils.isEmpty(response)){
            post(new Runnable() {
                @Override
                public void run() {
                    String jscode = "javascript:mGlobal.callback('" + callbackname + "','" + response + "')";
                    Log.e("xxxxxx", jscode);
                    evaluateJavascript(jscode, null);
                }
            });
        }
    }
}
