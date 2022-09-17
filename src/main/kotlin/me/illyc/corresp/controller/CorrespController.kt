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
    fun getCarriers(@RequestParam date: String) : Set<String> {
        return dao.getCarrierByDate(date)
    }

    @GetMapping("/countries")
    @ResponseBody
    fun getCountries() = dao.countries

    @GetMapping("/stations")
    @ResponseBody
    fun getStations() = dao.stations

    @GetMapping("/roads")
    @ResponseBody
    fun getRailRoads() = dao.roads
}