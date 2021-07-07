package com.sample.currencylayer.di

import android.content.Context
import androidx.room.Room
import com.sample.currencylayer.BuildConfig
import com.sample.currencylayer.data.local.CurrencyConverterDatabase
import com.sample.currencylayer.data.local.currencies.CurrencyDao
import com.sample.currencylayer.data.local.exchangerates.ExchangeRatesDao
import com.sample.currencylayer.data.remote.APIs
import com.sample.currencylayer.repositories.currencies.DefaultCurrenciesRepository
import com.sample.currencylayer.repositories.currencies.CurrenciesRepository
import com.sample.currencylayer.repositories.exchangerate.DefaultExchangeRateRepository
import com.sample.currencylayer.repositories.exchangerate.ExchangeRateRepository
import com.sample.currencylayer.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDefaultCurrenciesRepository(currencyDao: CurrencyDao, api: APIs) =
        DefaultCurrenciesRepository(currencyDao, api) as CurrenciesRepository

    @Singleton
    @Provides
    fun provideDefaultExchangeRateRepository(exchangeRatesDao: ExchangeRatesDao, api: APIs) =
        DefaultExchangeRateRepository(exchangeRatesDao, api) as ExchangeRateRepository


    @Singleton
    @Provides
    fun provideNytApi(): APIs {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
            .create(APIs::class.java)
    }


    @Singleton
    @Provides
    fun provideCurrencyConverterDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context, CurrencyConverterDatabase::class.java,
        DATABASE_NAME
    ).build()


    @Singleton
    @Provides
    fun provideCurrencyDao(
        converterDatabase: CurrencyConverterDatabase
    ) = converterDatabase.currencyDao()


    @Singleton
    @Provides
    fun provideExchangeRatesDao(
        converterDatabase: CurrencyConverterDatabase
    ) = converterDatabase.exchangeRatesDao()

}

















