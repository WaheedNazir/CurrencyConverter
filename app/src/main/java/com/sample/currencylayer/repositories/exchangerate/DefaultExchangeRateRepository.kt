package com.sample.currencylayer.repositories.exchangerate

import com.sample.currencylayer.data.local.exchangerates.ExchangeItem
import com.sample.currencylayer.data.local.exchangerates.ExchangeRatesDao
import com.sample.currencylayer.data.remote.APIs
import com.sample.currencylayer.data.remote.responses.ExchangeRatesResponse
import com.sample.currencylayer.utils.Constants.NO_INTERNET
import com.sample.currencylayer.utils.Constants.SOMETHING_WENT_WRONG
import com.sample.currencylayer.utils.Resource
import javax.inject.Inject

class DefaultExchangeRateRepository @Inject constructor(
    private val exchangeRatesDao: ExchangeRatesDao,
    private val APIs: APIs
) : ExchangeRateRepository {


    override suspend fun insertExchangeItem(exchangeItem: ExchangeItem) {
        exchangeRatesDao.insertExchangeItem(exchangeItem)
    }

    override suspend fun insertAllExchangeItems(exchangeItems: List<ExchangeItem>) {
        exchangeRatesDao.insertCurrencyItems(exchangeItems)
    }

    override suspend fun deleteExchangeItem(exchangeItem: ExchangeItem) {
        exchangeRatesDao.deleteExchangeItem(exchangeItem)
    }

    override suspend fun deleteAllExchangeItems() {
        exchangeRatesDao.getAllExchangeItems()
    }

    override suspend fun deleteAllExchangeItems(selectedSourceCurrency: String) {
        exchangeRatesDao.deleteAllCurrencyItems(selectedSourceCurrency)
    }

    override suspend fun getAllExchangeItems(): List<ExchangeItem> {
        return exchangeRatesDao.getAllExchangeItems()
    }

    override suspend fun getAllExchangeItems(selectedSourceCurrency: String): List<ExchangeItem> {
        return exchangeRatesDao.getAllExchangeItems(selectedSourceCurrency)
    }

    /**
     * Get exchange rates
     */
    override suspend fun getExchangeRates(selectedSourceCurrency: String): Resource<ExchangeRatesResponse> {
        return try {
            val response = APIs.getExchangeRates(selectedSourceCurrency)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error(SOMETHING_WENT_WRONG, null)
            } else {
                Resource.error(SOMETHING_WENT_WRONG, null)
            }
        } catch (e: Exception) {
            Resource.error(NO_INTERNET, null)
        }
    }
}














