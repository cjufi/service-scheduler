package com.prime.rushhour.domain.appointment.service;

import com.prime.rushhour.domain.appointment.dto.AppointmentRequest;
import com.prime.rushhour.domain.appointment.dto.AppointmentResponse;
import com.prime.rushhour.domain.appointment.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AppointmentService {

    AppointmentResponse save(AppointmentRequest appointmentRequest);

    AppointmentResponse getById(Long id);

    Page<AppointmentResponse> getAll(Pageable pageable);

    void delete(Long id);

    AppointmentResponse update(Long id, AppointmentRequest appointmentRequest);

    Appointment findById(Long id);
}