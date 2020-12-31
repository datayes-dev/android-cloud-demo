# 通联数据-基础服务-脚手架项目
## 一.接入指南

### (一).依赖

#### 1.依赖镜像：

    repositories {
        maven { url 'https://maven.aliyun.com/repository/google' }
        maven { url 'https://maven.aliyun.com/repository/jcenter' }
        maven { url "https://jitpack.io" }
    }

#### 2.依赖库

以下依赖是必须的，但是版本可以按照自己的项目情况修改

    // 原生依赖
    implementation 'androidx.appcompat:appcompat:1.2.0'
    implementation 'androidx.multidex:multidex:2.0.0'
    implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
    implementation 'androidx.core:core-ktx:1.3.2'
    // UI组件
    implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
    implementation 'androidx.recyclerview:recyclerview:1.1.0'
    implementation 'com.google.android.material:material:1.2.1'
    implementation 'com.flyco.tablayout:FlycoTabLayout_Lib:2.1.2@aar'
    implementation "com.github.hackware1993:MagicIndicator:1.6.0"
    implementation "in.srain.cube:ultra-ptr:1.0.11"
    implementation "de.hdodenhof:circleimageview:3.1.0"
    implementation 'androidx.cardview:cardview:1.0.0'
    // x5 webview
    implementation 'com.tencent.tbs.tbssdk:sdk:43939'
    // greenDao
    implementation "org.greenrobot:greendao:3.3.0"
    // okHttp
    implementation 'com.squareup.okhttp3:okhttp:4.9.0'
    // retrofit
    implementation "com.squareup.retrofit2:retrofit:2.9.0"
    implementation "com.squareup.retrofit2:converter-gson:2.9.0"
    implementation "com.squareup.retrofit2:converter-scalars:2.9.0"
    // glide
    implementation "com.github.bumptech.glide:glide:4.10.0"
    // rxjava
    implementation 'io.reactivex.rxjava2:rxjava:2.2.12'
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'
    implementation "com.trello.rxlifecycle3:rxlifecycle-android:3.1.0"
    implementation "com.trello.rxlifecycle3:rxlifecycle-components:3.1.0"
    implementation "com.jakewharton.rxbinding2:rxbinding:2.2.0"
    implementation "com.github.tbruyelle:rxpermissions:0.10.2"
    // router
    implementation "com.alibaba:arouter-api:1.5.1"
    kapt "com.alibaba:arouter-compiler:1.5.1"
    // 一键登录
    implementation 'cn.jiguang.sdk:jcore:2.4.2'
    implementation 'cn.jiguang.sdk:jverification:2.6.5'

    // 通联基础组件
    implementation (name: 'module-common-1.28.22', ext: 'aar')
    implementation (name: 'common-view-0.9.3', ext: 'aar')
    implementation (name: 'common-bus-0.5.3', ext: 'aar')
    implementation (name: 'common-net-0.6.8', ext: 'aar')
    implementation (name: 'common-storage-0.5.0', ext: 'aar')
    implementation (name: 'common-utils-0.5.9', ext: 'aar')
    // 通联云平台服务
    implementation (name: 'common-cloud-0.10.11', ext: 'aar')
    // 通联登陆服务实现
    implementation (name: 'cloud-1.0.5', ext: 'aar')
    // 通联基础服务非实现（纯接口）
    implementation (name: 'rrp-api-1.6.18', ext: 'aar')

#### 3.通联登陆信息配置 AndroidManifest

    <!-- 通联app_id -->
    <meta-data
        android:name="my_app_id"
        android:value="ira" />
    <!-- 通联app_secret -->
    <meta-data
        android:name="my_app_secret"
        android:value="ira_secret" />
    <!-- 通联regisger_source 注册来源 -->
    <meta-data
        android:name="my_regisger_source"
        android:value="ira_secret " />
    <!-- 渠道 -->
    <meta-data
        android:name="CHANNEL"
        android:value="xiaomi" />

#### 4.一键登录配置

    defaultConfig {
        manifestPlaceholders = [
                // 包名需要与app包名相同
                JPUSH_PKGNAME: "com.datayes.irr",
                // 需要找通联的同事配置appkey
                JPUSH_APPKEY : "***********",
                // 暂时填写默认值即可.
                JPUSH_CHANNEL: "developer-default",
        ]
    }

### (二).SDK初始化

    // 初始化通联数据环境
    DataYesCloud.INSTANCE.init(
        this,
        Environment.STG,
        "9",  // 通联数据产品Id，需要通联云平台做配置
        "xiaomi",
        BuildConfig.DEBUG
    )

    // 初始化一键登录
    DataYesCloud.INSTANCE.initOneBtnLogin(this, BuildConfig.DEBUG)
    // 初始化x5 webView自动带有登录信息【非必须】
    X5WebViewManager.INSTANCE.initX5()


### (三).接口用户权限

用户权限是基于okhttp拦截器，可以参考Demo（AuthorizationInterceptor）


    // 初始化智能盯盘
    Starling.INSTANCE
            // okhttp用户拦截器
            .setUserInterceptor(new AuthorizationInterceptor())

##### 例子：

    /**
     * okhttp 用户权限拦截器
     * Created by shenen.gao on 2018/4/27.
     *
     * @author shenen.gao
     */

    public class AuthorizationInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();

            // TODO 用户id
            builder.addHeader("DatayesPrincipalName", "testestest11");

            // TODO deviceId
            builder.addHeader("deviceId", "deviceId");

            return chain.proceed(builder.build());
        }
    }


### (四).SDK需要实现的接口

接入者需要实现IExternalProvider接口，可以参考Demo（YiChuangExternalImpl）

    // 初始化智能盯盘
    Starling.INSTANCE
            // 设置回调
            .setExternalProvider(new YiChuangExternalImpl())

##### 需要实现的接口：

    @Override
    public List<StockBean> getSelfStockList() {

        // TODO 获取自选股列表，这里调用比较频繁，最好是取缓存
        return getStockBeans();
    }

    @Override
    public void openStockDetail(Context context, String ticker, String market) {

        // TODO  打开股票详情页面
        ToastUtils.showShortToast(context, "打开股票详情页ticker: " + ticker);
    }

    @Override
    public String getWebSocketUrl() {
        // TODO 获取长链接url, 需要保证url token的正确性
        return Test.INSTANCE.getWebSocketUrl();
    }

##### 股票数据结构：

    new StockBean(
        "000002.XSHE", // 证券ID
        "000002",  // 股票代码 (必须)
        "万科A", // 名称 (必须)
        "WKA", // 证券简称拼音
        "XSHE", // 交易市场  XSHE, XSHG (必须)
        "E", // 证券类型(股票，债券)
        "L",// L-上市，S-暂停，DE-终止上市，UN-未上市
        "",
        "");

### (五).智能盯盘入口

个股异动入口view (IiaStarlingStockChgView)

    // 必须调用start开始才会启动长链接
    IiaStarlingStockChgView.start();

    // 必须调用stop才会停止长链接
    // 未及时断开会产生不必要的性能消耗
    IiaStarlingStockChgView.stop();


板块异动入口view (IiaStarlingAreaChangeView)

    // 必须调用start开始才会启动长链接
    IiaStarlingAreaChangeView.start();

    // 必须调用stop才会停止长链接
    // 未及时断开会产生不必要的性能消耗
    IiaStarlingAreaChangeView.stop();