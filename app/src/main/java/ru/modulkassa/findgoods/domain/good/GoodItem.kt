package ru.modulkassa.findgoods.domain.good

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
)

enum class Measure(
    val shortName: String,
    val exportName: String,
    val allowFloat: Boolean) {
    PCS("pcs", "шт", false),
    KG("kg", "кг", true),
    LTR("ltr", "л", true)
}