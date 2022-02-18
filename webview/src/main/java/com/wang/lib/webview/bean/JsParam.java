package com.wang.lib.webview.bean;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class JsParam {
    @SerializedName("command")
    public String command;
    @SerializedName("param")
    public JsonObject param;
}
