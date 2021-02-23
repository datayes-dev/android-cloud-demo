package com.datayes.clouddemo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.datayes.common_bus.BusManager;
import com.datayes.common_bus.Subscribe;
import com.datayes.common_bus.ThreadMode;
import com.datayes.common_cloud.Cloud;
import com.datayes.common_cloud.user.User;
import com.datayes.common_cloud.user.UserInfoManager;
import com.datayes.common_cloud.user.UserManager;
import com.datayes.common_cloud.user.bean.AccountBean;
import com.datayes.common_cloud.user.event.LoginEvent;
import com.datayes.common_cloud.user.event.LogoutEvent;
import com.datayes.iia.module_common.base.BaseActivity;
import com.datayes.iia.module_common.base.rxjava.observer.NextErrorObserver;
import com.datayes.iia.module_common.base.rxjava.observer.NextObserver;
import com.datayes.iia.module_common.utils.RxJavaUtils;
import com.datayes.irr.rrp_api.RrpApiRouter;
import com.datayes.rrp.cloud.user.info.ProfileInfo;

import io.reactivex.annotations.NonNull;

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
            UserInfoManager.INSTANCE.hasBindMobile();
            UserInfoManager.INSTANCE.hasBindWeChat();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 处理小概率事件的补救措施
        if (User.INSTANCE.isLogin() && User.INSTANCE.getAccountBean() == null) {
            UserManager.INSTANCE.getAccountInfo()
                    .subscribe(new NextObserver<AccountBean>() {
                        @Override
                        public void onNext(@NonNull AccountBean accountBean) {
                            // 刷新ui
                            refreshUserName();
                        }
                    });
        }

        refreshUserName();
    }

    private void refreshUserName() {
        if (User.INSTANCE.isLogin()) {
            // 刷新用户名
            setUserNameView();
            // 时时拉取用户信息数据
            ProfileInfo.INSTANCE.refresh()
                    .compose(this.bindToLifecycle())
                    .compose(RxJavaUtils.observableIoToMain())
                    .subscribe(new NextErrorObserver<Boolean>() {
                        @Override
                        public void onNext(@NonNull Boolean aBoolean) {
                            setUserNameView();
                            // 获取头像url
                            String headIcon = Cloud.INSTANCE.getUserHeader(ProfileInfo.INSTANCE.getProfileBean().getPortrait());
                            // 刷新头像

                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            setUserNameView();
                        }
                    });
        } else {
            // 未登陆的逻辑
            TextView tvUserName = findViewById(R.id.tv_user_name);
            tvUserName.setText("为登录");
        }
    }

    private void setUserNameView() {
        TextView tvUserName = findViewById(R.id.tv_user_name);
        if (TextUtils.isEmpty(ProfileInfo.INSTANCE.getNickname())) {
            tvUserName.setText(ProfileInfo.INSTANCE.getNickname());
        } else {
            if (User.INSTANCE.getAccountBean() != null) {
                tvUserName.setText(User.INSTANCE.getAccountBean().getUserName());
            }
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
