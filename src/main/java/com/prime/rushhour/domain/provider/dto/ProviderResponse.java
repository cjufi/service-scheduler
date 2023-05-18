package com.prime.rushhour.domain.provider.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

public record ProviderResponse(
        String name,
        String website,
        String businessDomain,
        String phone,
        LocalTime startTimeOfWorkingDay,
        LocalTime endTimeOfWorkingDay,
        Set<DayOfWeek> workingDays) {
}