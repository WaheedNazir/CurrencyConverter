package com.sample.currencylayer.repositories.exchangerate

import com.sample.currencylayer.data.local.exchangerates.ExchangeItem
import com.sample.currencylayer.data.remote.responses.ExchangeRatesResponse
import com.sample.currencylayer.utils.Resource

interface ExchangeRateRepository {

    suspend fun insertAllExchangeItems(exchangeItems: List<ExchangeItem>)

    suspend fun insertExchangeItem(exchangeItem: ExchangeItem)

    suspend fun deleteExchangeItem(exchangeItem: ExchangeItem)

    suspend fun deleteAllExchangeItems()

    suspend fun deleteAllExchangeItems(selectedSourceCurrency: String)

    suspend fun getAllExchangeItems(): List<ExchangeItem>

    suspend fun getAllExchangeItems(selectedSourceCurrency: String): List<ExchangeItem>

    suspend fun getExchangeRates(selectedSourceCurrency: String): Resource<ExchangeRatesResponse>
}