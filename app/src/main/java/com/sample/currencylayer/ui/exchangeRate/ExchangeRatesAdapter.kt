package com.sample.currencylayer.ui.exchangeRate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.sample.currencylayer.R
import com.sample.currencylayer.data.local.currencies.CurrencyItem
import com.sample.currencylayer.data.local.exchangerates.ExchangeItem
import com.sample.currencylayer.utils.ExchangeRateSearchFilter
import kotlinx.android.synthetic.main.item_exchange_rate.view.*
import javax.inject.Inject

class ExchangeRatesAdapter @Inject constructor() :
    RecyclerView.Adapter<ExchangeRatesAdapter.ExchangeRateViewHolder>(),
    Filterable {

    class ExchangeRateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var conversionsFiltered: ArrayList<ExchangeItem> = ArrayList()
    private var conversions: ArrayList<ExchangeItem> = ArrayList()
    private var inputAmount: Double = 1.0

    fun submitList(conversions: List<ExchangeItem>, inputAmount: Double) {
        this.inputAmount = inputAmount
        this.conversions.clear()
        this.conversions.addAll(conversions)
        conversionsFiltered.clear()
        conversionsFiltered.addAll(conversions)
        notifyDataSetChanged()
    }

    fun updateExchangeRate(inputAmount: Double) {
        this.inputAmount = inputAmount
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeRateViewHolder {
        return ExchangeRateViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_exchange_rate,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = conversionsFiltered.size

    override fun onBindViewHolder(holder: ExchangeRateViewHolder, position: Int) {
        val currency = conversionsFiltered[position]
        holder.itemView.apply {
            tvToCurrency.text = String.format("%s", currency.currency)
            tvConversionRate.text = String.format("%,.2f", (currency.amount * inputAmount))
        }
    }


    /**
     * Swap function to set new data on updating
     */
    fun updateSearchResults(items: List<ExchangeItem>) {
        conversionsFiltered.clear()
        conversionsFiltered.addAll(items)
        notifyDataSetChanged()
    }

    /**
     * Search function would be triggered from here
     */
    override fun getFilter(): Filter {
        return ExchangeRateSearchFilter(this, conversions)
    }
}