package com.wang.lib.webview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.wang.lib.webview.databinding.LibFragmentWebBinding
import com.wang.lib.webview.loadsir.EmptyCallback
import com.wang.lib.webview.loadsir.ErrorCallback
import com.wang.lib.webview.loadsir.LoadingCallback
import com.wang.lib.webview.utils.Constant
import com.wang.lib.webview.webviewprocessor.MWebChromeClient
import com.wang.lib.webview.webviewprocessor.MWebSettings
import com.wang.lib.webview.webviewprocessor.MWebViewClient
import com.wang.lib.webview.webviewprocessor.WebCallback

class WebFragment : Fragment(), WebCallback, OnRefreshListener {
    private val TAG: String = "WebFragment"
    var mUrl: String? = ""
    lateinit var mLibFragmentWebBinding: LibFragmentWebBinding
    lateinit var mLoadService: LoadService<Any>
    var isEnableRefresh = false

    var isError = false

    companion object {
        fun newInstance(url: String? = "", isEnableRefresh: Boolean): WebFragment {
            val fragment = WebFragment()
            fragment.arguments = Bundle().apply {
                putString(Constant.URL, url ?: "")
                putBoolean(Constant.ISENABLREFRESH, isEnableRefresh)
            }
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "onCreate")
        arguments?.apply {
            mUrl = getString(Constant.URL)
            isEnableRefresh = getBoolean(Constant.ISENABLREFRESH)
        }
        Log.e(TAG, "mUrl: $mUrl")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mLibFragmentWebBinding = DataBindingUtil.inflate(inflater, R.layout.lib_fragment_web, container, false)
        initWebViews()
        return mLoadService.loadLayout
    }

    private fun initWebViews() {
        mLibFragmentWebBinding.smartrefreshlayout.setOnRefreshListener(this)
        mLibFragmentWebBinding.smartrefreshlayout.isEnableRefresh = isEnableRefresh
        mLibFragmentWebBinding.smartrefreshlayout.isEnableLoadMore = false

        mLibFragmentWebBinding.webview.registerWebCallback(this)
        mLibFragmentWebBinding.webview.loadUrl(mUrl)

        mLoadService = LoadSir.Builder()
                .addCallback(LoadingCallback())
                .addCallback(ErrorCallback())
                .addCallback(EmptyCallback())
                .build()
                .register(mLibFragmentWebBinding.smartrefreshlayout, Callback.OnReloadListener {
                    mLoadService.showCallback(LoadingCallback::class.java)
                    mLibFragmentWebBinding.webview.reload()
                })
    }

    override fun onReceivedTitle(title: String?) {
        if (activity is WebActivity) {
            (activity as WebActivity).setTitle(title ?: "")
        }
    }

    override fun onPageStarted(url: String?) {
        Log.e(TAG, "onPageStarted")
        mLoadService.showCallback(LoadingCallback::class.java)
    }

    override fun onPageFinished(url: String?) {
        Log.e(TAG, "onPageFinished isError:$isError")
        if (isError) {
            mLibFragmentWebBinding.smartrefreshlayout.isEnableRefresh = true
            mLoadService.showCallback(ErrorCallback::class.java)
        } else {
            mLibFragmentWebBinding.smartrefreshlayout.isEnableRefresh = isEnableRefresh
            mLoadService.showSuccess()
        }
    }

    override fun onReceivedError() {
        Log.e(TAG, "onReceivedError")
        isError = true

    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        Log.e(TAG, "onRefresh")
        mLibFragmentWebBinding.webview.reload()
    }
}