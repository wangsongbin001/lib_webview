var mGlobal = {};
mGlobal.os = {};
mGlobal.os.isIOS = /iOS|iPhone|iPad|iPod/i.test(navigator.userAgent);
mGlobal.os.isAndroid = !mGlobal.os.isIOS;
mGlobal.callbacks = {}

mGlobal.callback = function (callbackname, response) {
   console.log("xxxx " + callbackname);
   var callbackobject = mGlobal.callbacks[callbackname];
   if (callbackobject !== undefined){
       if(callbackobject.callback != undefined){
          console.log("xxxxxx "+response);
            var ret = callbackobject.callback(response);
           if(ret === false){
               return
           }
           delete mGlobal.callbacks[callbackname];
       }
   }
}

mGlobal.takeNativeAction = function(command, paramters){
    console.log("mGlobal takenativeaction");
    var request = {};
    request.command = command;
    request.param = paramters;
    if(mGlobal.os.isAndroid){
        window.NativeWebView.takeNativeAction(JSON.stringify(request))
    }else{ //ios
        window.webkit.messageHandlers.NativeWebView.postMessage(JSON.stringify(request))
    }
}

mGlobal.takeNativeActionWithCallback = function(command, paramters, callback){
    console.log("mGlobal takeNativeActionWithCallback");
    var callbackname = "nativetojs_callback_" +  (new Date()).getTime() + "_" + Math.floor(Math.random() * 10000);
    mGlobal.callbacks[callbackname] = {callback:callback};

    var request = {};
    request.command = command;
    request.param = paramters;
    request.param.callbackname = callbackname;
    if(mGlobal.os.isAndroid){
        window.NativeWebView.takeNativeAction(JSON.stringify(request))
    }else{ //ios
        window.webkit.messageHandlers.NativeWebView.postMessage(JSON.stringify(request))
    }
}

window.mGlobal = mGlobal