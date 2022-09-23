package me.illyc.corresp.dao

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import javax.annotation.PostConstruct

@Repository
class Dao (val template: JdbcTemplate) {

    val nameMapper = RowMapper<String>{ rowSet, _ -> rowSet.getString(1) }

    val nameAndCodeMapper = RowMapper<Any>{ rowSet, _ -> object {
        val name  = rowSet.getString("name")
        val code = rowSet.getString("code")
    }}

    fun getCarriersByDate(date: String) = template.query("""
        SELECT DISTINCT nazvd AS name 
        FROM nsi.sobper 
        WHERE '$date' between datano and datako 
        ORDER BY name
        """, nameMapper)
        .toSet()

    fun getCountriesByDate(date: String) = template.query("""
        SELECT string_agg(g_name, ' | ') AS name, g_vid AS code
        FROM (
            SELECT DISTINCT g_name, g_vid
            FROM nsi.gosk
            WHERE '$date' between g_datan  and g_datak
            AND   '$date' between g_datani and g_dataki
        ) as c
        GROUP BY code
        """, nameAndCodeMapper)
        .toSet()

    fun getRoadsByDateAndCountryCodes(date: String, countryCodes: Set<Int>) = template.query("""
        SELECT string_agg(d_name, ' | ') AS name, d_kod AS code
        FROM (
             SELECT DISTINCT d_name, d_kod
             FROM nsi.dork
             WHERE array_position(Array $countryCodes, d_vidgos) IS NOT NULL
             AND '$date' between d_datan  and d_datak
             AND '$date' between d_datani and d_dataki
         ) as r
        GROUP BY code
        """, nameAndCodeMapper)
        .toSet()

    fun getStationsByDateAndRoadCodes(date: String, roadCodes: Set<String>) = template.query("""
        SELECT string_agg(pnazv, ' | ') AS name, stan AS code
        FROM (
             SELECT DISTINCT pnazv, stan
             FROM nsi.stanv
             WHERE array_position(Array ${roadCodes.map{code -> "'$code'::char"}}, dor) IS NOT NULL
             AND '$date' between datand and datakd
             AND '$date' between datani and dataki
         ) as s
        GROUP BY code
        """, nameAndCodeMapper)
        .toSet()
}