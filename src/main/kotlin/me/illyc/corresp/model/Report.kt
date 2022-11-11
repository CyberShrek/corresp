package me.illyc.corresp.model

// The simplest form of report: it is just a rows of String arrays
data class Report(
    val header: Array<String>,
    val body: ArrayList<List<String>>)