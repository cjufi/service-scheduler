package com.prime.rushhour.domain.activity.dto;

import com.prime.rushhour.domain.provider.dto.ProviderResponse;

import java.math.BigDecimal;
import java.util.List;

public record ActivityResponse(
        String name,
        BigDecimal price,
        Long duration,
        ProviderResponse provider,
        List<Long> employeeIds
) {
}
