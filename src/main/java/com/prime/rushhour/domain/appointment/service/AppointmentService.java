package com.prime.rushhour.domain.appointment.service;

import com.prime.rushhour.domain.appointment.dto.AppointmentRequest;
import com.prime.rushhour.domain.appointment.dto.AppointmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppointmentService {

    AppointmentResponse save(AppointmentRequest appointmentRequest);

    AppointmentResponse getById(Long id);

    Page<AppointmentResponse> getAll(Pageable pageable);

    void delete(Long id);

    AppointmentResponse update(Long id, AppointmentRequest appointmentRequest);
}