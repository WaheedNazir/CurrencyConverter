package com.sample.currencylayer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sample.currencylayer.data.local.currencies.CurrencyDao
import com.sample.currencylayer.data.local.currencies.CurrencyItem
import com.sample.currencylayer.data.local.exchangerates.ExchangeItem
import com.sample.currencylayer.data.local.exchangerates.ExchangeRatesDao

@Database(
    entities = [CurrencyItem::class, ExchangeItem::class],
    version = 1
)
abstract class CurrencyConverterDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    abstract fun exchangeRatesDao(): ExchangeRatesDao
}