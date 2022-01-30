package de.gtopcu.conversion

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/conversion")
class MoneyConversionController(@Autowired private val service: MoneyConversionService) {
    @GetMapping("/{code}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findMoneyConversion(@PathVariable code: String): ResponseEntity<MoneyConversionList> {
        return service.findMoneyConversion(code)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()
    }
}