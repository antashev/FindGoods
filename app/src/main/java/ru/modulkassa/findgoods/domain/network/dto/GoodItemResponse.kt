package ru.modulkassa.findgoods.domain.network.dto

import com.google.gson.annotations.SerializedName
import ru.modulkassa.findgoods.domain.good.GoodItem

data class GoodItemResponse(
    val data: List<GoodItem>,
    val pos: Int,
    @SerializedName("total_count")
    val totalCount: Long
)