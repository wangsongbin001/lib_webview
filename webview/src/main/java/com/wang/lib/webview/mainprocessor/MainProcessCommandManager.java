package com.wang.lib.webview.mainprocessor;

import android.os.RemoteException;
import android.util.Log;

import com.google.gson.Gson;
import com.wang.lib.webview.ICallbackFromMain2WebInterface;
import com.wang.lib.webview.IMainProcessInterface;
import com.wang.lib.webview.command.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class MainProcessCommandManager extends IMainProcessInterface.Stub {

    private static MainProcessCommandManager sInstance;
    private static HashMap<String, Command> mCommands = new HashMap<>();

    public static MainProcessCommandManager getInstance() {
        if (sInstance == null) {
            synchronized (MainProcessCommandManager.class) {
                if (sInstance == null) {
                    sInstance = new MainProcessCommandManager();
                }
            }
        }
        return sInstance;
    }

    private MainProcessCommandManager() {
        ServiceLoader<Command> serviceLoader = ServiceLoader.load(Command.class);
        for (Command command : serviceLoader) {
            if (command != null && !mCommands.containsKey(command.name())) {
                mCommands.put(command.name(), command);
            }
        }
    }

    @Override
    public void handleWebCommandInMain(String commandName, String jsonParams, ICallbackFromMain2WebInterface callback) throws RemoteException {
        Log.i("MWebView", "handleWebCommandInMain:" + jsonParams);
        Command command = mCommands.get(commandName);
        if (command != null) {
            command.execute(new Gson().fromJson(jsonParams, Map.class), callback);
        }
    }
}
