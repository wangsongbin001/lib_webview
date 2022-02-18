package com.wang.webview;

import android.content.Intent;
import android.os.RemoteException;

import com.google.auto.service.AutoService;
import com.wang.base.BaseApplication;
import com.wang.lib.webview.ICallbackFromMain2WebInterface;
import com.wang.lib.webview.command.Command;

import java.util.Map;

@AutoService({Command.class})
public class LoginCommand implements Command {

    @Override
    public String name() {
        return "login";
    }

    @Override
    public void execute(Map mapParams, ICallbackFromMain2WebInterface callback) {
        Intent intent = new Intent(BaseApplication.mApp, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseApplication.mApp.startActivity(intent);

        try{
            String callbackName = (String) mapParams.get("callbackname");
            if(callback != null) {
                callback.onResult(callbackName, "login success");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
