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
    val identifiers: List<FindRequest>,
    val title: String
)

data class FindRequest(
    val key: String,
    val type: String
)