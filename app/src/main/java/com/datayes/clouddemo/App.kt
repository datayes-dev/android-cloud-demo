package com.datayes.clouddemo

import android.app.Application
import cn.udesk.DataYesUDesk
import com.datayes.common.net.Environment
import com.datayes.iia.fund.DyFund
import com.datayes.iia.module_common.ModuleCommon
import com.datayes.iia.module_common.ModuleManager
import com.datayes.iia.module_common.base.x5webview.WebViewJsManager
import com.datayes.iia.module_common.base.x5webview.X5WebViewManager
import com.datayes.rrp.cloud.DataYesCloud
import com.datayes.servicethirdparty.ServiceThirdParty
import com.datayes.servicethirdparty.ShareJsCallNative

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // 微信注册
//        WeixinHelper.registerToWx(this, "wxc6773613f4e5e17e") 这个删除
        // 初始化第三方组件
        ServiceThirdParty.INSTANCE
            .initWeixin(this, "wxc6773613f4e5e17e")
            .enableShareComponent(true, false, false, false)
            .apply {
                try {
                    // 替换成微博AppKey
                    // initSina(this@App, "**", "https://api.weibo.com/oauth2/default.html", "")
                } catch (e: Error) {
                    e.printStackTrace()
                }
            }
        // 分享到微信的豆腐块icon
        ServiceThirdParty.INSTANCE.shareIconRes = R.drawable.common_ic_share_icon
        // 支持分享js回调
        WebViewJsManager.INSTANCE.registerJsHandler(ShareJsCallNative())

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
        // 是否是萝卜理财
        DyFund.INSTANCE.isRobotFund = true
        // 设置服务子url
        DyFund.INSTANCE.fundNetSubUrl = when (ModuleCommon.INSTANCE.environment) {
            Environment.STG -> "/rrp_fund_stg"
            else -> "/rrp_fund"
        }
        // h5 url配置
        DyFund.INSTANCE.webBaseUrl = "https://m-robo.datayes.com"

        // webview 添加通华的白名单
        X5WebViewManager.INSTANCE.addDefaultJumpNewWhiteList("tonghuafund.com.cn")
        // 客服系统初始化
        DataYesUDesk.INSTANCE.init(
            this, "datayes.udesk.cn",
            "e04648492d27e4de1e4b5367a2935754", "0f2ea5028bdf8710"
        )
    }


}