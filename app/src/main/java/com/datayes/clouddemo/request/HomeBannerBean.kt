package com.datayes.clouddemo.request

import com.google.gson.annotations.SerializedName


/**
 * 首页Banner信息
 *
 * @author Mr.Taohf
 * @date 2020/8/31 13:33
 */
data class HomeBannerBean(
        @SerializedName("title")
        var title: String, // 黄金最新分析！影响黄金的宏观数据一览，上金所Au99.99收盘价为397.44元
        @SerializedName("jumpUrl")
        var jumpUrl: String, // http://m-robo-qa.datayes-stg.com/feed/detail?id=14644&type=0&roboAppPush=1
        @SerializedName("imgUrl")
        var imgUrl: String // https://robo-storage.datayes.com/feed/image/qa/20200720/2aef4052-4420-4a1c-9c5c-fe586ca4b445.png
)