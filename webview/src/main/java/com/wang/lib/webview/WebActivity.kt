package com.wang.lib.webview

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.wang.lib.webview.databinding.LibActivityWebBinding
import com.wang.lib.webview.utils.Constant

class WebActivity : AppCompatActivity() {

    private val TAG: String = "WebActivity"

    lateinit var mLibActivityWebBinding: LibActivityWebBinding
    lateinit var mUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLibActivityWebBinding = DataBindingUtil.setContentView<LibActivityWebBinding>(this, R.layout.lib_activity_web)

        val title = intent.getStringExtra(Constant.TITLE) ?: ""
        mUrl = intent.getStringExtra(Constant.URL) ?: ""
        mLibActivityWebBinding.title.text = title
        mLibActivityWebBinding.back.setOnClickListener {
            finish()
        }
        initFragment()
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.web_fragment, WebFragment.newInstance(mUrl, false))
                .commitAllowingStateLoss()
        Log.e(TAG, "mUrl: $mUrl")
    }

    public fun setTitle(title: String) {
        mLibActivityWebBinding.title.text = title
    }
}