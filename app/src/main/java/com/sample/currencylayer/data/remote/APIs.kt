package com.sample.currencylayer.data.remote

import com.sample.currencylayer.BuildConfig
import com.sample.currencylayer.data.remote.responses.CurrenciesResponse
import com.sample.currencylayer.data.remote.responses.ExchangeRatesResponse
import com.sample.currencylayer.utils.Constants.CURRENCIES_END_POINT
import com.sample.currencylayer.utils.Constants.DEFAULT_SOURCE_CURRENCY
import com.sample.currencylayer.utils.Constants.LIVE_END_POINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIs {

    @GET(LIVE_END_POINT)
    suspend fun getExchangeRates(
        @Query("source") source: String = DEFAULT_SOURCE_CURRENCY,
        @Query("access_key") apiKey: String = BuildConfig.API_KEY
    ): Response<ExchangeRatesResponse>


    @GET(CURRENCIES_END_POINT)
    suspend fun getCurrencies(
        @Query("access_key") apiKey: String = BuildConfig.API_KEY
    ): Response<CurrenciesResponse>

}