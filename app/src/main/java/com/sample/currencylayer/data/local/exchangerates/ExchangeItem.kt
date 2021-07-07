package com.sample.currencylayer.data.local.exchangerates

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exchange_items")
data class ExchangeItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val sourceCurrency: String = "",
    val currency: String = "",
    val amount: Double = 0.0
)