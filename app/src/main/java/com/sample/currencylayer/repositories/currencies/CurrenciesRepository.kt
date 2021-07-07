package com.sample.currencylayer.repositories.currencies

import com.sample.currencylayer.data.local.currencies.CurrencyItem
import com.sample.currencylayer.data.remote.responses.CurrenciesResponse
import com.sample.currencylayer.utils.Resource

interface CurrenciesRepository {

    suspend fun insertAllCurrencyItems(currencyItems: List<CurrencyItem>)

    suspend fun insertCurrencyItem(currencyItem: CurrencyItem)

    suspend fun deleteCurrencyItem(currencyItem: CurrencyItem)

    suspend fun deleteAllCurrencyItems()

    suspend fun getAllCurrencyItems(): List<CurrencyItem>

    suspend fun getCurrencies(): Resource<CurrenciesResponse>
}