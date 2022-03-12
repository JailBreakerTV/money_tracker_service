package de.gtopcu.control.conversions

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class MoneyConversionsControl(
    @Autowired private val mapper: ObjectMapper,
    @Value("\${apiKey}") private val apiKey: String,
) {
    fun findMoneyConversions(baseCurrency: String): MoneyConversionList? {
        println("Fetching data")
        val template = RestTemplate()
        val uri = kBaseUri.format(apiKey, baseCurrency.uppercase())
        val result = template.getForObject(uri, String::class.java)
        val response = result?.let { mapper.readValue(it, MoneyConversionResponse::class.java) }
        return response?.toMoneyConversionList()
    }

    companion object {
        private const val kBaseUri = "https://freecurrencyapi.net/api/v2/latest?apikey=%s&base_currency=%s"
    }
}