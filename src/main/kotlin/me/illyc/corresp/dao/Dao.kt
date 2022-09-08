package me.illyc.corresp.dao

import org.springframework.stereotype.Component

@Component
class Dao {
    val carriers
    get() = setOf("Петрович", "Васяныч", "Яндекс.перевозки")

    val countries
    get() = setOf("Россия", "Беларусь", "Украина")

    val stations
    get() = setOf("Москва", "Санкт-Петербург", "Минск", "Гомель", "Киев", "Харьков")

    val railRoads
    get() = setOf("ЖД-1", "ЖД-2", "ЖД-3", "ЖД-4", "ЖД-5", "ЖД-6")
}