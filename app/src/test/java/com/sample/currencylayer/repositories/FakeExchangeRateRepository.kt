package com.sample.currencylayer.repositories

import androidx.lifecycle.MutableLiveData
import com.sample.currencylayer.data.local.currencies.CurrencyItem
import com.sample.currencylayer.data.local.exchangerates.ExchangeItem
import com.sample.currencylayer.data.remote.responses.ExchangeRatesResponse
import com.sample.currencylayer.utils.Resource
import com.sample.currencylayer.repositories.exchangerate.ExchangeRateRepository

class FakeExchangeRateRepository : ExchangeRateRepository {
    private val currencies = mutableListOf<CurrencyItem>()
    private val observableCurrencies = MutableLiveData<List<CurrencyItem>>(currencies)


    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun insertAllExchangeItems(exchangeItems: List<ExchangeItem>) {
    }

    override suspend fun insertExchangeItem(exchangeItem: ExchangeItem) {
    }

    override suspend fun deleteExchangeItem(exchangeItem: ExchangeItem) {
    }

    override suspend fun deleteAllExchangeItems() {
    }

    override suspend fun deleteAllExchangeItems(selectedSourceCurrency: String) {
    }

    override suspend fun getAllExchangeItems(): List<ExchangeItem> {
        return emptyList()
    }

    override suspend fun getAllExchangeItems(selectedSourceCurrency: String): List<ExchangeItem> {
        return emptyList()
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