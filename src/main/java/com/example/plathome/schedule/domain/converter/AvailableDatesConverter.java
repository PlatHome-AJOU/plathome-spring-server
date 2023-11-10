package com.example.plathome.schedule.domain.converter;

import com.example.plathome.schedule.domain.AvailableDate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class AvailableDatesConverter implements AttributeConverter<Set<AvailableDate>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Set<AvailableDate> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert Set<AvailableDate> to JSON String", e);
        }
    }

    @Override
    public Set<AvailableDate> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<Set<AvailableDate>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON String to Set<AvailableDate>", e);
        }
    }
}