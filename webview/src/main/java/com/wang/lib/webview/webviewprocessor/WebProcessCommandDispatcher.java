package com.wang.lib.webview.webviewprocessor;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.wang.base.BaseApplication;
import com.wang.lib.webview.ICallbackFromMain2WebInterface;
import com.wang.lib.webview.IMainProcessInterface;
import com.wang.lib.webview.bean.JsParam;
import com.wang.lib.webview.mainprocessor.MainProcessCommandService;

public class WebProcessCommandDispatcher implements ServiceConnection {

    private static WebProcessCommandDispatcher mInstance;

    public static WebProcessCommandDispatcher getInstance() {
        if (mInstance == null) {
            synchronized (WebProcessCommandDispatcher.class) {
                if (mInstance == null) {
                    mInstance = new WebProcessCommandDispatcher();
                }
            }
        }
        return mInstance;
    }

    private IMainProcessInterface mIMainProcessInterface;

    private WebProcessCommandDispatcher() {
        initAIDLConnection();
    }

    private void initAIDLConnection() {
        Intent intent = new Intent(BaseApplication.mApp, MainProcessCommandService.class);
        BaseApplication.mApp.bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mIMainProcessInterface = IMainProcessInterface.Stub.asInterface(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mIMainProcessInterface = null;
        initAIDLConnection();
    }

    @Override
    public void onBindingDied(ComponentName name) {
        mIMainProcessInterface = null;
        initAIDLConnection();
    }

    public void executeCommand(JsParam jsParam, MWebView mWebView) {
        Log.i("MWebView", "executeCommand:" + jsParam);
        if (mIMainProcessInterface != null) {
            try {
                mIMainProcessInterface.handleWebCommandInMain(jsParam.command, jsParam.param.toString(), new ICallbackFromMain2WebInterface.Stub() {
                    @Override
                    public void onResult(String callbackName, String respone) throws RemoteException {
                        Log.i("MWebView", "onResult:" + callbackName + " response:"  + respone);
                        mWebView.handleCallback(callbackName, respone);
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
