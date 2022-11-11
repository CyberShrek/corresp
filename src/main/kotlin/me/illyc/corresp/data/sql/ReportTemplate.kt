package me.illyc.corresp.data.sql

import me.illyc.corresp.model.ReportInputParams
import org.intellij.lang.annotations.Language

// General query template for all report queries. To learn how it works, take a look at the 'query' field
class ReportTemplate (private val params: ReportInputParams,
                      // If it is main report, the query will find all combinations of the gotten station codes,
                      // otherwise it will only use them as pairs fromCode -> toCode
                      private val isMainReport: Boolean = false,
                      // Affects where the data for "passengers", "income", "passenger_turnover" will be taken from.
                      // It must be "correspon" (default), "correspvag" or "passprofile"
                      private val source: String = "correspon",
                      // Extra WITH expression
                      private val beforeWithBase: String = "",
                      // With base query
                      private val beforeBaseSelect: String,
                      private val afterBaseFrom:    String = "",
                      private val carrierCode: String = "'"+params.carrierCode+"'",
                      private val date1: String = "'"+params.date1+"'",
                      private val date2: String = "'"+params.date2+"'",
                      private val afterBaseWhere:   String = "",
                      private val groupBaseBy:      String,
                      // Result
                      private val beforeResultSelect:      String = groupBaseBy,
                      private val afterResultWhere:        String = "",
                      private val beforeResultTotalSelect: String,
                      private val orderResultBy:           String = "",
                      // Only for comparing with last year
                      private val beforeComparedSelect: String = beforeResultSelect,
                      private val comparatorKey:        String = beforeComparedSelect) {

    var query = query("""
            WITH $beforeWithBase ${if (beforeWithBase != "") "," else ""} base AS (
                SELECT $beforeBaseSelect, 
                       -- General values. They're used by all query objects (almost!)
                       sum($source.kolpm   - $source.kolvozm) AS passengers,
                       sum($source.sumbil  + $source.sumpl  + $source.sumserv  + $source.sumkomsb
                         + $source.sumbilv + $source.sumplv + $source.sumservv + $source.sumkomsbv) AS income,
                       sum($source.passkm) AS passenger_turnover
            
                FROM rmest.day_trainpass AS trainpass
                JOIN rmest.day_correspon AS correspon USING (id_pzd)
                ${if(source == "correspvag" || source == "passprofile") 
                    "JOIN rmest.day_correspvag AS correspvag  USING (id_corresp)" else ""}
                $afterBaseFrom
            
                WHERE trainpass.skp = $carrierCode
                  AND trainpass.datapzd BETWEEN ($date1::date - '7 days'::interval)::date AND $date2
                  AND correspon.dateotp BETWEEN $date1 AND $date2
                  -- If it is main report, the query will find all combinations of the gotten station codes,
                  -- otherwise it will only use them as pairs fromCode -> toCode
                  ${if(isMainReport) getMainStationsCondition() else getSecondaryStationsCondition() }
                  $afterBaseWhere
            
                GROUP BY $groupBaseBy
            ),
            -- Counting the total values
            total AS (
                SELECT sum(passengers)         AS passengers,
                       sum(income)             AS income,
                       sum(passenger_turnover) AS passenger_turnover 
                FROM base
            )
            -- Resulting
            SELECT $beforeResultSelect,
                   base.passengers,
                   round((base.passengers / NULLIF(total.passengers, 0)) * 100, 1) AS passengers_proportion,
                   null AS passengers_changed_from_ly,
                   round(base.income / 1000) AS income,
                   round((base.income / NULLIF(total.income, 0)) * 100, 1) AS income_proportion,
                   null AS income_changed_from_ly,
                   round(COALESCE(base.income / NULLIF(base.passengers, 0), 0), 1) AS income_rate,
                   null AS income_rate_changed_from_ly,
                   base.passenger_turnover,
                   round((base.passenger_turnover / NULLIF(total.passenger_turnover, 0)) * 100, 1) AS passenger_turnover_proportion,
                   null AS passenger_turnover_changed_from_ly
            
            FROM base JOIN total ON true
            $afterResultWhere
            
            -- A total amount row
            UNION SELECT $beforeResultTotalSelect,
                         passengers, 100, null,
                         round(income / 1000, 1), 100, null,
                         round(COALESCE(income / NULLIF(passengers, 0), 0), 2), null,
                         passenger_turnover, 100, null
            FROM total
            
            ${if (orderResultBy != "") "ORDER BY $orderResultBy" else ""}
        """)

    fun queryComparedByLY(paramsLY: ReportInputParams) = """
            WITH 
            last_year AS ( ${ReportTemplate(paramsLY, isMainReport, source, beforeWithBase, beforeBaseSelect, afterBaseFrom, carrierCode, "'"+paramsLY.date1+"'", "'"+paramsLY.date2+"'", afterBaseWhere, groupBaseBy, beforeResultSelect, afterResultWhere, beforeResultTotalSelect).query} ),
            curr_year AS ( $query )
            
            SELECT $beforeComparedSelect,
                   curr_year.passengers, curr_year.passengers_proportion,
                   round((curr_year.passengers / NULLIF(last_year.passengers, 0)) * 100 - 100, 1) AS passengers_changed_from_ly,
                   curr_year.income, curr_year.income_proportion,
                   round((curr_year.income / NULLIF(last_year.income, 0)) * 100 - 100, 1) AS income_changed_from_ly,
                   curr_year.income_rate,
                   round((curr_year.income_rate / NULLIF(last_year.income_rate, 0)) * 100 - 100, 1) AS income_rate_changed_from_ly,
                   curr_year.passenger_turnover, curr_year.passenger_turnover_proportion,
                   round((curr_year.passenger_turnover / NULLIF(last_year.passenger_turnover, 0)) * 100 - 100, 1) AS passenger_turnover_changed_from_ly
            
            FROM curr_year
            LEFT JOIN last_year USING ($comparatorKey)
        """

    private fun getMainStationsCondition() = """
            AND array_position(Array ${params.fromCodes.map { code -> "'$code'::char(7)" }}, correspon.stanotp)  IS NOT NULL
            AND array_position(Array ${params.toCodes.map   { code -> "'$code'::char(7)" }}, correspon.stannazn) IS NOT NULL
            """

    private fun getSecondaryStationsCondition(): String {
        if (params.fromCodes.size != params.toCodes.size) throw Exception("fromCodes.size != toCodes.size")
        var condition = "AND ("
        for(i in params.fromCodes.indices){
            if (i != 0) condition += " OR "
            condition += "correspon.stanotp||'->'||correspon.stannazn = '${params.fromCodes[i]}->${params.toCodes[i]}'"
        }
        return "$condition)"
    }

    private fun query(@Language("SQL") sql: String) = sql
}