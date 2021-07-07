package com.sample.currencylayer.repositories

import androidx.lifecycle.MutableLiveData
import com.sample.currencylayer.data.remote.responses.CurrenciesResponse
import com.sample.currencylayer.data.local.currencies.CurrencyItem
import com.sample.currencylayer.utils.Resource
import com.sample.currencylayer.repositories.currencies.CurrenciesRepository

class FakeCurrenciesRepository : CurrenciesRepository {

    private val currencies = mutableListOf<CurrencyItem>()
    private val observableCurrencies = MutableLiveData<List<CurrencyItem>>(currencies)


    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun insertAllCurrencyItems(currencyItems: List<CurrencyItem>) {
    }

    override suspend fun insertCurrencyItem(currencyItem: CurrencyItem) {
    }

    override suspend fun deleteCurrencyItem(currencyItem: CurrencyItem) {
    }

    override suspend fun deleteAllCurrencyItems() {
    }

    override suspend fun getAllCurrencyItems(): List<CurrencyItem> {
        return emptyList()
    }

    /**
     * Get Popular Article
     *  getCurrencies(): Resource<Currencies>
     */
    override suspend fun getCurrencies(): Resource<CurrenciesResponse> {
        return if (shouldReturnNetworkError) {
            Resource.error("Error", null)

        } else {
            val currenciesMap = hashMapOf<String, String>()
            currenciesMap["AED"] = "United Arab Emirates Dirham"
            currenciesMap["AFN"] = "Afghan Afghani"
            val response = CurrenciesResponse(currenciesMap, true)
            Resource.success(response)
        }
    }
}











