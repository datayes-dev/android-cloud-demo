package com.datayes.clouddemo.request

import com.datayes.iia.module_common.base.bean.BaseRrpBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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