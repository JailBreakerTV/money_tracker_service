package de.gtopcu.view

import de.gtopcu.control.conversion.MoneyConversionControl
import de.gtopcu.model.MoneyConversionModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/conversions")
class MoneyConversionsView(@Autowired private val conversionControl: MoneyConversionControl) {
    @GetMapping("/{baseCurrency}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findMoneyConversions(@PathVariable baseCurrency: String): ResponseEntity<List<MoneyConversionModel>> {
        return conversionControl.findMoneyConversions(baseCurrency)
            .takeIf { it.isNotEmpty() }
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }
}