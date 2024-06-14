package com.prime.rushhour.domain.activity.service;

import com.prime.rushhour.domain.activity.dto.ActivityRequest;
import com.prime.rushhour.domain.activity.dto.ActivityResponse;
import com.prime.rushhour.domain.activity.entity.Activity;
import com.prime.rushhour.domain.activity.mapper.ActivityMapper;
import com.prime.rushhour.domain.activity.repository.ActivityRepository;
import com.prime.rushhour.domain.employee.service.EmployeeService;
import com.prime.rushhour.infrastructure.exceptions.EntityNotFoundException;
import com.prime.rushhour.infrastructure.exceptions.ForbiddenActivityException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    private final ActivityMapper activityMapper;

    private final EmployeeService employeeService;

    public ActivityServiceImpl(ActivityRepository activityRepository, ActivityMapper activityMapper, EmployeeService employeeService) {
        this.activityRepository = activityRepository;
        this.activityMapper = activityMapper;
        this.employeeService = employeeService;
    }

    @Override
    public ActivityResponse save(ActivityRequest activityRequest) {

        var activity = activityMapper.toEntity(activityRequest);
        return activityMapper.toDto(activityRepository.save(activity));
    }

    @Override
    public ActivityResponse getById(Long id) {
        var activity = activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Activity.class.getSimpleName(), "id", id));
        return activityMapper.toDto(activity);
    }

    @Override
    public Page<ActivityResponse> getAll(Pageable pageable) {
        return activityRepository.findAll(pageable).map(activityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        if (!activityRepository.existsById(id)) {
            throw new EntityNotFoundException(Activity.class.getSimpleName(), "id", id);
        }
        activityRepository.deleteById(id);
    }

    @Override
    public ActivityResponse update(Long id, ActivityRequest activityRequest) {
        var activity = activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Activity.class.getSimpleName(), "id", id));

        activityMapper.update(activity, activityRequest);
        return activityMapper.toDto(activityRepository.save(activity));
    }

    @Override
    public void deleteByProviderId(Long id) {
        activityRepository.deleteActivitiesByProviderId(id);
    }

    @Override
    public Long getProviderIdFromActivityId(Long id) {
        return activityRepository.findProviderIdByActivityId(id);
    }

    @Override
    public Long getProviderIdFromAccountId(Long id) {
        return employeeService.getProviderIdFromAccount(id);
    }

    @Override
    public Page<ActivityResponse> getAllFromSameProvider(Pageable pageable, Long id) {
        var providerId = getProviderIdFromAccountId(id);
        return activityRepository.findActivitiesByProviderId(pageable, providerId).map(activityMapper::toDto);
    }

    @Override
    public List<Activity> idsToActivities(List<Long> ids) {
        return activityRepository.findByIdIn(ids);
    }

    @Override
    public Activity getActivityById(Long id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Activity.class.getSimpleName(), "id", id));
    }

    @Override
    public boolean isEmployeesActivitySame(List<Long> activityIds, Long accountId) {
        var providerId = employeeService.getProviderIdFromAccount(accountId);
        for (Long id : activityIds) {
            getActivityById(id);
            var activitiesProviderId = activityRepository.findProviderIdByActivityId(id);
            if (!(activitiesProviderId.equals(providerId))) {
                throw new ForbiddenActivityException(id);
            }
        }
        return true;
    }

    @Override
    public BigDecimal addPricesOfActivities(List<Activity> activities) {
        BigDecimal sum = BigDecimal.valueOf(0);

        for(Activity activity : activities) {
            sum = sum.add(activity.getPrice());
        }
        return sum;
    }
}