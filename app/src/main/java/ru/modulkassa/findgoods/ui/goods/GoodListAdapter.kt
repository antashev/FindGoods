package ru.modulkassa.findgoods.ui.goods

import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_good.view.name
import ru.modulkassa.findgoods.R
import ru.modulkassa.findgoods.domain.good.Good

class GoodListAdapter(
    val items: ArrayList<Good>,
    val listener: (item: Good) -> Unit
) : Adapter<GoodItemListViewHolder>() {
    override fun onBindViewHolder(holder: GoodItemListViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodItemListViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_good, parent, false)
        return GoodItemListViewHolder(itemView)
    }

    fun updateItems(items: List<Good>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}

class GoodItemListViewHolder(itemView: View) : ViewHolder(itemView) {
    fun bind(item: Good, listener: (item: Good) -> Unit) = with(itemView) {
        name.text = item.name
        setOnClickListener { listener(item) }
    }
}
