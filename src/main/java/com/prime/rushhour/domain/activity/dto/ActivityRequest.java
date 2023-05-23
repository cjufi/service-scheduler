package com.prime.rushhour.domain.activity.dto;

import java.util.List;

public record ActivityRequest(

        String name,
        Double price,
        Integer duration,
        Long providerId,
        List<Long> employeeIds
) {
}
