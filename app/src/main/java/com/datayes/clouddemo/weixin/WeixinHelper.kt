package com.datayes.clouddemo.weixin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.datayes.common_utils.log.LogUtils
import com.datayes.common_utils.toast.ToastUtils
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory

/**
 * 微信辅助类
 *
 * @author Mr.Taohf
 * @date 2021/1/7 10:50
 */
object WeixinHelper {

    /**
     * 微信开放API
     */
    var wxApi: IWXAPI? = null

    var appId: String? = null

    /**
     * 将应用AppId注册到微信
     */
    fun registerToWx(context: Context, appId: String) {
        this.appId = appId
        // 获取开放API实例
        wxApi = WXAPIFactory.createWXAPI(context, appId, true)

        // 动态监听微信启动来完成app的注册
        context.registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                // 将应用注册到微信
                val result = wxApi?.registerApp(appId)

                LogUtils.i("微信注册：" + if (result == true) "成功" else "失败")
            }
        }, IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP))
    }

    /**
     * 唤起微信授权
     */
    fun fetchWxAuth(context: Context) {
        if (wxApi == null) {
            ToastUtils.showShortToast(context, "应用还未注册到微信")
        } else {
            if (wxApi?.isWXAppInstalled == true) {
                val req = SendAuth.Req()
                req.scope = "snsapi_userinfo"
                req.state = "datayes_wx_login"
                val result: Boolean? = wxApi?.sendReq(req)

                LogUtils.i("微信result：" + if (result == true) "true" else "false")
            } else {
                ToastUtils.showShortToast(context, "请安装微信客户端")
            }
        }
    }

}