package me.illyc.corresp.controller

import me.illyc.corresp.dao.Dao
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class CorrespController(private var dao: Dao) {

    @GetMapping
    fun index() = "index.html"

    @GetMapping("/carriers")
    @ResponseBody
    fun getCarriers(@RequestParam date: String) = dao.getCarriersByDate(date)

    @GetMapping("/countries")
    @ResponseBody
    fun getCountries(@RequestParam date: String) = dao.getCountriesByDate(date)

    @GetMapping("/roads")
    @ResponseBody
    fun getRoads(@RequestParam date: String,
                 @RequestParam countryCodes: Set<Int>) = dao.getRoadsByDateAndCountryCodes(date, countryCodes)

    @GetMapping("/stations")
    @ResponseBody
    fun getStations(@RequestParam date: String,
                    @RequestParam roadCodes: Set<String>) = dao.getStationsByDateAndRoadCodes(date, roadCodes)
}