package me.illyc.corresp.controller

import me.illyc.corresp.dao.CorrespDao
import me.illyc.corresp.model.Params
import me.illyc.corresp.service.CorrespService
import org.springframework.web.bind.annotation.*

@RestController
class CorrespController(private val dao: CorrespDao,
                        private val service: CorrespService) {

    @GetMapping("/carriers")
    fun getCarriers(@RequestParam date: String) = dao.getCarriersByDate(date)

    @GetMapping("/countries")
    fun getCountries(@RequestParam date: String) = dao.getCountriesByDate(date)

    @GetMapping("/roads")
    fun getRoads(@RequestParam date: String,
                 @RequestParam countryCodes: Set<String>) = dao.getRoadsByCountryCodesAndDate(countryCodes, date)

    @GetMapping("/stations")
    fun getStations(@RequestParam date: String,
                    @RequestParam roadCodes: Set<String>) = dao.getStationsByRoadCodesAndDate(roadCodes, date)

    @PostMapping("/report1")
    fun createReport1(@RequestBody params: Params) = service.createReport1ByParams(params)
}
