package me.illyc.corresp.data

import me.illyc.corresp.data.sql.*
import me.illyc.corresp.model.Report
import me.illyc.corresp.model.ReportInputParams
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.rowset.SqlRowSet
import org.springframework.stereotype.Repository

@Repository
class ReportDao (private val jdbc: JdbcTemplate) {

    // Returns a new Report corresponding to it's number and parameters
    fun getReportByParams(num: Byte, params: ReportInputParams): Any = when(num){
        1.toByte() -> getReport(::getReport1ByParams, params)
        2.toByte() -> getReport(::getReport2ByParams, params)
        // Report3 consists of daily and monthly Reports
        3.toByte() -> object {
            val dailyReport   = getReport(::getDailyReport3ByParams, params)
            val monthlyReport = getReport(::getMonthlyReport3ByParams, params)
        }
        4.toByte() -> getReport(::getReport4ByParams, params)
        5.toByte() -> getReport(::getReport5ByParams, params)
        6.toByte() -> getReport(::getReport6ByParams, params)
        7.toByte() -> getReport(::getReport7ByParams, params)
        else -> throw Exception("Unknowing report number: $num")
    }

    // High-ordered
    private fun getReport(getQueryByParams: (ReportInputParams) -> ReportTemplate, params: ReportInputParams): Report{
        val reportQuery = getQueryByParams(params)

        if(params.compareWithLastYear){
            reportQuery.query = reportQuery
                .queryComparedByLY(getLastYearInputParams(params))
        }
        return rowSetToReport(jdbc.queryForRowSet(reportQuery.query))
    }

    private fun getLastYearInputParams(params: ReportInputParams): ReportInputParams {
        var date1LY = ""; var date2LY = ""
        val rowSet = jdbc.queryForRowSet("""SELECT (date '${params.date1}' - interval '1 year')::date AS date1, 
                                                       (date '${params.date2}' - interval '1 year')::date AS date2""")

        if (rowSet.next()){
            date1LY = rowSet.getString("date1") ?: "1970-01-01"
            date2LY = rowSet.getString("date2") ?: "1970-01-01"
        }
        return ReportInputParams(
            date1 = date1LY, date2 = date2LY, carrierCode = params.carrierCode,
            fromCodes = params.fromCodes, toCodes = params.toCodes
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