package com.datayes.clouddemo

import android.app.Application
import com.datayes.clouddemo.weixin.WeixinHelper
import com.datayes.common.net.Environment
import com.datayes.rrp.cloud.DataYesCloud

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // 微信注册
        WeixinHelper.registerToWx(this, "微信开放平台注册的appId")
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