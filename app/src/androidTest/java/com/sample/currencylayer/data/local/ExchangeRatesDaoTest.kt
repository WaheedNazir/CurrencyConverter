package com.sample.currencylayer.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.sample.currencylayer.data.local.exchangerates.ExchangeItem
import com.sample.currencylayer.data.local.exchangerates.ExchangeRatesDao
import com.sample.currencylayer.utils.Constants
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ExchangeRatesDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named(Constants.DATABASE_NAME)
    lateinit var database: CurrencyConverterDatabase
    private lateinit var dao: ExchangeRatesDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.exchangeRatesDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertExchangeItem() = runBlockingTest {

        val exchangeItem = ExchangeItem(1, "USD", "PKR", 158.0)
        dao.insertExchangeItem(exchangeItem)

        val allShoppingItems = dao.getAllExchangeItems()

        assertThat(allShoppingItems).contains(exchangeItem)
    }

    @Test
    fun deleteExchangeItem() = runBlockingTest {
        val exchangeItem = ExchangeItem(1, "USD", "PKR", 158.0)
        dao.insertExchangeItem(exchangeItem)
        dao.deleteExchangeItem(exchangeItem)

        val allShoppingItems = dao.getAllExchangeItems()

        assertThat(allShoppingItems).doesNotContain(exchangeItem)
    }
}













