package com.wang.lib.webview.mainprocessor;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MainProcessCommandService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return MainProcessCommandManager.getInstance();
    }
}
