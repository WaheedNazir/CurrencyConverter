package com.sample.currencylayer.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sample.currencylayer.MainCoroutineRule
import com.sample.currencylayer.utils.Status
import com.sample.currencylayer.repositories.FakeCurrenciesRepository
import com.google.common.truth.Truth.assertThat
import com.sample.currencylayer.data.local.currencies.CurrencyItem
import com.sample.currencylayer.getOrAwaitValueTest
import com.sample.currencylayer.utils.Resource
import com.sample.currencylayer.ui.currencies.CurrenciesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CurrenciesResponseViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: CurrenciesViewModel

    @Before
    fun setup() {
        viewModel = CurrenciesViewModel(FakeCurrenciesRepository())
    }

    @Test
    fun `insert currencies check loading state, returns success`() {
        viewModel.updateCurrenciesLiveData(Resource(Status.LOADING, ArrayList(), ""))
        val value = viewModel.currencies.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.LOADING)
    }

    @Test
    fun `insert currencies with invalid response, returns error`() {
        viewModel.updateCurrenciesLiveData(Resource(Status.ERROR, null, ""))
        val value = viewModel.currencies.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert currencies with valid response, returns success`() {
        val exchangeRates: ArrayList<CurrencyItem> = ArrayList()
        exchangeRates.add(CurrencyItem(1, "AED"))
        exchangeRates.add(CurrencyItem(2, "AFN"))
        val response = Resource(Status.SUCCESS, exchangeRates, "")
        viewModel.updateCurrenciesLiveData(response)
        val value = viewModel.currencies.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `insert currencies with valid response and verify data, returns error`() {
        val exchangeRates: ArrayList<CurrencyItem> = ArrayList()
        exchangeRates.add(CurrencyItem(1, "AED"))
        val response = Resource(Status.SUCCESS, exchangeRates, "")
        viewModel.updateCurrenciesLiveData(response)

        val value = viewModel.currencies.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.data?.first()?.currency).isNotEqualTo("USD")
    }

    @Test
    fun `insert currencies with valid response and verify data, returns success`() {
        val exchangeRates: ArrayList<CurrencyItem> = ArrayList()
        exchangeRates.add(CurrencyItem(1, "AED"))
        val response = Resource(Status.SUCCESS, exchangeRates, "")
        viewModel.updateCurrenciesLiveData(response)

        val value = viewModel.currencies.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.data?.first()?.currency).isEqualTo("AED")
    }
}












