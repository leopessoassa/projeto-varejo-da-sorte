package com.virtualize.varejodasorte.api.entity.converter;

import jakarta.persistence.AttributeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDate8Converter implements AttributeConverter<LocalDate, Integer> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalDate8Converter.class);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Override
    public Integer convertToDatabaseColumn(LocalDate attribute) {
        if (attribute == null) {
            return null;
        }

        try {
            return Integer.parseInt(attribute.format(formatter));
        } catch (NumberFormatException e) {
            LOGGER.warn("Erro ao realizar a conversão para inteiro de {}: {}. Retornando null", attribute, e.getMessage());
            return null;
        }
    }

    @Override
    public LocalDate convertToEntityAttribute(Integer dbData) {
        try {
            return dbData == null || dbData == 0 ? null : LocalDate.parse("" + dbData, formatter);
        } catch (DateTimeParseException e) {
            LOGGER.warn("Erro ao realizar parse de {}: {}. Retornando null", dbData, e.getMessage());
            return null;
        }
    }
}
