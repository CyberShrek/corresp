package me.illyc.corresp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CorrespApplication

fun main(args: Array<String>) {
    runApplication<CorrespApplication>(*args)
}
