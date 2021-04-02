package com.datayes.clouddemo

import android.content.Intent
import android.view.View
import com.datayes.iia.fund.main.FundMainActivity
import com.datayes.iia.fund.main.FundMainFragment
import com.datayes.iia.module_common.base.BaseTitleActivity

class FundDemoActivity : BaseTitleActivity() {
    override fun getContentLayoutRes() = R.layout.activity_fund_demo

    fun onFundActivity(v: View) {
        startActivity(Intent(this, FundMainActivity::class.java))
    }

    fun onFundFragment(v: View) {
        var fragment =
            supportFragmentManager.findFragmentByTag(FundMainFragment::class.java.simpleName)
        if (fragment == null) {
            fragment = FundMainFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl_fund_container, fragment)
                .commit()
        }
    }
}