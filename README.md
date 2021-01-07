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
    // 微信
    implementation 'com.tencent.mm.opensdk:wechat-sdk-android-without-mta:+'

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
        android:value="需要找通联的同事配置" />
    <!-- 通联app_secret -->
    <meta-data
        android:name="my_app_secret"
        android:value="需要找通联的同事配置" />
    <!-- 通联regisger_source 注册来源 -->
    <meta-data
        android:name="my_regisger_source"
        android:value="需要找通联的同事配置 " />
    <!-- 渠道 -->
    <meta-data
        android:name="CHANNEL"
        android:value="xiaomi" />

#### 4.gradle配置
通联aar包配置[ 具体依赖包在demo中 ]：

    android {
        repositories {
                flatDir {
                    //this way we can find the .aar file in libs folder
                    dirs 'libs'
                }
            }
    }

路由：

    // ARouter编译配置
    kapt {
        arguments {
            arg("AROUTER_MODULE_NAME", project.getName())
        }
    }
一键登录：

    android {
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
    }

    微信登录配置步骤：
    1.添加微信响应的页面WXEntryActivity，在 manifest 文件里面加上exported、taskAffinity及launchMode属性，
      其中exported设置为true，taskAffinity设置为你的包名，launchMode设置为singleTask
      注：WXEntryActivity目录位置需要在“包名.wxapi.WXEntryActivity”

        <activity
            android:name="包名.wxapi.WXEntryActivity"
            android:label="@string/app_name"
            android:theme="@android:style/Theme.Translucent.NoTitleBar"
            android:exported="true"
            android:taskAffinity="填写你的包名"
            android:launchMode="singleTask">
        </activity>

    2.授权登录逻辑在WeChatServiceImpl和WeixinHelper两个类中，请copy到项目中

    3.在应用合适位置进行微信注册初始化(可放在Application或MainActivity页面进行注册)
        WeixinHelper.registerToWx(this, "微信开放平台的appId")

    4.权限
    <uses-permission android:name="android.permission.INTERNET" />

    <!-- for mta statistics, not necessary-->
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>

    4.以上步骤执行完后，可在登录页面进行微信授权登录测试。

### (二).SDK初始化

    // 初始化通联数据环境
    DataYesCloud.INSTANCE.init(
        this,
        Environment.PRD, // 环境配置
        "9",  // 通联数据产品Id，需要通联云平台做配置
        "xiaomi",
        BuildConfig.DEBUG
    )

    // 初始化一键登录
    DataYesCloud.INSTANCE.initOneBtnLogin(this, BuildConfig.DEBUG)
    // 初始化x5 webView自动带有登录信息【非必须】
    DataYesCloud.INSTANCE.initWebView(this)


### (三).接口用户权限

用户权限是基于okhttp拦截器

##### 例子：

    /**
     *  retrofit 请求接口
     */
    private val service: IService? by lazy {
        // 带权限服务获取
        ApiServiceGenerator.createService(IService::class.java)
    }

    interface IService {
        /**
         * Rxjava类型接口
         */
        @GET("{subPath}/whitelist/banner/homepage")
        fun fetchHomeBannerInfo(
                @Path(value = "subPath", encoded = true) subPath: String,
                @Query("onlyFree") onlyFree: Boolean
        ): Observable<BaseRrpBean<List<HomeBannerBean>>>

    }

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
