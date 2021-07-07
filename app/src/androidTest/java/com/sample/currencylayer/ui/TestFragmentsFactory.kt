package com.sample.currencylayer.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.sample.currencylayer.ui.currencies.CurrenciesAdapter
import com.sample.currencylayer.ui.exchangeRate.ExchangeRatesAdapter
import com.sample.currencylayer.ui.currencies.CurrenciesFragment
import com.sample.currencylayer.ui.exchangeRate.ExchangeRatesFragment
import javax.inject.Inject

class TestFragmentsFactory @Inject constructor(
    private val exchangeRatesAdapter: ExchangeRatesAdapter,
    private val currenciesAdapter: CurrenciesAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ExchangeRatesFragment::class.java.name -> ExchangeRatesFragment(exchangeRatesAdapter)
            CurrenciesFragment::class.java.name -> CurrenciesFragment(currenciesAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}