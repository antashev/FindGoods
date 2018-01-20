package ru.modulkassa.findgoods.domain.network.dto

import ru.modulkassa.findgoods.domain.network.dto.CommandType.ADD
import ru.modulkassa.findgoods.domain.network.dto.CommandType.CLEAR
import ru.modulkassa.findgoods.domain.network.dto.CommandType.DELETE

enum class CommandType {
    ADD, CLEAR, DELETE
}

/**
 * Команда синхронизации каталога
 */
sealed class Command {
    abstract val command: CommandType
}
data class CommandAdd(val entity: CatalogEntity) : Command() {
    override val command: CommandType
        get() = ADD
}

data class CommandClear(val catalogs: List<CatalogType>) : Command() {
    override val command: CommandType
        get() = CLEAR
}

data class CommandDelete(val type: CatalogType, val entityId: String) : Command() {
    override val command: CommandType
        get() = DELETE
}