package ru.modulkassa.findgoods.domain.repository

import ru.modulkassa.findgoods.domain.good.GoodItem

class GoodItemRepository {
    val items: ArrayList<GoodItem> = ArrayList()

    fun getItems(): List<GoodItem> = items

    fun addItem(goodItem: GoodItem) {
        items.add(goodItem)
    }

    fun deleteItem(inventCode: String) {
        val index = items.indexOfFirst { it.inventCode == inventCode }
        if (index != -1) {
            items.removeAt(index)
        }
    }

    fun clear() {
        items.clear()
    }
}