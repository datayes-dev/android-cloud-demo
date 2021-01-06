package com.datayes.clouddemo

import android.app.Application
import com.datayes.common.net.Environment
import com.datayes.iia.module_common.base.x5webview.X5WebViewManager
import com.datayes.rrp.cloud.DataYesCloud

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // 初始化通联数据环境
        DataYesCloud.INSTANCE.init(
            this,
            Environment.STG, // 环境配置
            "9",  // 通联数据产品Id，需要通联云平台做配置
            "xiaomi",
            BuildConfig.DEBUG
        )

        // 初始化一键登录
        DataYesCloud.INSTANCE.initOneBtnLogin(this, BuildConfig.DEBUG)
        // 初始化x5 webView自动带有登录信息【非必须】
        DataYesCloud.INSTANCE.initWebView(this)
    }
}