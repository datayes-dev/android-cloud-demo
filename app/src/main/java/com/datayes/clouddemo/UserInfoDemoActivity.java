package com.datayes.clouddemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.datayes.common_bus.BusManager;
import com.datayes.common_bus.Subscribe;
import com.datayes.common_bus.ThreadMode;
import com.datayes.common_cloud.user.User;
import com.datayes.common_cloud.user.UserInfoManager;
import com.datayes.common_cloud.user.UserManager;
import com.datayes.common_cloud.user.bean.AccountBean;
import com.datayes.common_cloud.user.event.LoginEvent;
import com.datayes.common_cloud.user.event.LogoutEvent;
import com.datayes.iia.module_common.base.BaseActivity;
import com.datayes.irr.rrp_api.RrpApiRouter;

/**
 * 用户信息demo
 *
 * @author shenen.gao
 */
public class UserInfoDemoActivity extends BaseActivity {

    //TODO 建议使用BaseActivity，或者继承与BaseActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_demo);
        // TODO 需要监听的地方注册通知
        BusManager.getBus().register(this);

        UserManager.INSTANCE.loginOut();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO 解绑通知
        // ！！！！！！ 这里非常非常重要，防止内存泄漏
        BusManager.getBus().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN_THREAD)
    public void onLogin(LoginEvent event) {
        Toast.makeText(this, "接受到登录成功通知！！", Toast.LENGTH_LONG).show();

        // 判断登录
        User.INSTANCE.isLogin();
        // 获取用户信息
        if (User.INSTANCE.isLogin()) {
            AccountBean user = User.INSTANCE.getAccountBean();
            // 用户名
            user.getUserName();
            // 用户信息
            UserInfoManager.INSTANCE.getBindWeChat();
            UserInfoManager.INSTANCE.getMobile();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN_THREAD)
    public void onLogout(LogoutEvent event) {
        Toast.makeText(this, "登出成功了！！", Toast.LENGTH_LONG).show();
    }

    @Override
    protected int getContentLayoutRes() {
        return R.layout.activity_user_info_demo;
    }

    public void onLoginClick(View v) {
        if (User.INSTANCE.isLogin()) {
            Toast.makeText(this, "已登录", Toast.LENGTH_SHORT).show();
        } else {
            ARouter.getInstance().build(RrpApiRouter.LOGIN_PAGE).navigation();
        }
    }

}
