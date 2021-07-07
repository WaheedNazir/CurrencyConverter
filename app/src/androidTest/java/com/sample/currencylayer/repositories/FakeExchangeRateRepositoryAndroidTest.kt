package com.sample.currencylayer.repositories

import androidx.lifecycle.MutableLiveData
import com.sample.currencylayer.data.local.currencies.CurrencyItem
import com.sample.currencylayer.data.local.exchangerates.ExchangeItem
import com.sample.currencylayer.data.remote.responses.ExchangeRatesResponse
import com.sample.currencylayer.utils.Resource
import com.sample.currencylayer.repositories.exchangerate.ExchangeRateRepository

class FakeExchangeRateRepositoryAndroidTest : ExchangeRateRepository {

    private val _exchangeRate = mutableListOf<ExchangeItem>()
    private val exchangeRate = MutableLiveData<List<ExchangeItem>>(_exchangeRate)


    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData() {
        exchangeRate.postValue(_exchangeRate)
    }

    override suspend fun insertAllExchangeItems(exchangeItems: List<ExchangeItem>) {
        _exchangeRate.addAll(exchangeItems)
        refreshLiveData()
    }

    override suspend fun insertExchangeItem(exchangeItem: ExchangeItem) {
        _exchangeRate.add(exchangeItem)
        refreshLiveData()
    }

    override suspend fun deleteExchangeItem(exchangeItem: ExchangeItem) {
        _exchangeRate.remove(exchangeItem)
        refreshLiveData()
    }

    override suspend fun deleteAllExchangeItems() {
        _exchangeRate.clear()
        refreshLiveData()
    }

    override suspend fun deleteAllExchangeItems(selectedSourceCurrency: String) {
        _exchangeRate.forEach { item ->
            if (item.sourceCurrency == selectedSourceCurrency) {
                _exchangeRate.remove(item)
            }
        }
        refreshLiveData()
    }

    override suspend fun getAllExchangeItems(): List<ExchangeItem> {
        return _exchangeRate
    }

    override suspend fun getAllExchangeItems(selectedSourceCurrency: String): List<ExchangeItem> {
        return _exchangeRate.filter { item -> item.sourceCurrency == selectedSourceCurrency }
    }

    /**
     * Get Popular Article
     *   getExchangeRates(source: String): Resource<ExchangeRates>
     */
    override suspend fun getExchangeRates(source: String): Resource<ExchangeRatesResponse> {
        return if (shouldReturnNetworkError) {
            Resource.error("Error", null)

        } else {
            val qoutes = hashMapOf<String, Double>()
            qoutes["USDAED"] = 3.673199
            qoutes["USDAFN"] = 79.722894
            val response = ExchangeRatesResponse(quotes = qoutes, source, true, 1212121)
            Resource.success(response)
        }
    }
}