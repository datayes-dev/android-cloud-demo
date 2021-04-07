package com.datayes.clouddemo

import android.app.Application
import com.datayes.clouddemo.weixin.WeixinHelper
import com.datayes.common.net.Environment
import com.datayes.iia.fund.DyFund
import com.datayes.iia.module_common.ModuleCommon
import com.datayes.iia.module_common.ModuleManager
import com.datayes.rrp.cloud.DataYesCloud

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // 微信注册
        WeixinHelper.registerToWx(this, "wxc6773613f4e5e17e")
        // 初始化通联数据环境
        DataYesCloud.INSTANCE.init(
            this,
            Environment.QA, // 环境配置
            "9",  // 通联数据产品Id，需要通联云平台做配置
            "xiaomi",
            BuildConfig.DEBUG
        )

        // 初始化一键登录
        DataYesCloud.INSTANCE.initOneBtnLogin(this, BuildConfig.DEBUG)
        // 初始化x5 webView自动带有登录信息【非必须】
        DataYesCloud.INSTANCE.initWebView(this)
        // 初始化基金
        ModuleManager.INSTANCE.register(DyFund.INSTANCE)
        // 基金禁止feed功能
        DyFund.INSTANCE.enableFeed = false
        // 禁止分享
        DyFund.INSTANCE.shareEnable = false
        // 设置服务子url
        DyFund.INSTANCE.fundNetSubUrl = when (ModuleCommon.INSTANCE.environment) {
            Environment.STG -> "/rrp_fund_stg"
            else -> "/rrp_fund"
        }
        // h5 url配置
        DyFund.INSTANCE.webBaseUrl = "https://m-robo.datayes.com"
    }
}