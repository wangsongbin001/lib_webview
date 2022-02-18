// lMainProcessInterface.aidl
package com.wang.lib.webview;

import com.wang.lib.webview.ICallbackFromMain2WebInterface;

// Declare any non-default types here with import statements

interface IMainProcessInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     void handleWebCommandInMain(String commandName, String jsonParams, in ICallbackFromMain2WebInterface callback);
}