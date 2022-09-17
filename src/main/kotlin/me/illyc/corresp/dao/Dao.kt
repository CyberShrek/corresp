package me.illyc.corresp.dao

import org.springframework.stereotype.Component

@Component
class Dao {

    fun getCarrierByDate(date: String) = setOf("Перевозчик-1", "Перевозчик-2", "Перевозчик-3")

    val countries
        get() = setOf("Россия", "Северная Корея", "Эритрея", "Сирия", "Беларусь")

    val stations
        get() = setOf(
            "Москва",
            "Санкт-Петербург",
            "Новосибирск",
            "Екатеринбург",
            "Казань",
            "Нижний Новгород",
            "Челябинск",
            "Омск"
        )

    val roads
        get() = setOf("ЖД-1", "ЖД-2", "ЖД-3", "ЖД-4", "ЖД-5", "ЖД-6")
}