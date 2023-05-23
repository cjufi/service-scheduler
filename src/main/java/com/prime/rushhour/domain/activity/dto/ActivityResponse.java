package com.prime.rushhour.domain.activity.dto;

import com.prime.rushhour.domain.provider.dto.ProviderResponse;

import java.time.Duration;
import java.util.List;

public record ActivityResponse(
        String name,
        Double price,
        Duration duration,
        ProviderResponse providerResponse,
        List<Long> employeeIds
) {
}
