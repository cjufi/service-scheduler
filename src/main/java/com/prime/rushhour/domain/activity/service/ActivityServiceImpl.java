package com.prime.rushhour.domain.activity.service;

import com.prime.rushhour.domain.activity.dto.ActivityRequest;
import com.prime.rushhour.domain.activity.dto.ActivityResponse;
import com.prime.rushhour.domain.activity.entity.Activity;
import com.prime.rushhour.domain.activity.mapper.ActivityMapper;
import com.prime.rushhour.domain.activity.repository.ActivityRepository;
import com.prime.rushhour.infrastructure.exceptions.EntityNotFoundException;
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

        var activity = activityMapper.toEntity(activityRequest);
        return activityMapper.toDto(activityRepository.save(activity));
    }

    @Override
    public ActivityResponse getById(Long id) {
        var activity = activityRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(Activity.class.getSimpleName(), "id", id));
        return activityMapper.toDto(activity);
    }

    @Override
    public Page<ActivityResponse> getAll(Pageable pageable) {
        return activityRepository.findAll(pageable).map(activityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        if(!activityRepository.existsById(id)){
            throw new EntityNotFoundException(Activity.class.getSimpleName(), "id", id);
        }
        activityRepository.deleteById(id);
    }

    @Override
    public ActivityResponse update(Long id, ActivityRequest activityRequest) {
        var activity = activityRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(Activity.class.getSimpleName(), "id", id));

        activityMapper.update(activity, activityRequest);
        return activityMapper.toDto(activityRepository.save(activity));
    }
}
