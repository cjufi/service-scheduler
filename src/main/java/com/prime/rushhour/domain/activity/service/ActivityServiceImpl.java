package com.prime.rushhour.domain.activity.service;

import com.prime.rushhour.domain.activity.dto.ActivityRequest;
import com.prime.rushhour.domain.activity.dto.ActivityResponse;
import com.prime.rushhour.domain.activity.mapper.ActivityMapper;
import com.prime.rushhour.domain.activity.repository.ActivityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    private final ActivityMapper activityMapper;

    public ActivityServiceImpl(ActivityRepository activityRepository, ActivityMapper activityMapper) {
        this.activityRepository = activityRepository;
        this.activityMapper = activityMapper;
    }

    @Override
    public ActivityResponse save(ActivityRequest activityRequest) {
        return null;
    }

    @Override
    public ActivityResponse getById(Long id) {
        return null;
    }

    @Override
    public Page<ActivityResponse> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public ActivityResponse update(Long id, ActivityRequest activityRequest) {
        return null;
    }
}
