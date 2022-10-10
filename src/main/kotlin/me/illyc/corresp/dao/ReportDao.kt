package me.illyc.corresp.dao

import me.illyc.corresp.dao.sql.Select
import me.illyc.corresp.model.Report
import me.illyc.corresp.model.Params
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.rowset.SqlRowSet
import org.springframework.stereotype.Repository
import kotlin.collections.ArrayList

@Repository
class ReportDao (private val jdbc: JdbcTemplate) {

    fun getReport1ByParams(params: Params): Report {
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
                departureType   = params.departureType,   departureCodes   = params.departureCodes,
                destinationType = params.destinationType, destinationCodes = params.destinationCodes
            )

            println(Select.Reports.comparedReport1ByParams(params, paramsLY))
            return rowSetToReport(jdbc.queryForRowSet(Select.Reports.comparedReport1ByParams(params, paramsLY)))
        }
        println(Select.Reports.report1ByParams(params))
        return rowSetToReport(jdbc.queryForRowSet(Select.Reports.report1ByParams(params)))
    }

    fun getReport2ByParams(params: Params): Report{
        return Report(ArrayList(), ArrayList())
    }

    fun getReport3ByParams(params: Params): Report{
        return Report(ArrayList(), ArrayList())
    }

    fun getReport4ByParams(params: Params): Report{
        return Report(ArrayList(), ArrayList())
    }

    fun getReport5ByParams(params: Params): Report{
        return Report(ArrayList(), ArrayList())
    }

    fun getReport6ByParams(params: Params): Report{
        return Report(ArrayList(), ArrayList())
    }

    private fun rowSetToReport(rowSet: SqlRowSet): Report{
        val columnNames = rowSet.metaData.columnNames
        val report = Report(columnNames.map{ name -> shake_case_toCamelCase(name) }, ArrayList())
        while (rowSet.next()) {
            report.content.add(columnNames.map{ name -> rowSet.getString(name) ?: "" })
        }
        return report
    }
    private fun shake_case_toCamelCase(text: String) = text
        .split("_")
        .mapIndexed { i, word -> if (i > 0) word.capitalize() else word }
        .joinToString("")
}