package de.gtopcu.control

import java.sql.Timestamp
import java.time.LocalDateTime
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class LocalDateTimeAttributeConverter : AttributeConverter<LocalDateTime, Timestamp> {
    override fun convertToDatabaseColumn(attribute: LocalDateTime?): Timestamp? = attribute?.let { Timestamp.valueOf(it) }

    override fun convertToEntityAttribute(dbData: Timestamp?): LocalDateTime? = dbData?.toLocalDateTime()
}