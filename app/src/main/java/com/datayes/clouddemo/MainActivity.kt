package com.datayes.clouddemo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.alibaba.android.arouter.launcher.ARouter
import com.datayes.common_cloud.user.User
import com.datayes.common_cloud.user.UserManager
import com.datayes.iia.module_common.base.BaseActivity
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

    fun onRequestDemo(v: View) {

    }

}