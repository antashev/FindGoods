package ru.modulkassa.findgoods.domain.network.dto

import ru.modulkassa.findgoods.domain.good.Good
import ru.modulkassa.findgoods.domain.good.Measure
import ru.modulkassa.findgoods.domain.good.Measure.KG
import ru.modulkassa.findgoods.domain.good.Measure.LTR
import ru.modulkassa.findgoods.domain.network.dto.MeasureDto.pcs
import java.math.BigDecimal


data class CatalogResponse(
    val catalog: Catalog,
    val catalogEntity: GoodDto,
    val entityId: String, //1
    val id: String, //5a7dbd2925f4be6f9f7624a8
    val modificationTime: Long, //1518189958887
    val removed: Boolean //false
)

data class GoodDto(
    val inventCode: String = "", //10001
    val name: String = "", //Молоко Лебедевское, 2,5%
    val barcode: String = "", //10001
    val price: BigDecimal = BigDecimal.ZERO, //52
    val minPrice: BigDecimal = BigDecimal.ZERO, //52
    val measure: MeasureDto = pcs
) {
    private val catalogType = "INVENTORY"

    fun toDomain(): Good {
        return Good(
            inventCode = inventCode,
            name = name,
            barcode = barcode,
            price = price,
            minPrice = minPrice,
            measure = measure.toDomain()
        )
    }
}

enum class MeasureDto(
    val shortName: String,
    val exportName: String,
    val allowFloat: Boolean) {
    pcs("pcs", "шт", false),
    kg("kg", "кг", true),
    ltr("ltr", "л", true);

    fun toDomain(): Measure {
        return when (this) {
            pcs -> Measure.PCS
            kg -> KG
            ltr -> LTR
        }
    }
}

data class Catalog(
    val company: Any, //null
    val id: String, //178a3201-0d4d-4d31-8ccf-d6862dd533f6
    val name: Any, //null
    val target: Target
)

data class Target(
    val company: Company,
    val id: String, //178a3201-0d4d-4d31-8ccf-d6862dd533f6
    val name: String //asdaasasdd
)

data class Company(
    val companyName: String, //aa
    val id: String, //edb6316e-09fe-4356-a50b-083b725429f5
    val inn: Any, //null
    val msIntegrationEnabled: Boolean, //false
    val msLogin: Any, //null
    val msPassword: Any //null
)