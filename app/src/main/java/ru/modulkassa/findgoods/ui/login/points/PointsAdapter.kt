package ru.modulkassa.findgoods.ui.login.points

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_point.view.address
import kotlinx.android.synthetic.main.item_point.view.name
import ru.modulkassa.findgoods.R
import ru.modulkassa.findgoods.domain.point.RetailPoint

class PointsAdapter(
    private val items: ArrayList<RetailPoint>,
    private val listener: (point: RetailPoint) -> Unit
) : RecyclerView.Adapter<PointsViewHolder>() {
    override fun onBindViewHolder(holder: PointsViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_point, parent, false)
        return PointsViewHolder(itemView)
    }

    fun updateItems(items: List<RetailPoint>) {
        this.items.clear()
        addItems(items)
    }

    fun addItems(items: List<RetailPoint>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}

class PointsViewHolder(itemView: View) : ViewHolder(itemView) {
    fun bind(point: RetailPoint, listener: (point: RetailPoint) -> Unit) {
        itemView.name.text = point.name
        itemView.address.text = point.address
        itemView.setOnClickListener { listener(point) }
    }
}