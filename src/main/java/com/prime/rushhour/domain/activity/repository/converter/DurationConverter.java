package com.prime.rushhour.domain.activity.repository.converter;

import jakarta.persistence.AttributeConverter;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class DurationConverter implements AttributeConverter<Duration, Long> {
    @Override
    public Long convertToDatabaseColumn(Duration duration) {
        return duration.toMinutes();
    }

    @Override
    public Duration convertToEntityAttribute(Long aLong) {
        return Duration.ofMinutes(aLong);
    }
}