package ru.modulkassa.findgoods.domain.network.dto

data class RateAndGoodsResponse(
    val pagination: Pagination,
    val results: List<RgFindResult>
)

data class Pagination(
    val page: Int,
    val page_count: Int,
    val page_size: Int,
    val total: Int,
    val next: String,
    val previous: String
)

data class RgFindResult(
    val identifiers: List<GoodIdentifier>,
    val title: String,
    val alcohol: Boolean,
    val measure: RgMeasure
)

data class RgMeasure(
    val count: String,
    val units: String
)

data class GoodIdentifier(
    val key: String,
    val type: String
)