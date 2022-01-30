package de.gtopcu.conversion

import java.io.Serializable

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

data class MoneyConversionEntry(val title: String, val amount: Double) : Serializable
data class MoneyConversionList(val baseCurrency: String, val currencies: List<MoneyConversionEntry>) : Serializable