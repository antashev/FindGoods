package ru.modulkassa.findgoods.domain.network.dto

/**
 * Варианты сущностей хранящихся в каталоге
 */
enum class CatalogType {
    /**
     * Товары
     */
    INVENTORY,
    /**
     * Горячие клавиши
     */
    HOT_KEYS,
    /**
     * Контрагенты
     */
    CONTRACTOR,
    /**
     * Группы товаров
     */
    ITEM_GROUP,
    /**
     * Скидки
     */
    SIMPLE_DISCOUNT,
    /**
     * Модификаторы
     */
    MODIFIER_GROUP
}