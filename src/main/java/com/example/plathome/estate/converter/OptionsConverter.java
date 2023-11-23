package com.example.plathome.estate.converter;

import com.example.plathome.estate.Option;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.Set;

@Converter
public class OptionsConverter implements AttributeConverter<Set<Option>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Set<Option> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new RuntimeException("Converting Set<Option> to JSON string failed.", e);
        }
    }

    @Override
    public Set<Option> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<Set<Option>>(){});
        } catch (IOException e) {
            throw new RuntimeException("Converting JSON string to Set<Option> failed.", e);
        }
    }
}
