package de.gtopcu.control.conversions

import de.gtopcu.model.MoneyConversionModel
import java.io.Serializable
import java.time.LocalDateTime

data class MoneyConversionResponse(
    var query: Map<String, Any> = mutableMapOf(),
    var data: Map<String, Double> = mutableMapOf()
) : Serializable {
    fun toMoneyConversionList(): MoneyConversionList {
        val baseCurrency = query["base_currency"] as String
        val currencies = data.map { MoneyConversionEntry(it.key, it.value) }.toList()
        return MoneyConversionList(baseCurrency, currencies)
    }
}

data class MoneyConversionEntry(val title: String, val amount: Double) : Serializable {
    fun toModel(baseCurrency: String) = MoneyConversionModel(
        baseCurrency = baseCurrency,
        currency = title,
        value = amount,
        timestamp = LocalDateTime.now()
    )
}

data class MoneyConversionList(val baseCurrency: String, val currencies: List<MoneyConversionEntry>) : Serializable {
    fun toModels() = currencies.map { it.toModel(baseCurrency) }.toList()
}