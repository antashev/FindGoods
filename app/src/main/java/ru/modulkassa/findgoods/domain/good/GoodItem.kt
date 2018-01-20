package ru.modulkassa.findgoods.domain.good

import ru.modulkassa.findgoods.domain.network.dto.CatalogEntity
import ru.modulkassa.findgoods.domain.network.dto.CatalogType
import ru.modulkassa.findgoods.domain.network.dto.CatalogType.INVENTORY
import java.math.BigDecimal


data class GoodItem(
		val inventCode: String, //10001
		val name: String, //Молоко Лебедевское, 2,5%
		val barcode: String, //10001
		val price: BigDecimal, //52
		val minPrice: BigDecimal, //52
		val volume: BigDecimal, //null
		val alcVolume: BigDecimal, //null
		val vatTag: Any, //null
		val taxMode: Any, //null
        val measure: Measure
) : CatalogEntity() {
    override val catalogType: CatalogType
        get() = INVENTORY

    override fun getId(): String {
        return inventCode
    }
}

enum class Measure(
    val shortName: String,
    val exportName: String,
    val allowFloat: Boolean) {
    PCS("pcs", "шт", false),
    KG("kg", "кг", true),
    LTR("ltr", "л", true)
}