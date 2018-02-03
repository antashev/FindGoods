package ru.modulkassa.findgoods.domain.good

import ru.modulkassa.findgoods.domain.good.Measure.PCS
import java.math.BigDecimal


data class Good(
    val inventCode: String = "", //10001
    val name: String = "", //Молоко Лебедевское, 2,5%
    val barcode: String = "", //10001
    val price: BigDecimal = BigDecimal.ZERO, //52
    val minPrice: BigDecimal? = BigDecimal.ZERO, //52
//    val volume: BigDecimal, //null
//    val alcVolume: BigDecimal, //null
//    val vatTag: Any, //null
//    val taxMode: Any, //null
    val measure: Measure = PCS
) {
    private val catalogType = "INVENTORY"
}

enum class Measure(
    val shortName: String,
    val exportName: String,
    val allowFloat: Boolean) {
    PCS("pcs", "шт", false),
    KG("kg", "кг", true),
    LTR("ltr", "л", true)
}