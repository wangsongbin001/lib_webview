package com.wang.lib.webview.command;

import com.wang.lib.webview.ICallbackFromMain2WebInterface;

import java.util.Map;

public interface Command {
    String name();

    void execute(Map mapParams, ICallbackFromMain2WebInterface callback);
}
