package ru.modulkassa.findgoods.domain.point

import io.reactivex.Single
import ru.modulkassa.findgoods.domain.network.api.RetailPointApi
import javax.inject.Inject

interface RetailPointManager {
    fun getRetailPoints(): Single<List<RetailPoint>>
}

class RetailPointsImpl @Inject constructor(
    private val api: RetailPointApi
) : RetailPointManager {
    override fun getRetailPoints(): Single<List<RetailPoint>> {
        return api.retailPoints()
    }
}