package com.prime.rushhour.infrastructure.mapper.price;

import com.prime.rushhour.domain.activity.entity.Activity;
import com.prime.rushhour.domain.activity.service.ActivityService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class PriceMapper {

    private final ActivityService activityService;

    public PriceMapper(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PriceMapping
    public BigDecimal addPrices(List<Activity> activities) {
        return activityService.addPricesOfActivities(activities);
    }
}
