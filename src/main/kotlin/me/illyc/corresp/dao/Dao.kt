package me.illyc.corresp.dao

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class Dao (val template: JdbcTemplate){

    val firstColumn = RowMapper<String>{ rowSet, _ -> rowSet.getString(1) }

    fun getCarriersByDate(date: String) = template.query(
        "SELECT nazvd " +
                "FROM nsi.sobper " +
                "WHERE '$date' between datano and datako", firstColumn)
        .toSortedSet() // setOf("Перевозчик-1", "Перевозчик-2", "Перевозчик-3")

    fun getCountriesByDate(date: String) = template.query(
        "SELECT g_name " +
                "FROM nsi.gosk " +
                "WHERE '$date' between g_datan  and g_datak " +
                "AND   '$date' between g_datani and g_dataki", firstColumn)
        .toSortedSet() // setOf("Россия", "Северная Корея", "Эритрея", "Сирия", "Беларусь")

    fun getRoadsByDate(date: String) = template.query(
        "SELECT d_name " +
                "FROM nsi.dork " +
                "WHERE '$date' between d_datan  and d_datak " +
                "AND   '$date' between d_datani and d_dataki", firstColumn)
        .toSortedSet() // setOf("ЖД-1", "ЖД-2", "ЖД-3", "ЖД-4", "ЖД-5", "ЖД-6")

    fun getStationsByDate(date: String) = template.query(
        "SELECT pnazv " +
                "FROM nsi.stanv " +
                "WHERE '$date' between datand and datakd " +
                "AND   '$date' between datani and dataki", firstColumn)
        .toSortedSet() // setOf("Москва", "Санкт-Петербург", "Новосибирск", "Екатеринбург", "Казань", "Нижний Новгород", "Челябинск", "Омск")

}