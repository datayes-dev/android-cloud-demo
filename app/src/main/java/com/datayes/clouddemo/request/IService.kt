package com.datayes.clouddemo.request

import com.datayes.iia.module_common.base.bean.BaseRrpBean
import io.reactivex.Observable
import retrofit2.http.*


interface IService {
    /**
     * Rxjava类型接口
     */
    @GET("{subPath}/whitelist/banner/homepage")
    fun fetchHomeBannerInfo(
        @Path(value = "subPath", encoded = true) subPath: String,
        @Query("onlyFree") onlyFree: Boolean
    ): Observable<BaseRrpBean<List<HomeBannerBean>>>

    @POST("{subPath}/api/smartFof/recommend/list")
    fun getFoFList(
        @Path(value = "subPath", encoded = true) subPath: String?,
        @Body body: TestRequestBean
    ): Observable<Any>?


}