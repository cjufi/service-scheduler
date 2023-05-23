package com.prime.rushhour.domain.activity.dto;

import com.prime.rushhour.domain.provider.dto.ProviderResponse;

import java.util.List;

public record ActivityResponse(
        String name,
        Double price,
        Integer duration,
        ProviderResponse providerResponse,
        List<Long> employeeIds
) {
}
