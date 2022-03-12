package de.gtopcu.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(
    name = "money_conversion",
    indexes = [
        Index(name = "idx_base_currency", columnList = "base_currency", unique = false),
        Index(name = "idx_currency", columnList = "currency", unique = false),
    ]
)
data class MoneyConversionModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, length = 11)
    var id: Long = -1,

    @Column(name = "base_currency", nullable = false, length = 3)
    var baseCurrency: String = "undefined",

    @Column(name = "currency", nullable = false, length = 3)
    var currency: String = "undefined",

    @Column(name = "value", precision = 20, scale = 5)
    var value: Double = -1.0,

    @Column(name = "timestamp")
    val timestamp: LocalDateTime = LocalDateTime.now()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MoneyConversionModel

        if (id != other.id) return false
        if (baseCurrency != other.baseCurrency) return false
        if (currency != other.currency) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + baseCurrency.hashCode()
        result = 31 * result + currency.hashCode()
        return result
    }

    override fun toString(): String {
        return "MoneyConversionModel(id=$id, baseCurrency='$baseCurrency', currency='$currency', value=$value, timestamp=$timestamp)"
    }
}