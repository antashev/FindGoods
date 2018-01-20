package ru.modulkassa.findgoods.domain.network.dto

/**
 * Базовый класс для сущностей каталога
 */
abstract class CatalogEntity {

    abstract val catalogType: CatalogType

    /**
     * Идентификатор сущности
     */
    abstract fun getId(): String
}
