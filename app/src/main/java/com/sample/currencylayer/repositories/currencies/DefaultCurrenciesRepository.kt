package com.sample.currencylayer.repositories.currencies

import com.sample.currencylayer.data.local.currencies.CurrencyDao
import com.sample.currencylayer.data.local.currencies.CurrencyItem
import com.sample.currencylayer.data.remote.APIs
import com.sample.currencylayer.data.remote.responses.CurrenciesResponse
import com.sample.currencylayer.utils.Constants.NO_INTERNET
import com.sample.currencylayer.utils.Constants.SOMETHING_WENT_WRONG
import com.sample.currencylayer.utils.Resource
import javax.inject.Inject

class DefaultCurrenciesRepository @Inject constructor(
    private val currencyDao: CurrencyDao,
    private val APIs: APIs
) : CurrenciesRepository {

    override suspend fun insertAllCurrencyItems(currencyItems: List<CurrencyItem>) {
        currencyDao.insertCurrencyItems(currencyItems)
    }

    override suspend fun insertCurrencyItem(currencyItem: CurrencyItem) {
        currencyDao.insertCurrencyItem(currencyItem)
    }

    override suspend fun deleteCurrencyItem(currencyItem: CurrencyItem) {
        currencyDao.deleteCurrencyItem(currencyItem)
    }

    override suspend fun deleteAllCurrencyItems() {
        currencyDao.deleteAllCurrencyItems()
    }

    override suspend fun getAllCurrencyItems(): List<CurrencyItem> {
        return currencyDao.getAllCurrencyItems()
    }

    /**
     * Get currencies
     */
    override suspend fun getCurrencies(): Resource<CurrenciesResponse> {
        return try {
            val response = APIs.getCurrencies()
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












