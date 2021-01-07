package com.datayes.clouddemo.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.datayes.clouddemo.R;
import com.datayes.clouddemo.weixin.WeixinHelper;
import com.datayes.common_bus.BusManager;
import com.datayes.common_utils.log.LogUtils;
import com.datayes.common_utils.toast.ToastUtils;
import com.datayes.irr.rrp_api.login.EPlatform;
import com.datayes.irr.rrp_api.login.ThirdPartyLoginEvent;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.SubscribeMessage;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.json.JSONObject;

/**
 * 微信回调activity
 * Created by shenen.gao on 2018/3/2.
 *
 * @author shenen.gao
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wx_entry);
        LogUtils.i("WXEntryActivity create wx callback activity");

        api = WeixinHelper.INSTANCE.getWxApi();
        if (api != null) {
            api.handleIntent(this.getIntent(), this);
        } else {
            finish();
        }
    }

    @Override
    protected final void onNewIntent(Intent paramIntent) {
        LogUtils.d("WXEntryActivity WXCallbackActivity   onNewIntent");
        super.onNewIntent(paramIntent);
        this.setIntent(paramIntent);
        api.handleIntent(paramIntent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        try {
            if (baseReq.getType() == ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX) {
                // 微信启动app
                if (baseReq instanceof ShowMessageFromWX.Req) {
                    ShowMessageFromWX.Req showReq = (ShowMessageFromWX.Req) baseReq;
                    WXMediaMessage mediaMsg = showReq.message;
                    String extInfo = mediaMsg.messageExt;
                    LogUtils.i("微信onReq extInfo：" + extInfo);

                    JSONObject jsonObject = new JSONObject(extInfo);
                    if (jsonObject.has("url")) {
                        String url = jsonObject.getString("url");
                        onJumpWeChartOpenPage(url);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.finish();
    }

    protected void onJumpWeChartOpenPage(String url) {
        if (!TextUtils.isEmpty(url)) {
            // 页面跳转
            ARouter.getInstance().build(Uri.parse(url))
                    .navigation();
        }
    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp instanceof SubscribeMessage.Resp) {
            SubscribeMessage.Resp mesResp = (SubscribeMessage.Resp) baseResp;
            ThirdPartyLoginEvent event = new ThirdPartyLoginEvent(EPlatform.WEIXIN, mesResp.errCode, "");
            event.setAction(mesResp.action);
            event.setOpenId(mesResp.openId);
            event.setReserved(mesResp.reserved);
            event.setScene(mesResp.scene);
            event.setTemplateId(mesResp.templateID);
            BusManager.getBus().post(event);
        } else {
            // 授权登录
            if (baseResp.getType() == 1) {

                switch (baseResp.errCode) {
                    case BaseResp.ErrCode.ERR_AUTH_DENIED:

                        ToastUtils.showLongToastSafe(this, "登录失败：授权未成功");

                        break;
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        ToastUtils.showLongToastSafe(this, "登录失败：用户取消登录");
                        break;
                    case BaseResp.ErrCode.ERR_OK:
                        SendAuth.Resp resp = (SendAuth.Resp) baseResp;
                        BusManager.getBus().post(new ThirdPartyLoginEvent(EPlatform.WEIXIN, baseResp.errCode, resp.code));
                        break;
                    default:

                        break;
                }
            }
        }

        this.finish();
    }

}
