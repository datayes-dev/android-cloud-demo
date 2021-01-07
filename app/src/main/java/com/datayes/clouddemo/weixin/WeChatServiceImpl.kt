package com.datayes.clouddemo.weixin

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.datayes.irr.rrp_api.wechart.IWeChartService

/**
 * 微信Service
 *
 * @author Mr.Taohf
 * @date 2021/1/7 11:20
 */
@Route(path = "/clouddemo/auth/service")
class WeChatServiceImpl : IWeChartService {

    override fun init(context: Context) {
        // empty impl
    }

    override fun weiXinOauth(context: Context) {
        WeixinHelper.fetchWxAuth(context)
    }

}