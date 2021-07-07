package com.sample.currencylayer.ui.currencies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.sample.currencylayer.R
import com.sample.currencylayer.data.local.currencies.CurrencyItem
import com.sample.currencylayer.utils.CurrenciesSearchFilter
import kotlinx.android.synthetic.main.item_currencies.view.*
import javax.inject.Inject

class CurrenciesAdapter @Inject constructor() :
    RecyclerView.Adapter<CurrenciesAdapter.ExchangeRateViewHolder>(),
    Filterable {

    class ExchangeRateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var currenciesFiltered: ArrayList<CurrencyItem> = ArrayList()

    private var currencyItems: ArrayList<CurrencyItem> = ArrayList()

    fun submitList(currencyItems: List<CurrencyItem>) {
        currenciesFiltered.clear()
        currenciesFiltered.addAll(currencyItems)
        this.currencyItems.clear()
        this.currencyItems.addAll(currencyItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeRateViewHolder {
        return ExchangeRateViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_currencies,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = currenciesFiltered.size

    var onCurrencyClicked: ((CurrencyItem) -> Unit)? = null

    override fun onBindViewHolder(holder: ExchangeRateViewHolder, position: Int) {
        val currency = currenciesFiltered[position]

        holder.itemView.tvCurrency.text = String.format("%s", currency.currency)
        holder.itemView.tvCurrencyLabel.text = String.format("%s", currency.currencyLabel)

        holder.itemView.setOnClickListener {
            onCurrencyClicked?.invoke(currency)
        }
    }

    /**
     * Swap function to set new data on updating
     */
    fun updateSearchResults(items: List<CurrencyItem>) {
        currenciesFiltered.clear()
        currenciesFiltered.addAll(items)
        notifyDataSetChanged()
    }

    /**
     * Search function would be triggered from here
     */
    override fun getFilter(): Filter {
        return CurrenciesSearchFilter(this, currencyItems)
    }
}