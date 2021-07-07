package com.sample.currencylayer.data.local.currencies

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_items")
data class CurrencyItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val currency: String = "",
    val currencyLabel: String = ""
)