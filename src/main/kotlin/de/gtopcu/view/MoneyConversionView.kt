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
@RequestMapping("/conversion")
class MoneyConversionView(@Autowired private val conversionControl: MoneyConversionControl) {
    @GetMapping("/{baseCurrency}/{currency}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findMoneyConversions(
        @PathVariable baseCurrency: String,
        @PathVariable currency: String
    ): ResponseEntity<MoneyConversionModel> {
        return ResponseEntity.ok(conversionControl.findMoneyConversion(baseCurrency, currency))
    }
}