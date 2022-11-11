package me.illyc.corresp.controller

import me.illyc.corresp.data.ReportDao
import me.illyc.corresp.data.ServiceBank
import me.illyc.corresp.model.ReportInputParams
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
class DataController(private val reportDao: ReportDao) {

    @GetMapping("/carriers")
    fun getCarriers(@RequestParam date: String,
                    request: HttpServletRequest) = ServiceBank(request).getCarriersByDate(date)

    @GetMapping("/countries")
    fun getCountries(@RequestParam date: String,
                     request: HttpServletRequest) = ServiceBank(request).getCountriesByDate(date)

    @GetMapping("/roads")
    fun getRoads(@RequestParam date: String,
                 @RequestParam countryCodes: Set<String>,
                 request: HttpServletRequest) = ServiceBank(request).getRoadsByCountryCodesAndDate(countryCodes, date)

    @GetMapping("/stations")
    fun getStations(@RequestParam date: String,
                    @RequestParam roadCodes: Set<String>,
                    request: HttpServletRequest) = ServiceBank(request)
        .getStationsByRoadCodesAndDate(roadCodes, date)

    @GetMapping("/reports/{num}")
    fun getReport(@PathVariable(value = "num") num: Byte,
                   params: ReportInputParams) = reportDao.getReportByParams(num, params)
}