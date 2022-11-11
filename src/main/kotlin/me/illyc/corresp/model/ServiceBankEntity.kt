package me.illyc.corresp.model

// The abstract entity which is transferring by service bank
abstract class ParamsFormEntity(val name: String, val code: String)

class Carrier(name: String, code: String): ParamsFormEntity(name, code)

class Country(name: String, code: String): ParamsFormEntity(name, code)

class Road (name: String, code: String,
            var countryCode: String = ""): ParamsFormEntity(name, code)

class Station(name: String, code: String): ParamsFormEntity(name, code)