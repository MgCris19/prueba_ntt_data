package com.codingtest.client.jpa.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CharToBooleanConverter implements AttributeConverter<Boolean, Character> {

    @Override
    public Character convertToDatabaseColumn(Boolean attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute ? 'A' : 'I';
    }

    @Override
    public Boolean convertToEntityAttribute(Character dbData) {
        if (dbData == null) {
            return null;
        }
        return dbData == 'A';
    }
}