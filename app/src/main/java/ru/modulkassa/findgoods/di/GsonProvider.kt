package ru.modulkassa.findgoods.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory
import ru.modulkassa.findgoods.domain.good.GoodItem
import ru.modulkassa.findgoods.domain.network.dto.CatalogEntity
import ru.modulkassa.findgoods.domain.network.dto.CatalogType
import ru.modulkassa.findgoods.domain.network.dto.CommandAdd
import ru.modulkassa.findgoods.domain.network.dto.CommandClear
import ru.modulkassa.findgoods.domain.network.dto.CommandDelete
import ru.modulkassa.findgoods.domain.network.dto.Command
import javax.inject.Provider

class GsonProvider : Provider<Gson> {
    override fun get(): Gson {
        val commandAdapterFactory = RuntimeTypeAdapterFactory.of(
            Command::class.java, "command")
            .registerSubtype(CommandAdd::class.java, "add")
            .registerSubtype(CommandDelete::class.java, "delete")
            .registerSubtype(CommandClear::class.java, "clear")

        val entityAdapterFactory = RuntimeTypeAdapterFactory.of(CatalogEntity::class.java,
            "catalogType")
            .registerSubtype(GoodItem::class.java, CatalogType.INVENTORY.name)

        return GsonBuilder()
            .registerTypeAdapterFactory(commandAdapterFactory)
            .registerTypeAdapterFactory(entityAdapterFactory)
            .create()
    }
}