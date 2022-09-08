package me.illyc.corresp.controller

import me.illyc.corresp.dao.Dao
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.ModelAndView

@Controller
class CorrespController(private var dao: Dao) {

    @GetMapping
    fun index() = "html/index.html"

    @GetMapping("/carriers")
    @ResponseBody
    fun getCarriers() = dao.carriers

    @GetMapping("/countries")
    @ResponseBody
    fun getCountries() = dao.countries

    @GetMapping("/stations")
    @ResponseBody
    fun getStations() = dao.stations

    @GetMapping("/railRoads")
    @ResponseBody
    fun getRailRoads() = dao.railRoads
}