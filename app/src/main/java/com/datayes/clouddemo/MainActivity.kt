package com.datayes.clouddemo

import android.content.Intent
import android.view.View
import android.widget.Toast
import com.alibaba.android.arouter.launcher.ARouter
import com.datayes.clouddemo.request.HomeBannerBean
import com.datayes.clouddemo.request.IService
import com.datayes.common_cloud.user.User
import com.datayes.common_cloud.user.UserManager
import com.datayes.common_utils.parse.GsonUtils
import com.datayes.iia.module_common.base.BaseActivity
import com.datayes.iia.module_common.base.bean.BaseRrpBean
import com.datayes.iia.module_common.base.rxjava.observer.NextErrorObserver
import com.datayes.iia.module_common.net.ApiServiceGenerator
import com.datayes.iia.module_common.utils.RxJavaUtils
import com.datayes.irr.rrp_api.ARouterPath
import com.datayes.irr.rrp_api.RrpApiRouter
import com.datayes.rrp.cloud.RouterPath

class MainActivity : BaseActivity() {

    override fun getContentLayoutRes() = R.layout.activity_main

    fun onLoginClick(view: View?) {
        if (User.INSTANCE.isLogin) {
            Toast.makeText(this, "已登录", Toast.LENGTH_SHORT).show()
        } else {
            ARouter.getInstance().build(RrpApiRouter.LOGIN_PAGE).navigation()
        }
    }

    fun onLogoutClick(view: View?) {
        // 登出
        UserManager.INSTANCE.loginOut()
        Toast.makeText(this, "登出成功", Toast.LENGTH_SHORT).show()
    }

    fun onOneBtnLoginClick(view: View?) {
        if (User.INSTANCE.isLogin) {
            Toast.makeText(this, "已登录", Toast.LENGTH_SHORT).show()
        } else {
            ARouter.getInstance().build(RrpApiRouter.LOGIN_PAGE)
                .withBoolean("useDialog", true)
                .navigation()
        }
    }

    fun showUserInfo(view: View?) {
        if (User.INSTANCE.isLogin) {
            ARouter.getInstance().build(ARouterPath.USER_INFO_V2_PAGE)
                .navigation()
        }
    }

    fun onBindClick(v: View) {
        if (User.INSTANCE.isLogin) {
            ARouter.getInstance().build(RouterPath.ACCOUNT_SECURITY)
                .navigation()
        }
    }

    /**
     *  retrofit 请求接口
     */
    private val service: IService? by lazy {
        // 带权限服务获取
        ApiServiceGenerator.createService(IService::class.java)
    }

    fun onUserInfo(v: View?) {
        startActivity(Intent(this, UserInfoDemoActivity::class.java))
    }

    fun onRequestDemo(v: View?) {
        // rxJava
        val serviceSubUrl = "/rrp_mammon/mobile"
        service?.fetchHomeBannerInfo(serviceSubUrl, true)
            ?.map {
                // todo 耗时操作
                it
            }
            ?.compose(RxJavaUtils.observableIoToMain())
            ?.subscribe(object : NextErrorObserver<BaseRrpBean<List<HomeBannerBean>>>() {
                override fun onError(e: Throwable) {

                }

                override fun onNext(t: BaseRrpBean<List<HomeBannerBean>>) {
                    val rep = GsonUtils.createGsonString(t)
                    Toast.makeText(baseContext, "请求返回：$rep", Toast.LENGTH_LONG)
                        .show()
                }
            })
    }

    fun onWebViewDemo(v: View?) {
        ARouter.getInstance()
            .build("/datayesiia/webview")
            .withString("url", "https://m-robo.datayes.com/feed/detail?id=1000")
            .navigation()
    }

}