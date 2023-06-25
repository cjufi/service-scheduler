package com.prime.rushhour.domain.appointment.service;

import com.prime.rushhour.domain.activity.entity.Activity;
import com.prime.rushhour.domain.activity.service.ActivityService;
import com.prime.rushhour.domain.appointment.dto.AppointmentRequest;
import com.prime.rushhour.domain.appointment.dto.AppointmentResponse;
import com.prime.rushhour.domain.appointment.entity.Appointment;
import com.prime.rushhour.domain.appointment.mapper.AppointmentMapper;
import com.prime.rushhour.domain.appointment.repository.AppointmentRepository;
import com.prime.rushhour.infrastructure.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService{

    private final AppointmentRepository appointmentRepository;

    private final AppointmentMapper appointmentMapper;

    private final ActivityService activityService;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper, ActivityService activityService) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
        this.activityService = activityService;
    }

    @Override
    public AppointmentResponse save(AppointmentRequest appointmentRequest) {
        var appointment = appointmentMapper.toEntity(appointmentRequest);
        appointment.setEndDate(appointment.getStartDate().plusMinutes(getDurationOfActivities(appointment.getActivities())));
        return appointmentMapper.toDto(appointmentRepository.save(appointment));
    }

    @Override
    public AppointmentResponse getById(Long id) {
        var appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Appointment.class.getSimpleName(), "id", id));
        return appointmentMapper.toDto(appointment);
    }

    @Override
    public Page<AppointmentResponse> getAll(Pageable pageable) {
        return appointmentRepository.findAll(pageable).map(appointmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        if(!appointmentRepository.existsById(id)) {
            throw new EntityNotFoundException(Appointment.class.getSimpleName(), "id", id);
        }
        appointmentRepository.deleteById(id);
    }

    @Override
    public AppointmentResponse update(Long id, AppointmentRequest appointmentRequest) {
        var appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Appointment.class.getSimpleName(), "id", id));

        List<Activity> activities = activityService.idsToActivities(appointmentRequest.activityIds());
        appointment.setEndDate(appointmentRequest.startDate()
                .plusMinutes(getDurationOfActivities(activities)));

        appointmentMapper.update(appointment, appointmentRequest);
        return appointmentMapper.toDto(appointmentRepository.save(appointment));
    }

    @Override
    public Appointment findById(Long id) {
        return appointmentRepository.findAppointmentById(id);
    }

    private long getDurationOfActivities(List<Activity> activities) {
        long totalDuration = 0;
        for(Activity activity : activities) {
            totalDuration += activity.getDuration().toMinutes();
        }
        return totalDuration;
    }
}