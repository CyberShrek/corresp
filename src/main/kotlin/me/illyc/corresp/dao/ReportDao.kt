package me.illyc.corresp.dao

import me.illyc.corresp.dao.sql.Select.Reports
import me.illyc.corresp.dao.sql.Select.Reports.report1ByParams
import me.illyc.corresp.dao.sql.Select.Reports.report2ByParams
import me.illyc.corresp.dao.sql.Select.Reports.report4ByParams
import me.illyc.corresp.dao.sql.Select.Reports.report5ByParams
import me.illyc.corresp.dao.sql.Select.Reports.report6ByParams
import me.illyc.corresp.dao.sql.Select.Reports.report7ByParams
import me.illyc.corresp.model.Report
import me.illyc.corresp.model.Params
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.rowset.SqlRowSet
import org.springframework.stereotype.Repository
import kotlin.collections.ArrayList

@Repository
class ReportDao (private val jdbc: JdbcTemplate) {

    // Returns a new Report corresponding to it's number and parameters
    fun getReportByParams(num: Byte, params: Params) = when(num){
        1.toByte() -> getReport(::report1ByParams, params)
        2.toByte() -> getReport(::report2ByParams, params)
        3.toByte() -> Report(arrayOf(), ArrayList()) // TODO
        4.toByte() -> getReport(::report4ByParams, params)
        5.toByte() -> getReport(::report5ByParams, params)
        6.toByte() -> getReport(::report6ByParams, params)
        7.toByte() -> getReport(::report7ByParams, params)
        else -> throw Exception("Unknowing report number: $num")
    }

    // High-ordered
    private fun getReport(getQueryByParams: (Params) -> Reports.ReportQuery, params: Params): Report{
        val reportQuery = getQueryByParams(params)
        if(params.compareWithLastYear){
            reportQuery.text = reportQuery
                .textComparedByLY(getLastYearParams(params))
        }
        println(reportQuery.text.trimIndent())
        return rowSetToReport(jdbc.queryForRowSet(reportQuery.text))
    }

    private fun getLastYearParams(params: Params): Params {
        var date1LY = ""; var date2LY = ""
        val rowSet = jdbc.queryForRowSet("""SELECT (date '${params.date1}' - interval '1 year')::date AS date1, 
                                                       (date '${params.date2}' - interval '1 year')::date AS date2""")
        if (rowSet.next()){
            date1LY = rowSet.getString("date1") ?: "1970-01-01"
            date2LY = rowSet.getString("date2") ?: "1970-01-01"
        }
        return Params(
            date1 = date1LY, date2 = date2LY, carrierCode = params.carrierCode,
            departureType   = params.departureType,   departureCodes   = params.departureCodes,
            destinationType = params.destinationType, destinationCodes = params.destinationCodes
        )
    }

    private fun rowSetToReport(rowSet: SqlRowSet): Report {
        val columnNames = rowSet.metaData.columnNames
        val report = Report(columnNames, ArrayList())
        while (rowSet.next()) {
            report.body.add(columnNames.map{ name -> rowSet.getString(name) ?: "" })
        }
        return report
    }
}