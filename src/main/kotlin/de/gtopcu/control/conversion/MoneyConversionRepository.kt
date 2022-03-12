package de.gtopcu.control.conversion

import de.gtopcu.model.MoneyConversionModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface MoneyConversionRepository : JpaRepository<MoneyConversionModel, Int> {
    fun findMoneyConversionModelsByBaseCurrency(baseCurrency: String): List<MoneyConversionModel>

    @Query(
        """
            select conversion.id
            from MoneyConversionModel conversion
            where conversion.baseCurrency=:baseCurrency and conversion.currency=:currency
        """
    )
    fun findMoneyConversionModel(
        @Param("baseCurrency") baseCurrency: String,
        @Param("currency") currency: String
    ): MoneyConversionModel?

    @Modifying
    @Transactional
    @Query("delete from MoneyConversionModel conversion where conversion.baseCurrency=:baseCurrency")
    fun deleteMoneyConversionsByBaseCurrency(@Param("baseCurrency") baseCurrency: String)
}