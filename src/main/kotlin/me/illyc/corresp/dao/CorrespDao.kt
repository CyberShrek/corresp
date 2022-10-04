package me.illyc.corresp.dao

import me.illyc.corresp.dao.sql.Select
import me.illyc.corresp.model.Params
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.rowset.SqlRowSet
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

    fun getReport1ByParams(params: Params): List<Any> {
        if(params.compareWithLastYear){
            var date1LY = ""; var date2LY = ""
            val rowSet = jdbc.queryForRowSet("""SELECT (date '${params.date1}' - interval '1 year')::date AS date1, 
                                                           (date '${params.date2}' - interval '1 year')::date AS date2""")
            if (rowSet.next()){
                date1LY = rowSet.getString("date1") ?: "1970-01-01"
                date2LY = rowSet.getString("date2") ?: "1970-01-01"
            }

            val paramsLY = Params(
                date1 = date1LY, date2 = date2LY, carrierCode = params.carrierCode,
                departureType = params.departureType, departureCodes = params.departureCodes,
                destinationType = params.destinationType, destinationCodes = params.destinationCodes
            )
            return getReportByRowSet(jdbc.queryForRowSet(Select.Reports.comparedReport1ByParams(params, paramsLY)))
        }
        return getReportByRowSet(jdbc.queryForRowSet(Select.Reports.report1ByParams(params)))
    }

    private fun getReportByRowSet(rowSet: SqlRowSet): ArrayList<Any>{
        val report = ArrayList<Any>()
        while (rowSet.next()) {
            report.add(
                object {
                    val fromTo = rowSet.getString("from_to")
                    val passengers = object {
                        val amount = rowSet.getBigDecimal("passengers")
                        val changedFromLY = rowSet.getFloat("passangers_changed_from_ly")
                        val proportion = rowSet.getFloat("passengers_proportion")
                    }
                    val income = object {
                        val amount = rowSet.getBigDecimal("income")
                        val changedFromLY = rowSet.getFloat("income_changed_from_ly")
                        val proportion = rowSet.getFloat("income_proportion")
                    }
                    val incomeRate = object {
                        val amount = rowSet.getFloat("income_rate")
                        val changedFromLY = rowSet.getFloat("income_rate_changed_from_ly")
                    }
                    val passengerTurnover = object {
                        val amount = rowSet.getBigDecimal("passenger_turnover")
                        val changedFromLY = rowSet.getFloat("passenger_turnover_changed_from_ly")
                        val proportion = rowSet.getFloat("passenger_turnover_proportion")
                    }
                    val trainsCount = rowSet.getBigDecimal("trains_count")
                }
            )
        }
        return report
    }
}