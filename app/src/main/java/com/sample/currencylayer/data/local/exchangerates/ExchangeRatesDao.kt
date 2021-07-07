package com.sample.currencylayer.data.local.exchangerates

import androidx.room.*

@Dao
interface ExchangeRatesDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencyItems(exchangeItems: List<ExchangeItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExchangeItem(exchangeItem: ExchangeItem)

    @Delete
    suspend fun deleteExchangeItem(exchangeItem: ExchangeItem)

    @Query("DELETE FROM exchange_items")
    suspend fun deleteAllCurrencyItems()

    @Query("DELETE FROM exchange_items WHERE sourceCurrency = :selectedSourceCurrency")
    suspend fun deleteAllCurrencyItems(selectedSourceCurrency: String)

    @Query("SELECT * FROM exchange_items")
    fun getAllExchangeItems(): List<ExchangeItem>

    @Query("SELECT * FROM exchange_items WHERE sourceCurrency = :selectedSourceCurrency")
    fun getAllExchangeItems(selectedSourceCurrency: String): List<ExchangeItem>

}