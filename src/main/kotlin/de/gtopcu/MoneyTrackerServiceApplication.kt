package de.gtopcu

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MoneyTrackerServiceApplication

fun main(args: Array<String>) {
    runApplication<MoneyTrackerServiceApplication>(*args)
}
