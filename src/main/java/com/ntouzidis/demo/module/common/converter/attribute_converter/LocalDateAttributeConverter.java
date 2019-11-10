package com.ntouzidis.demo.module.common.converter.attribute_converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDate;

@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Timestamp> {

  @Override
  public Timestamp convertToDatabaseColumn(LocalDate localDate) {
    return localDate == null ? null : Timestamp.valueOf(localDate.atStartOfDay());
  }

  @Override
  public LocalDate convertToEntityAttribute(Timestamp timestamp) {
    return timestamp == null ? null : timestamp.toLocalDateTime().toLocalDate();
  }
}