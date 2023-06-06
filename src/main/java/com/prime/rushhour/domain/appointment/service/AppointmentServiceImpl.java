package com.prime.rushhour.domain.appointment.service;

import com.prime.rushhour.domain.appointment.dto.AppointmentRequest;
import com.prime.rushhour.domain.appointment.dto.AppointmentResponse;
import com.prime.rushhour.domain.appointment.repository.AppointmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements AppointmentService{

    private final AppointmentRepository appointmentRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public AppointmentResponse save(AppointmentRequest appointmentRequest) {
        return null;
    }

    @Override
    public AppointmentResponse getById(Long id) {
        return null;
    }

    @Override
    public Page<AppointmentResponse> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public AppointmentResponse update(Long id, AppointmentRequest appointmentRequest) {
        return null;
    }
}