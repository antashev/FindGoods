package ru.modulkassa.findgoods.ui.goods

import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_good.view.code
import kotlinx.android.synthetic.main.item_good.view.minPrice
import kotlinx.android.synthetic.main.item_good.view.name
import kotlinx.android.synthetic.main.item_good.view.price
import ru.modulkassa.findgoods.R
import ru.modulkassa.findgoods.domain.good.Good
import ru.modulkassa.findgoods.ui.shared.toCurrencyString

class GoodListAdapter(
    val items: ArrayList<Good>,
    val listener: (item: Good) -> Unit
) : Adapter<ListViewHolder>() {

    companion object {
        const val ITEM = 0
        const val LOADING = 1
    }

    var loading = false

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return if (viewType == ITEM) {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_good, parent, false)
            GoodItemListViewHolder(itemView)
        } else {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_load, parent, false)
            LoadingViewHolder(itemView)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val count = items.size - 1
        return if (position == count && loading) LOADING else ITEM
    }

    fun updateItems(items: List<Good>) {
        this.items.clear()
        addItems(items)
    }

    fun updateItem(item: Good) {
        val index = items.indexOfFirst { it.inventCode == item.inventCode }
        if (index != -1) {
            items[index] = item
            notifyItemChanged(index)
        }
    }

    fun addItems(items: List<Good>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}

sealed class ListViewHolder(itemView: View): ViewHolder(itemView) {
    open fun bind(item: Good, listener: (item: Good) -> Unit) {}
}
class GoodItemListViewHolder(itemView: View) : ListViewHolder(itemView) {
    override fun bind(item: Good, listener: (item: Good) -> Unit) = with(itemView) {
        name.text = item.name
        code.text = item.barcode
        val priceText = "${item.price.toCurrencyString()} \u20BD"
        price.text = priceText
        item.minPrice?.let {
            val minPriceText = "${it.toCurrencyString()} \u20BD"
            minPrice.text = minPriceText
        }
        setOnClickListener { listener(item) }
    }
}
class LoadingViewHolder(itemView: View): ListViewHolder(itemView)