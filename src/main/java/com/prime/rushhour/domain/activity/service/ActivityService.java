package com.prime.rushhour.domain.activity.service;

import com.prime.rushhour.domain.activity.dto.ActivityRequest;
import com.prime.rushhour.domain.activity.dto.ActivityResponse;
import com.prime.rushhour.domain.activity.entity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActivityService {

    ActivityResponse save(ActivityRequest activityRequest);

    ActivityResponse getById(Long id);

    Page<ActivityResponse> getAll(Pageable pageable);

    void delete(Long id);

    ActivityResponse update(Long id, ActivityRequest activityRequest);

    void deleteByProviderId(Long id);

    Long getProviderIdFromActivityId(Long id);

    Long getProviderIdFromAccountId(Long id);

    Page<ActivityResponse> getAllFromSameProvider(Pageable pageable, Long id);

    Activity idToActivity(Long id);

    Activity getActivityById(Long id);

    boolean isEmployeesActivitySame(Long activityId, Long id);
}