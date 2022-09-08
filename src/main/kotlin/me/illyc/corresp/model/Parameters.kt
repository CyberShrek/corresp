package me.illyc.corresp.model

data class Parameters(var carriers: Set<String>,
                      var countries: Set<String>,
                      var railRoads: Set<String>,
                      var stations: Set<String>)