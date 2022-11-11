package me.illyc.corresp.model

// Absolutely general input parameters whose are used by absolutely all reports
data class ReportInputParams(
    var date1: String = "01-01-1970",
    var date2: String = "01-01-1970",
    var compareWithLastYear: Boolean = false,
    var carrierCode: String = " ",
    var fromCodes: List<String> = listOf("0", "1"),
    var toCodes: List<String> = listOf("0", "1")
)