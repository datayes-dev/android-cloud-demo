package com.datayes.clouddemo.webview;

import android.os.Bundle;

import com.datayes.clouddemo.R;
import com.datayes.iia.module_common.base.BaseTitleActivity;
import com.datayes.iia.module_common.base.x5webview.base.BaseX5WebViewClient;
import com.tencent.smtt.sdk.WebView;

/**
 * webview demo
 *
 * @author shenen.gao
 */
public class WebViewDemoActivity extends BaseTitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO 通联webview使用方式跟原生几乎一样，底层是基于 腾讯x5 webview
        // com.datayes.iia.module_common.base.x5webview.base.BaseX5WebView

        WebView webView = findViewById(R.id.common_webview);

        // TODO js, native 交互跟原生一样
        webView.addJavascriptInterface(this, "testest");

        // TODO webviewClient 使用这个，或者继承与这个
        webView.setWebViewClient(new BaseX5WebViewClient(webView) {

        });
        webView.loadUrl("https://m-robo.datayes.com/feed/detail?id=1000");
    }

    @Override
    protected int getContentLayoutRes() {
        return R.layout.test_webview_demo_activity;
    }



}
