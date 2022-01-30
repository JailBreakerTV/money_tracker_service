package de.gtopcu.conversion

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.Duration

@Service
class MoneyConversionService(
    @Autowired
    private val mapper: ObjectMapper,
    @Value("\${firstApiKey}")
    private val firstApiKey: String
) {
    private val expiration = Duration.ofHours(1)
    private val baseUri = "https://freecurrencyapi.net/api/v2/latest?apikey=%s&base_currency=%s"
    private val cache = Caffeine.newBuilder().expireAfterWrite(expiration).build<String, MoneyConversionList>()

    fun findMoneyConversion(code: String): MoneyConversionList? {
        return code.uppercase().let { normalized ->
            cache.getIfPresent(normalized) ?: run {
                val template = RestTemplate()
                val uri = baseUri.format(firstApiKey, normalized)
                val result = template.getForObject(uri, String::class.java)
                val response = result?.let { mapper.readValue(it, MoneyConversionResponse::class.java) }
                return@run response?.toMoneyConversionList()?.also { cache.put(normalized, it) }
            }
        }
    }
}