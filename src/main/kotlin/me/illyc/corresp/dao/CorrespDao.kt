package me.illyc.corresp.dao

import me.illyc.corresp.dao.sql.Select
import me.illyc.corresp.model.Params
import me.illyc.corresp.model.Report1Row
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class CorrespDao (private val jdbc: JdbcTemplate) {

    val nameAndCodeMapper = RowMapper<Any>{ rowSet, _ -> object {
        val name = rowSet.getString("name")
        val code = rowSet.getString("code")
    }}

    fun getCarriersByDate(date: String) = jdbc
        .query(Select.Carriers.byDate(date), nameAndCodeMapper)
        .toSet()

    fun getCountriesByDate(date: String) = jdbc
        .query(Select.Countries.byDate(date), nameAndCodeMapper)
        .toSet()

    fun getRoadsByCountryCodesAndDate(countryCodes: Set<String>, date: String) = jdbc
        .query(Select.Roads.byCountryCodesAndDate(countryCodes, date), nameAndCodeMapper)
        .toSet()

    fun getStationsByRoadCodesAndDate(roadCodes: Set<String>, date: String) =
        jdbc.query(Select.Stations.byRoadCodesAndDate(roadCodes, date), nameAndCodeMapper)
        .toSet()

    fun getReport1ByParams(params: Params): Map<String, Report1Row> {
        val report = HashMap<String, Report1Row>()
        val rowSet = jdbc.queryForRowSet("""
            WITH departSt AS (${Select.Stations.byTypedCodes(params.departType, params.departCodes)}),
                 destinSt AS (${Select.Stations.byTypedCodes(params.destinType, params.destinCodes)})
            
            SELECT departSt.name || ' -> ' || destinSt.name
            FROM departSt 
            JOIN rmest.day_correspon corsp ON departSt.code  = corsp.stanotp
            JOIN destinSt                  ON corsp.stannazn = destinSt.code
            
        """)

        return report
    }
}