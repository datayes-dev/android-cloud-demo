package com.datayes.clouddemo.fund

import android.content.Context
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.datayes.common_utils.Utils
import com.datayes.irr.rrp_api.fund.IFundTradeService
import io.reactivex.Observable

/**
 * 基金交易服务
 *
 * @author shenen.gao
 */
@Route(path = "/app_fund/fund/tradeService")
class FundTradeServiceImpl : IFundTradeService {

    override fun init(context: Context?) {
    }

    override fun goToTradePage(fundCode: String) {
        // TODO 返回是否支持基金交易
        Toast.makeText(Utils.getContext(), "跳转基金交易页面", Toast.LENGTH_SHORT).show()
    }

    override fun supportTrade(fundCode: String): Observable<Boolean> {
        // TODO 返回是否支持基金交易
        return Observable.just(true)
    }
}