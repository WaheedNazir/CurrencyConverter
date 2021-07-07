package com.sample.currencylayer.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sample.currencylayer.MainCoroutineRule
import com.sample.currencylayer.utils.Status
import com.sample.currencylayer.ui.exchangeRate.ExchangeRateViewModel
import com.google.common.truth.Truth.assertThat
import com.sample.currencylayer.data.local.currencies.CurrencyItem
import com.sample.currencylayer.data.local.exchangerates.ExchangeItem
import com.sample.currencylayer.getOrAwaitValueTest
import com.sample.currencylayer.utils.Resource
import com.sample.currencylayer.repositories.FakeExchangeRateRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ExchangeRateViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ExchangeRateViewModel

    @Before
    fun setup() {
        viewModel = ExchangeRateViewModel(FakeExchangeRateRepository())
    }

    @Test
    fun `insert exchange rates check loading state, returns success`() {
        viewModel.updateExchangeRatesLiveData(Resource(Status.LOADING, ArrayList(), ""))
        val value = viewModel.exchangeRates.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.LOADING)
    }

    @Test
    fun `insert exchange rates with invalid response, returns error`() {
        viewModel.updateExchangeRatesLiveData(Resource(Status.ERROR, null, ""))
        val value = viewModel.exchangeRates.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert exchange rates with valid response, returns success`() {
        val exchangeRates: ArrayList<ExchangeItem> = ArrayList()
        exchangeRates.add(ExchangeItem(1, "USD", "AED", 3.673199))
        exchangeRates.add(ExchangeItem(2, "USD", "AFN", 79.722894))

        val response = Resource(Status.SUCCESS, exchangeRates, "")
        viewModel.updateExchangeRatesLiveData(response)
        val value = viewModel.exchangeRates.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `insert exchange rates with valid response and verify data, returns error`() {
        val exchangeRates: ArrayList<ExchangeItem> = ArrayList()
        exchangeRates.add(ExchangeItem(1, "USD", "AED", 3.673199))
        val response = Resource(Status.SUCCESS, exchangeRates, "")
        viewModel.updateExchangeRatesLiveData(response)

        val value = viewModel.exchangeRates.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.data?.first()?.amount).isNotEqualTo(123)
    }

    @Test
    fun `insert exchange rates with valid response and verify data, returns success`() {
        val exchangeRates: ArrayList<ExchangeItem> = ArrayList()
        exchangeRates.add(ExchangeItem(1, "USD", "AED", 3.673199))
        val response = Resource(Status.SUCCESS, exchangeRates, "")
        viewModel.updateExchangeRatesLiveData(response)

        val value = viewModel.exchangeRates.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.data?.first()?.amount).isEqualTo(3.673199)
    }
}












