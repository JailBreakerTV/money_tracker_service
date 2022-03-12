package de.gtopcu.control.conversion

import de.gtopcu.control.conversions.MoneyConversionsControl
import de.gtopcu.model.MoneyConversionModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class MoneyConversionControl(
    @Autowired private val repository: MoneyConversionRepository,
    @Autowired private val conversionsControl: MoneyConversionsControl
) {
    fun findMoneyConversion(baseCurrency: String, currency: String): MoneyConversionModel? {
        return repository.findMoneyConversionModel(baseCurrency, currency)
    }

    fun findMoneyConversions(baseCurrency: String): List<MoneyConversionModel> {
        val conversions = repository.findMoneyConversionModelsByBaseCurrency(baseCurrency)
        if (conversions.isEmpty()) {
            return doSearchAndStoreMoneyConversions(baseCurrency)
        }
        if (conversions.any { conversion -> conversion.isOutdated() }) {
            repository.deleteMoneyConversionsByBaseCurrency(baseCurrency)
            return doSearchAndStoreMoneyConversions(baseCurrency)
        }
        return conversions
    }

    private fun MoneyConversionModel.isOutdated() = ChronoUnit.HOURS.between(timestamp, LocalDateTime.now()) >= 12

    private fun doSearchAndStoreMoneyConversions(baseCurrency: String): List<MoneyConversionModel> {
        return conversionsControl.findMoneyConversions(baseCurrency)
            ?.toModels()
            ?.let { models -> repository.saveAll(models) }
            ?: emptyList()
    }
}