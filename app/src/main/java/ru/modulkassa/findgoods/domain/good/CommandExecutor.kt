package ru.modulkassa.findgoods.domain.good

import ru.modulkassa.findgoods.domain.network.dto.CatalogType.INVENTORY
import ru.modulkassa.findgoods.domain.network.dto.Command
import ru.modulkassa.findgoods.domain.network.dto.CommandAdd
import ru.modulkassa.findgoods.domain.network.dto.CommandClear
import ru.modulkassa.findgoods.domain.network.dto.CommandDelete
import ru.modulkassa.findgoods.domain.network.dto.CommandType.ADD
import ru.modulkassa.findgoods.domain.network.dto.CommandType.CLEAR
import ru.modulkassa.findgoods.domain.network.dto.CommandType.DELETE
import ru.modulkassa.findgoods.domain.repository.GoodItemRepository
import javax.inject.Inject

class CommandExecutor @Inject constructor(
    private val goodItemRepository: GoodItemRepository
) {
    fun execute(commandDto: Command) {
        when (commandDto.command) {
            ADD -> add(commandDto)
            DELETE -> delete(commandDto)
            CLEAR -> clear(commandDto)
        }
    }

    private fun add(commandDto: Command) {
        val commandAdd = commandDto as CommandAdd
        val item =  commandAdd.entity as GoodItem
        goodItemRepository.addItem(item)
    }

    private fun clear(commandDto: Command) {
        val command = commandDto as CommandClear
        if (INVENTORY in command.catalogs) {
            goodItemRepository.clear()
        }
    }
    private fun delete(commandDto: Command) {
        val command = commandDto as CommandDelete
        if (command.type == INVENTORY) {
            goodItemRepository.deleteItem(command.entityId)
        }
    }
}